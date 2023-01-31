package offworkseekers.unnamed.api.controller;

import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.request.ArticleCreateRequest;
import offworkseekers.unnamed.api.response.*;
import offworkseekers.unnamed.api.service.ArticleService;
import offworkseekers.unnamed.db.entity.Article;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final ArticleService articleService;

    @PutMapping(value = "/api/v1/board/create")
    public CreateArticleResponse saveArticle(@RequestBody @Valid ArticleCreateRequest request) {
        Article article = articleService.createArticle(request);
        return new CreateArticleResponse(article.getArticleId(), article.getArticleContent(), article.getArticleTitle(), article.getArticleThumbnailUrl());
    }

    @GetMapping(value = "/api/v1/board")
    public List<ArticleWithFilmUrlResponse> getArticleList() {
        List<ArticleWithFilmUrlResponse> articleList = articleService.getArticleList();
        return articleList;
    }

    @GetMapping(path = "/api/v1/board/search/{keyword}")
    public List<SearchFilmResponse> getSearchArticleList(@PathVariable String keyword){
        List<SearchFilmResponse> articleList = articleService.getSearchArticleList(keyword);
        return articleList;

    }

    @GetMapping(path = "/api/v1/board/detail/{articleId}")
    public FilmDetailResponse getFilmDetail(@PathVariable Long articleId){
        FilmDetailResponse filmDetail = articleService.getFilmDetail(articleId);
        return filmDetail;
    }

    @GetMapping(value = "/api/v1/board/popular")
    public List<PopularFilmResponse> getPopularArticleList() {
        List<PopularFilmResponse> articleList = articleService.getPopularArticleList();
        return articleList;
    }

    @PostMapping(value = "api/v1/board/modal")
    public List<MyFilmListResponse> getModalFilmList(@RequestBody @Valid Map<String, String> param){
        String userId = param.get("user_id");
        return articleService.getModalFilmList(userId);
    }


}
