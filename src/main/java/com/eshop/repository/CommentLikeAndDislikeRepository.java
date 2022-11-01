package com.eshop.repository;

import com.eshop.dto.CommentLikeAndDislike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeAndDisliskeRepository extends JpaRepository<CommentLikeAndDislike, String> {
}
