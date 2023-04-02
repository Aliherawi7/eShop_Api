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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final CommentAgreeDisagreeService commentAgreeDisagreeService;
    private final CommentDTOMapper commentDTOMapper;

    public CommentService(CommentRepository commentRepository,
                          UserService userService,
                          CommentAgreeDisagreeService commentAgreeDisagreeService,
                          CommentDTOMapper commentDTOMapper) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.commentAgreeDisagreeService = commentAgreeDisagreeService;
        this.commentDTOMapper = commentDTOMapper;
    }

    public Collection<CommentDTO> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(item -> {
                    CommentDTO commentDTO = commentDTOMapper.apply(item);
                    UserApp user = userService.getUser(item.getUserId());
                    commentDTO.setUserName(user.getName() + " " + user.getLastName());
                    commentDTO.setLikes(commentAgreeDisagreeService.getAllLikesOfTheComment(item.getId()));
                    commentDTO.setDisLikes(commentAgreeDisagreeService.getAllLikesOfTheComment(item.getId()));
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }

    public CommentDTO getComment(long commentId) {

        Comment comment = commentRepository.findById(commentId).orElse(null);
        assert comment != null;
        CommentDTO commentDTO = commentDTOMapper.apply(comment);
        UserApp user = userService.getUser(comment.getUserId());
        commentDTO.setUserName(user.getName() + " " + user.getLastName());
        commentDTO.setLikes(commentAgreeDisagreeService.getAllLikesOfTheComment(commentId));
        commentDTO.setDisLikes(commentAgreeDisagreeService.getAllDislikesOfTheComment(commentId));
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
        comment = commentRepository.save(comment);
        CommentDTO commentDTO = commentDTOMapper.apply(comment);
        commentDTO.setUserName(userService.getUser(userId).getName() + " " + userService.getUser(userId).getLastName());
        commentDTO.setLikes(0);
        commentDTO.setDisLikes(0);
        return commentDTO;
    }

    public CommentDTO updateComment(SaveCommentDTO saveCommentDTO, HttpServletRequest request) {
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
                    user.getImage(),
                    commentAgreeDisagreeService.getAllLikesOfTheComment(saveCommentDTO.getId()),
                    commentAgreeDisagreeService.getAllDislikesOfTheComment(saveCommentDTO.getId())
            );
        } else {
            return null;
        }
    }

    public Collection<CommentDTO> getAllCommentsByProductId(long id) {
        return commentRepository.findAllByProductId(id)
                .stream()
                .map(item -> {
                    CommentDTO commentDTO = commentDTOMapper.apply(item);
                    UserApp user = userService.getUser(item.getUserId());
                    commentDTO.setUserName(user.getName() + " " + user.getLastName());
                    commentDTO.setLikes(commentAgreeDisagreeService.getAllLikesOfTheComment(item.getId()));
                    commentDTO.setDisLikes(commentAgreeDisagreeService.getAllDislikesOfTheComment(item.getId()));
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }

    public Collection<CommentDTO> getAllCommentByUserId(long userId) {
        return commentRepository.findAllByUserId(userId)
                .stream()
                .map(item -> {
                    CommentDTO commentDTO = commentDTOMapper.apply(item);
                    UserApp user = userService.getUser(item.getUserId());
                    commentDTO.setUserName(user.getName() + " " + user.getLastName());
                    commentDTO.setLikes(commentAgreeDisagreeService.getAllLikesOfTheComment(item.getId()));
                    commentDTO.setDisLikes(commentAgreeDisagreeService.getAllDislikesOfTheComment(item.getId()));
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }

    public CommentDTO likeComment(long commentId, HttpServletRequest request) {
        long userId = userService.getUser(TestUserWithJWT.getUserEmailByJWT(request)).getId();
        Map<String, Integer> likesAndDislikes = commentAgreeDisagreeService.addLikeToComment(commentId, userId);

        Comment comment = commentRepository.findById(commentId).orElse(null);
        CommentDTO commentDTO = null;
        if (comment != null) {
            commentDTO = commentDTOMapper.apply(comment);
            UserApp user = userService.getUser(comment.getUserId());
            commentDTO.setUserName(user.getName() + " " + user.getLastName());
            commentDTO.setLikes(likesAndDislikes.get("likes"));
            commentDTO.setDisLikes(likesAndDislikes.get("dislikes"));
        }
        return commentDTO;
    }

    public CommentDTO dislikeComment(long commentId, HttpServletRequest request) {
        long userId = userService.getUser(TestUserWithJWT.getUserEmailByJWT(request)).getId();
        Map<String, Integer> likesAndDislikes = commentAgreeDisagreeService.addDislikeToComment(commentId, userId);
        CommentDTO commentDTO = null;
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            commentDTO = commentDTOMapper.apply(comment);
            UserApp user = userService.getUser(comment.getUserId());
            commentDTO.setUserName(user.getName() + " " + user.getLastName());
            commentDTO.setLikes(likesAndDislikes.get("likes"));
            commentDTO.setDisLikes(likesAndDislikes.get("dislikes"));
        }
        return commentDTO;
    }
}
