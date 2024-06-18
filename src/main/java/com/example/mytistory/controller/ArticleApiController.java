package com.example.mytistory.controller;

import com.example.mytistory.domain.Article;
import com.example.mytistory.dto.AddArticleRequest;
import com.example.mytistory.dto.ArticleResponse;
import com.example.mytistory.dto.UpdateArticleRequest;
import com.example.mytistory.service.ArticleService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleService articleService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){

        Article savedArticle = articleService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = articleService.findAll()
            .stream()
            .map(ArticleResponse::new)
            .toList();

        return ResponseEntity.ok()
            .body(articles);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id){
        Article article = articleService.findById(id);

        return ResponseEntity.ok()
            .body(new ArticleResponse(article));
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody
        UpdateArticleRequest request){

        Article updatedArticle = articleService.update(id, request);

        return ResponseEntity.ok()
            .body(updatedArticle);
    }
}
