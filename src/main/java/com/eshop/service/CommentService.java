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
        long userId = userService.getUser(TestUserWithJWT.getUserEmailByJWT(request)).getId();
        if(commentRepository.existsCommentByUserIdAndProductId(userId, saveCommentDTO.getProductId())){
            System.out.println("update the comment");
            return updateComment(saveCommentDTO, request);
        }
        Comment comment = new Comment();

        comment.setUserId(userId);
        comment.setMessage(saveCommentDTO.getMessage());
        comment.setCommentDate(LocalDateTime.now());
        comment.setProductId(saveCommentDTO.getProductId());
        comment.setRate(saveCommentDTO.getRate());
        // save the comment and get the comment id back
        comment = commentRepository.save(comment);
        CommentDTO commentDTO = commentDTOMapper.apply(comment);
        commentDTO.setUserName(userService.getUser(userId).getName() + " " + userService.getUser(userId).getLastName());
        return commentDTO;
    }

    public CommentDTO updateComment(SaveCommentDTO saveCommentDTO, HttpServletRequest request) {
        UserApp user = userService.getUser(TestUserWithJWT.getUserEmailByJWT(request));
        Comment comment = commentRepository.findCommentByUserIdAndProductId(user.getId(), saveCommentDTO.getProductId());
        if (comment != null) {
            comment.setMessage(saveCommentDTO.getMessage());
            comment.setCommentDate(LocalDateTime.now());
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
        Comment comment = commentRepository.findById(commentId).orElse(null);
        CommentDTO commentDTO = null;
        if (comment != null) {
            commentDTO = commentDTOMapper.apply(comment);
            UserApp user = userService.getUser(comment.getUserId());
            commentDTO.setUserName(user.getName() + " " + user.getLastName());
            if(comment.getUserId() == userId){
                throw new RuntimeException("Not allowed to like the comment: "+commentId+ " by user: "+userId);
            }
            Map<String, Long> likesAndDislikes = commentAgreeDisagreeService.addLikeToComment(commentId, userId);
            commentDTO.setLikes(likesAndDislikes.get("likes"));
            commentDTO.setDisLikes(likesAndDislikes.get("dislikes"));
        }
        return commentDTO;
    }

    public CommentDTO dislikeComment(long commentId, HttpServletRequest request) {
        long userId = userService.getUser(TestUserWithJWT.getUserEmailByJWT(request)).getId();
        CommentDTO commentDTO = null;
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            commentDTO = commentDTOMapper.apply(comment);
            UserApp user = userService.getUser(comment.getUserId());
            commentDTO.setUserName(user.getName() + " " + user.getLastName());
            if(comment.getUserId() == userId){
                throw new RuntimeException("Not allowed to dislike the comment: "+commentId+ " by user: "+userId);
            }
            Map<String, Long> likesAndDislikes = commentAgreeDisagreeService.addDislikeToComment(commentId, userId);
            commentDTO.setLikes(likesAndDislikes.get("likes"));
            commentDTO.setDisLikes(likesAndDislikes.get("dislikes"));
        }
        return commentDTO;
    }
}
