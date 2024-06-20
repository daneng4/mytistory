package com.example.mytistory.controller;

import com.example.mytistory.domain.Article;
import com.example.mytistory.dto.ArticleListViewResponse;
import com.example.mytistory.dto.ArticleViewResponse;
import com.example.mytistory.service.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ArticleViewController {

    private final ArticleService articleService;

    @GetMapping("/articles")
    public String getArticles(Model model){
        List<ArticleListViewResponse> articles = articleService.findAll().stream()
            .map(ArticleListViewResponse::new)
            .toList();

        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model){
        Article article = articleService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

}
