package com.example.mytistory.service;

import com.example.mytistory.domain.Article;
import com.example.mytistory.dto.AddArticleRequest;
import com.example.mytistory.repository.ArticleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    // 블로그 글 추가
    public Article save(AddArticleRequest request){
        return articleRepository.save(request.toEntity());
    }

    // 블로그 글 조회
    public List<Article> findAll(){
        return articleRepository.findAll();
    }

}
