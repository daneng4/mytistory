package com.example.mytistory.dto;

// 전체 블로그 글 조회요청에 응답하여 필요한 데이터를 담는 DTO class

import com.example.mytistory.domain.Article;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ArticleResponse {

    // 단일 블로그 글 조회
    private final String title;
    private final String category;
    private final String content;

    public ArticleResponse(Article article){
        this.title = article.getTitle();
        this.category = article.getCategory();
        this.content = article.getContent();
    }

}
