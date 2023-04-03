package com.eshop.repository;

import com.eshop.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Collection<Comment> findAllByProductId(long productId);
    Collection<Comment> findAllByUserId(long userId);
    Comment findCommentByUserIdAndProductId(long userId, long productId);
    boolean existsCommentByUserIdAndProductId(long userId, long productId);

}
