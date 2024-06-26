package com.example.mytistory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter

// 글 수정 요청을 받을 DTO
public class UpdateArticleRequest {

    private String title;
    private String content;
    private String category;

}
