package com.eshop.service;

import com.eshop.constants.APIEndpoints;
import com.eshop.dto.CommentDTO;
import com.eshop.model.Comment;
import com.eshop.utils.BaseURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

@Component
public class CommentDTOMapper implements Function<Comment, CommentDTO> {
    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public CommentDTO apply(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                null,
                comment.getMessage(),
                comment.getRate(),
                comment.getCommentDate(),
                BaseURI.getBaseURI(httpServletRequest) + APIEndpoints.USER_PICTURE.getValue() + comment.getUserId(),
                0L,
                0L
        );
    }
}
