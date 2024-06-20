package com.example.mytistory.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.mytistory.domain.Article;
import com.example.mytistory.dto.AddArticleRequest;
import com.example.mytistory.dto.UpdateArticleRequest;
import com.example.mytistory.repository.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ArticleRepository articleRepository;

    @BeforeEach
    public void setMockMvc(){
        // webAppContextSetup을 통해 실제 애플리케이션과 동일한 설정을 적용하여 웹 계층 테스트 진행
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();
        articleRepository.deleteAll();
    }

    @DisplayName("블로그 글 추가에 성공한다")
    @Test
    public void addArticleTest() throws Exception{
        //given : 메소드 테스트에 필요한 요청 객체 만들기
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final String category = "category";
        //final LocalDateTime time = LocalDateTime.now();
        LocalDateTime time = LocalDateTime.of(2011, 1, 1, 1, 1);


        final AddArticleRequest request = new AddArticleRequest(title, content, category, time);
        // 객체 JSON으로 직렬화 수행 Java -> JSON
        final String requestBody = objectMapper.writeValueAsString(request);

        //when : 메소드에 API 요청 보내기
        ResultActions resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody));

        //then : 예상했던 응답코드 확인하기
        resultActions.andExpect(status().isCreated());
        List<Article> articles = articleRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
        assertThat(articles.get(0).getCategory()).isEqualTo(category);
    }

    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception{
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final String category = "category";
        final LocalDateTime time = LocalDateTime.now();

        articleRepository.save(Article.builder()
            .title(title)
            .content(content)
            .category(category)
            .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
            .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value(title))
            .andExpect(jsonPath("$[0].category").value(category));
    }

    @DisplayName("findArticle: 단일 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception{
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        final String category = "category";
        final LocalDateTime time = LocalDateTime.now();

        Article savedArticle = articleRepository.save(Article.builder()
            .title(title)
            .category(category)
            .content(content)
            .build());

        // when
        ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(title))
            .andExpect(jsonPath("$.category").value(category))
            .andExpect(jsonPath("$.content").value(content));
    }

    @DisplayName("updateArticle: 블로그 글을 수정한다.")
    @Test
    public void updateArticle() throws Exception{
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        final String category = "category";
        final LocalDateTime time = LocalDateTime.now();

        Article article = articleRepository.save(Article.builder()
            .title(title)
            .content(content)
            .category(category)
            .build());

        // new Article
        final String newTitle = "new title";
        final String newContent = "new content";
        final String newCategory = "new category";

        // dto
        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent, newCategory);

        // when
        ResultActions resultActions = mockMvc.perform(put(url, article.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions.andExpect(status().isOk());

        Article newArticle = articleRepository.findById(article.getId()).get();

        assertThat(newArticle.getTitle()).isEqualTo(newTitle);
        assertThat(newArticle.getContent()).isEqualTo(newContent);
        assertThat(newArticle.getCategory()).isEqualTo(newCategory);
    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception{
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        final String category = "category";
        final LocalDateTime time = LocalDateTime.now();

        Article savedArticle = articleRepository.save(Article.builder()
            .title(title)
            .content(content)
            .category(category)
            .build());

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
            .andExpect(status().isOk());

        // then
        List<Article> articles = articleRepository.findAll();
        assertThat(articles).isEmpty();

    }

}