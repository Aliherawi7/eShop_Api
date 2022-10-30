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
                    commentDTO.setComment(item.getComment());
                    commentDTO.setRate(item.getRete());
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
            commentDTO.setComment(comment.getComment());
            commentDTO.setRate(comment.getRete());
            commentDTO.setUserImage(user.getImage());
            commentDTO.setCommentDate(comment.getCommentDate());
        }
        return commentDTO;
    }

    public void addComment(SaveCommentDTO saveCommentDTO, HttpServletRequest request) {
        Comment comment = new Comment();
        long userId = userService.getUser(TestUserWithJWT.getUserEmailByJWT(request)).getId();
        comment.setUserId(userId);
        comment.setComment(saveCommentDTO.getComment());
        comment.setCommentDate(LocalDateTime.now());
        comment.setProductId(saveCommentDTO.getProductId());
        comment.setRete(saveCommentDTO.getRate());
        commentRepository.save(comment);
    }

    public void updateComment(SaveCommentDTO saveCommentDTO ,HttpServletRequest request) {
        if (commentRepository.findById(saveCommentDTO.getId()).isPresent()) {
            Comment comment = new Comment();
            long userId = userService.getUser(TestUserWithJWT.getUserEmailByJWT(request)).getId();
            comment.setId(saveCommentDTO.getId());
            comment.setUserId(userId);
            comment.setComment(saveCommentDTO.getComment());
            comment.setCommentDate(LocalDateTime.now());
            comment.setProductId(saveCommentDTO.getProductId());
            comment.setRete(saveCommentDTO.getRate());
            commentRepository.save(comment);
        }
    }

    public Collection<CommentDTO> getAllCommentsByProductId(Long id) {
        return commentRepository.findAllByProductId(id)
                .stream()
                .map(item -> {
                    CommentDTO commentDTO = new CommentDTO();
                    UserApp user = userService.getUser(item.getUserId());
                    commentDTO.setUserName(user.getName() + " " + user.getLastName());
                    commentDTO.setComment(item.getComment());
                    commentDTO.setRate(item.getRete());
                    commentDTO.setUserImage(user.getImage());
                    commentDTO.setCommentDate(item.getCommentDate());
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }

    public Collection<CommentDTO> getAllCommentByUserId(Long userId) {
        return commentRepository.findAllByUserId(userId)
                .stream()
                .map(item -> {
                    CommentDTO commentDTO = new CommentDTO();
                    UserApp user = userService.getUser(item.getUserId());
                    commentDTO.setUserName(user.getName() + " " + user.getLastName());
                    commentDTO.setComment(item.getComment());
                    commentDTO.setRate(item.getRete());
                    commentDTO.setUserImage(user.getImage());
                    commentDTO.setCommentDate(item.getCommentDate());
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }
}
