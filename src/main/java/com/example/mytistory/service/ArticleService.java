package com.example.mytistory.service;

import com.example.mytistory.domain.Article;
import com.example.mytistory.dto.AddArticleRequest;
import com.example.mytistory.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article save(AddArticleRequest request){
        return articleRepository.save(request.toEntity());
    }

}
