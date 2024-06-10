package com.example.mytistory.controller;

import com.example.mytistory.domain.Article;
import com.example.mytistory.dto.AddArticleRequest;
import com.example.mytistory.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

}
