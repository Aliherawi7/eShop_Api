package com.eshop.service;

import com.eshop.dto.CommentDTO;
import com.eshop.dto.SaveCommentDTO;
import com.eshop.model.Comment;
import com.eshop.model.UserApp;
import com.eshop.repository.CommentRepository;
import com.eshop.security.TestUserWithJWT;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public Collection<CommentDTO> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(item -> {
                    CommentDTO commentDTO = new CommentDTO();
                    UserApp user = userService.getUser(item.getUserId());
                    commentDTO.setUserName(user.getName() + " " + user.getLastName());
                    commentDTO.setMessage(item.getMessage());
                    commentDTO.setRate(item.getRate());
                    commentDTO.setUserImage(user.getImage());
                    commentDTO.setCommentDate(item.getCommentDate());
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }

    public CommentDTO getComment(long commentId) {
        CommentDTO commentDTO = new CommentDTO();
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            UserApp user = userService.getUser(comment.getUserId());
            commentDTO.setUserName(user.getName() + " " + user.getLastName());
            commentDTO.setMessage(comment.getMessage());
            commentDTO.setRate(comment.getRate());
            commentDTO.setUserImage(user.getImage());
            commentDTO.setCommentDate(comment.getCommentDate());
            commentDTO.setLikes(0);
            commentDTO.setDisLikes(0);
        }
        return commentDTO;
    }

    public CommentDTO addComment(SaveCommentDTO saveCommentDTO, HttpServletRequest request) {
        Comment comment = new Comment();
        long userId = userService.getUser(TestUserWithJWT.getUserEmailByJWT(request)).getId();
        comment.setUserId(userId);
        comment.setMessage(saveCommentDTO.getMessage());
        comment.setCommentDate(LocalDateTime.now());
        comment.setProductId(saveCommentDTO.getProductId());
        comment.setRate(saveCommentDTO.getRate());
        // save the comment and get the comment id back
        long commentId = commentRepository.save(comment).getId();

        return new CommentDTO(
                commentId,
                userService.getUser(userId).getName()+" "+userService.getUser(userId).getLastName(),
                saveCommentDTO.getMessage(),
                saveCommentDTO.getRate(),
                LocalDateTime.now(),
                userService.getUser(userId).getImage()
        );
    }

    public CommentDTO updateComment(SaveCommentDTO saveCommentDTO ,HttpServletRequest request) {
        if (commentRepository.findById(saveCommentDTO.getId()).isPresent()) {
            Comment comment = new Comment();
            UserApp user = userService.getUser(TestUserWithJWT.getUserEmailByJWT(request));
            comment.setId(saveCommentDTO.getId());
            comment.setUserId(user.getId());
            comment.setMessage(saveCommentDTO.getMessage());
            comment.setCommentDate(LocalDateTime.now());
            comment.setProductId(saveCommentDTO.getProductId());
            comment.setRate(saveCommentDTO.getRate());
            commentRepository.save(comment);
            return new CommentDTO(
                    saveCommentDTO.getId(),
                    user.getName() + " " + user.getLastName(),
                    saveCommentDTO.getMessage(),
                    saveCommentDTO.getRate(),
                    LocalDateTime.now(),
                    user.getImage()
            );
        }else{
            return null;
        }
    }

    public Collection<CommentDTO> getAllCommentsByProductId(long id) {
        return commentRepository.findAllByProductId(id)
                .stream()
                .map(item -> {
                    CommentDTO commentDTO = new CommentDTO();
                    UserApp user = userService.getUser(item.getUserId());
                    commentDTO.setUserName(user.getName() + " " + user.getLastName());
                    commentDTO.setMessage(item.getMessage());
                    commentDTO.setRate(item.getRate());
                    commentDTO.setUserImage(user.getImage());
                    commentDTO.setCommentDate(item.getCommentDate());
                    System.out.println(item.getUserId()+ " "+ item.getMessage()+" " +item.getProductId());
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }

    public Collection<CommentDTO> getAllCommentByUserId(long userId) {
        return commentRepository.findAllByUserId(userId)
                .stream()
                .map(item -> {
                    CommentDTO commentDTO = new CommentDTO();
                    UserApp user = userService.getUser(item.getUserId());
                    commentDTO.setUserName(user.getName() + " " + user.getLastName());
                    commentDTO.setMessage(item.getMessage());
                    commentDTO.setRate(item.getRate());
                    commentDTO.setUserImage(user.getImage());
                    commentDTO.setCommentDate(item.getCommentDate());
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }

    public long icreaseLikes(long commentId, HttpServletRequest request){
        long likes = 0;

        return likes;
    }
}
