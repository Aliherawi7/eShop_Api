package com.eshop.service;

import com.eshop.dto.CommentDTO;
import com.eshop.model.Comment;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CommentDTOMapper implements Function<Comment, CommentDTO> {
    @Override
    public CommentDTO apply(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                null,
                comment.getMessage(),
                comment.getRate(),
                comment.getCommentDate(),
                null,
                0,
                0
        );
    }
}
