package com.eshop.resources;

import com.eshop.dto.CommentDTO;
import com.eshop.dto.SaveCommentDTO;
import com.eshop.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping("/api/comments")
public class CommentResource {
    private final CommentService commentService;

    public CommentResource(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable long id) {
        CommentDTO comment = commentService.getComment(id);
        if (comment != null) {
            return ResponseEntity.ok().body(comment);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllComments() {
        Collection<CommentDTO> comments = commentService.getAllComments();
        if (comments.size() > 0) {
            return ResponseEntity.ok().body(comments);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getAllCommentByProductId(@PathVariable(value = "id") long productId) {
        Collection<CommentDTO> comments = commentService.getAllCommentsByProductId(productId);
        if (comments.size() > 0) {
            return ResponseEntity.ok().body(comments);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("users/{id}")
    public ResponseEntity<?> getAllCommentByUserId(@PathVariable(value = "id") long userId) {
        Collection<CommentDTO> comments = commentService.getAllCommentByUserId(userId);
        if (comments.size() > 0) {
            return ResponseEntity.ok().body(comments);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody SaveCommentDTO saveCommentDTO, HttpServletRequest request) {
        return ResponseEntity.ok().body(commentService.addComment(saveCommentDTO, request));
    }
    @PutMapping
    public ResponseEntity<?> updateComment(@RequestBody SaveCommentDTO saveCommentDTO, HttpServletRequest request) {
        return ResponseEntity.ok().body(commentService.updateComment(saveCommentDTO, request));
    }

}
