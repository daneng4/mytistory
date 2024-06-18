package com.example.mytistory.service;

import com.example.mytistory.domain.Article;
import com.example.mytistory.dto.AddArticleRequest;
import com.example.mytistory.dto.UpdateArticleRequest;
import com.example.mytistory.repository.ArticleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    // 블로그 글 추가
    public Article save(AddArticleRequest request){
        return articleRepository.save(request.toEntity());
    }

    // 블로그 글 조회
    public Article findById(Long id){
        return articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 블로그 글 목록 조회
    public List<Article> findAll(){
        return articleRepository.findAll();
    }

    // 블로그 글 수정
    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent(), request.getCategory());

        return article;
    }

    // 블로그 글 삭제


}
