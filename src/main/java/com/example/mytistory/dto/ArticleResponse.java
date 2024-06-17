package com.example.mytistory.dto;

// 전체 블로그 글 조회요청에 응답하여 필요한 데이터를 담는 DTO class

import com.example.mytistory.domain.Article;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ArticleResponse {

    // 글 목록에 대해서 조회하는 것이기 때문에 content는 받지 않도록 한다.
    private final String title;
    private final LocalDateTime postTime;
    private final String category;

    public ArticleResponse(Article article){
        this.title = article.getTitle();
        this.category = article.getCategory();
        this.postTime = article.getPostTime();
    }

}
