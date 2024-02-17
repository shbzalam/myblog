package com.myblog.controller;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }

    // http://localhost:8080/api/post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity <?> savePost (@Valid @RequestBody PostDto postDto, BindingResult result){

        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.savePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED); //201
    }

    // http://localhost:8080/api/post/1
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity <String> deletePost (@PathVariable("id") long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post is deleted successfully", HttpStatus.OK);
    }

    // http://localhost:8080/api/post/1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity <PostDto> updatePost (@PathVariable("id") long id,
                                                @RequestBody PostDto postDto){
        PostDto dto = postService.updatePost (id, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // http://localhost:8080/api/post/1
    @GetMapping("/{id}")
    public ResponseEntity <PostDto> getPostById(@PathVariable ("id") long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //
    
    @GetMapping
    public PostResponse getPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue ="asc", required = false) String sortDir
            ){

        PostResponse postResponse = postService.getPosts(pageNo, pageSize, sortBy, sortDir);
        return postResponse;
    }
}
