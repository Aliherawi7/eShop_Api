package com.eshop.service;

import com.eshop.exception.FileNotFoundException;
import com.eshop.properties.FileStorageLocation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    private final Path brandImageLocation;

    public FileStorageService(FileStorageLocation fileStorageLocation) {
        this.userProfileImageLocation = Paths
                .get(fileStorageLocation.getUserProfileUploadDir())
                .toAbsolutePath()
                .normalize();
        this.productImageLocation = Paths.get(fileStorageLocation.getProductUploadDir())
                .toAbsolutePath()
                .normalize();
        this.brandImageLocation = Paths.get(fileStorageLocation.getBrandUploadDir())
                .toAbsolutePath()
                .normalize();
        try {
            Files.createDirectories(userProfileImageLocation);
            Files.createDirectories(productImageLocation);
        } catch (IOException e) {
            System.out.println("Couldn't create the directory where the upload files will be saved. " + e.getMessage());
        }
    }

    /*
     * store the user profile image
     * */
    public void storeUserProfileImage(MultipartFile file, long userId) {
        try {
            storeTheFile(file, userId + "", userProfileImageLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * store the user profile image
     * */
    public void storeUserProfileImageByteArray(byte[] file, long userId) {
        try {
            storeTheByteArray(file, userId + "", userProfileImageLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (multipartFile == null || fileName == null) return;

        String originalFileName = StringUtils.getFilename(multipartFile.getOriginalFilename());

        // Check if the file's name contains valid  characters or not
        if (fileName.contains("..")) {
            throw new RuntimeException("Sorry! File name which contains invalid path sequence " + originalFileName);
        }
        assert originalFileName != null;
        String extension = originalFileName.split("\\.")[1];
        path = path.resolve(fileName + "." + extension);
        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    }

    public void storeTheByteArray(byte[] multipartFile, String fileName, Path path) throws IOException {
        if (multipartFile == null || fileName == null) return;
        File file = new File(path.resolve(fileName + ".png").toUri());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile);
        fileOutputStream.close();
//
//        Path targetLocation = userProfileImageLocation.resolve(fileName+".png");
//        Files.copy(fileInputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    /*
     * get the image by file name and file location
     * */
    public byte[] getFile(String fileName, Path path) {
        File image = Stream
                .of(Objects.requireNonNull(new File(path.toUri()).listFiles()))
                .filter(item -> item.getName().split("\\.")[0].equalsIgnoreCase(fileName))
                .findFirst()
                .orElse(null);
        if (image == null) {
            throw new FileNotFoundException("File not found with provided username");
        }
        byte[] imageBytes = new byte[(int) image.length()];

        try (FileInputStream inputStream = new FileInputStream(image)) {
            inputStream.read(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBytes;
    }

    /*
     * get the product images
     * */

    public byte[] getProductImage(String productName) {
        return getFile(productName, productImageLocation);
    }

    /*
     * get the user profile image
     * */
    public byte[] getUserImage(String username) {
        return getFile(username, userProfileImageLocation);
    }

    /*
     * get the brand image
     * */
    public byte[] getBrandImage(String brandName) {
        return getFile(brandName, brandImageLocation);
    }


}
