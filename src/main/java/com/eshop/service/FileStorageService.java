package com.eshop.service;

import com.eshop.properties.FileStorageLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    private final Path userProfileImageLocation;
    private final Path productImageLocation;

    public FileStorageService(FileStorageLocation fileStorageLocation) {
        this.userProfileImageLocation = Paths
                .get(fileStorageLocation.getUserProfileUploadDir())
                .toAbsolutePath()
                .normalize();
        this.productImageLocation = Paths.get(fileStorageLocation.getProductUploadDir())
                .toAbsolutePath()
                .normalize();
        try{
            Files.createDirectories(userProfileImageLocation);
            Files.createDirectories(productImageLocation);
        } catch (IOException e) {
            System.out.println("Couldn't create the directory where the upload files will be saved. " + e.getMessage());
        }
    }

    /*
    * store the user profile image
    * */
    public void storeUserProfileImage(MultipartFile file, String userName) throws IOException {
        storeTheFile(file, userName, userProfileImageLocation);
    }

    /*
    * store the product images
    * */
    public void storeProductImage(MultipartFile file, String productName) throws IOException {
        storeTheFile(file, productName, productImageLocation);
    }


    /*
     * store the file in the user-profile-image directory
     * */
    public void storeTheFile(MultipartFile multipartFile, String fileName, Path path) throws IOException {
        if(multipartFile == null || fileName == null) return;
        // Normalize file name
        String originalFileName = StringUtils.getFilename(multipartFile.getOriginalFilename());
        // Check if the file's name contains valid  characters or not
        assert originalFileName != null;
        if (originalFileName.contains("..")) {
            throw new RuntimeException("Sorry! File name which contains invalid path sequence " + fileName);
        }
        String extension = multipartFile.getOriginalFilename().split("\\.")[1];
        Path targetLocation = userProfileImageLocation.resolve(fileName+"."+extension);
        Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    /*
    * get the image by file name and file location
    * */
    public byte[] getFile(String fileName, Path path) throws FileNotFoundException {
        File image = Stream
                .of(Objects.requireNonNull(new File(path.toUri()).listFiles()))
                .filter(item -> item.getName().split("\\.")[0].equalsIgnoreCase(fileName))
                .findFirst()
                .orElse(null);
        if(image == null){
            throw new FileNotFoundException("File not found with provided username");
        }
        byte[] imageBytes = new byte[(int)image.length()];

        try( FileInputStream inputStream = new FileInputStream(image)){
            inputStream.read(imageBytes);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return imageBytes;
    }

    /*
    * get the product images
    * */

    public byte[] getProductImage(String productName) throws FileNotFoundException {
        return getFile(productName, productImageLocation);
    }

    /*
    * get the user profile image
    * */
    public byte[] getUserImage(String username) throws FileNotFoundException {
        return getFile(username, userProfileImageLocation);
    }


}
