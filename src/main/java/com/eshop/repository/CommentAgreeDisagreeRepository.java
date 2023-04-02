package com.eshop.repository;

import com.eshop.model.AgreeDisagree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CommentAgreeDisagreeRepository extends JpaRepository<AgreeDisagree, Long> {
    Collection<AgreeDisagree> findAllByCommentIdAndUserId(long commentId, long userId);

    Collection<AgreeDisagree> findAllByCommentIdAndAgree(long commentId, boolean agree);

    Collection<AgreeDisagree> findAllByCommentIdAndDisagree(long commentId, boolean disagree);

    AgreeDisagree findByCommentIdAndUserId(long commentId, long userId);
}
