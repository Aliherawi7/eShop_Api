package com.eshop.service;


import com.eshop.model.Comment;
import com.eshop.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public Collection<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Comment getComment(Long commentId){
        return commentRepository.findById(commentId).orElse(null);
    }

    public void addComment(Comment comment){
        commentRepository.save(comment);
    }

    public void updateComment(Comment comment){
        if(commentRepository.findById(comment.getId()).isPresent()){
            commentRepository.save(comment);
        }
    }

    public Collection<Comment> getAllCommentsByProductId(Long id){
        return commentRepository.findAllByProductId(id);
    }

    public Collection<Comment> getAllCommentByUserId(Long userId){
        return commentRepository.findAllByUserId(userId);
    }
}
