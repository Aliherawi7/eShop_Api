package com.eshop.service;


import com.eshop.model.AgreeDisagree;
import com.eshop.repository.CommentAgreeDisagreeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentAgreeDisagreeService {
    private final CommentAgreeDisagreeRepository commentAgreeDisagreeRepository;

    public CommentAgreeDisagreeService(CommentAgreeDisagreeRepository commentAgreeDisagreeRepository) {
        this.commentAgreeDisagreeRepository = commentAgreeDisagreeRepository;
    }


    public Map<String, Long> addLikeToComment(long commentId, long userId) {
        AgreeDisagree agreeDisagree =
                commentAgreeDisagreeRepository.findByCommentIdAndUserId(commentId, userId);
        Map<String, Long> map = new HashMap<>();

        // if the user has been agreed or disagreed before with this comment
        if (agreeDisagree != null) {
            // if user has been disagreed with this comment before
            if (agreeDisagree.isDisagree()) {
                agreeDisagree.setAgree(true);
                agreeDisagree.setDisagree(false);
                commentAgreeDisagreeRepository.save(agreeDisagree);

                // if user has been agreed with this comment before
            } else {
                commentAgreeDisagreeRepository.delete(agreeDisagree);
            }

            // if the comment has been liked or disliked before by this user
        } else {
            agreeDisagree = new AgreeDisagree();
            agreeDisagree.setAgree(true);
            agreeDisagree.setCommentId(commentId);
            agreeDisagree.setUserId(userId);
            agreeDisagree.setDisagree(false);
            commentAgreeDisagreeRepository.save(agreeDisagree);
        }
        map.put("likes", commentAgreeDisagreeRepository.countAgreeDisagreeByCommentIdAndAgree(commentId, true));
        map.put("dislikes", commentAgreeDisagreeRepository.countAgreeDisagreeByCommentIdAndDisagree(commentId, true));
        return map;
    }

    public Map<String, Long> addDislikeToComment(long commentId, long userId) {
        AgreeDisagree agreeDisagree =
                commentAgreeDisagreeRepository.findByCommentIdAndUserId(commentId, userId);
        Map<String, Long> map = new HashMap<>();

        // if the user has been agreed or disagreed before with this comment
        if (agreeDisagree != null) {
            // if user has been agreed with this comment before
            if (agreeDisagree.isAgree()) {
                agreeDisagree.setAgree(false);
                agreeDisagree.setDisagree(true);
                commentAgreeDisagreeRepository.save(agreeDisagree);

                // if user has not been agree with this comment before
            } else {
                commentAgreeDisagreeRepository.delete(agreeDisagree);
            }

            // if the comment has been disliked before by this user
        } else {
            agreeDisagree = new AgreeDisagree();
            agreeDisagree.setDisagree(true);
            agreeDisagree.setCommentId(commentId);
            agreeDisagree.setUserId(userId);
            commentAgreeDisagreeRepository.save(agreeDisagree);
        }

        map.put("likes", commentAgreeDisagreeRepository.countAgreeDisagreeByCommentIdAndAgree(commentId, true));
        map.put("dislikes", commentAgreeDisagreeRepository.countAgreeDisagreeByCommentIdAndDisagree(commentId, true));
        return map;
    }


    public long getAllLikesOfTheComment(long commentId) {
        return commentAgreeDisagreeRepository.countAgreeDisagreeByCommentIdAndAgree(commentId, true);
    }

    public long getAllDislikesOfTheComment(long commentId) {
        return commentAgreeDisagreeRepository.countAgreeDisagreeByCommentIdAndDisagree(commentId, true);
    }
}
