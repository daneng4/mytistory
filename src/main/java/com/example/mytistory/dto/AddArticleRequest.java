package com.example.mytistory.dto;

import com.example.mytistory.domain.Article;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;
    private String category;
    private LocalDateTime postTime;

    // DTO -> Entity 메서드, 블로그 글 추가 용도
    public Article toEntity(){
        return Article.builder()
            .title(title)
            .content(content)
            .category(category)
            .build();
    }
    
}
