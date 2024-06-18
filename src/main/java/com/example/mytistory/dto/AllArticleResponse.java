package com.example.mytistory.dto;

import com.example.mytistory.domain.Article;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class AllArticleResponse {

    // 글 목록에 대해서 조회하는 것이기 때문에 content는 받지 않도록 한다.
    private final String title;
    private final LocalDateTime postTime;
    private final String category;

    public AllArticleResponse(Article article){
        this.title = article.getTitle();
        this.category = article.getCategory();
        this.postTime = article.getPostTime();
    }
}
