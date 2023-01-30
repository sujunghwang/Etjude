package offworkseekers.unnamed.api.controller;

import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.response.MyPageArticlesResponse;
import offworkseekers.unnamed.api.response.MyPageFilmsResponse;
import offworkseekers.unnamed.api.response.MyPageFilmsWithMembersResponse;
import offworkseekers.unnamed.api.response.MyPageStudiosResponse;
import offworkseekers.unnamed.api.service.MyPageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @PostMapping("/api/v1/mypage/studio")
    public List<MyPageStudiosResponse> getMyPageStudios(@RequestBody Map<String, String> request){
        return myPageService.getMyPageStudios(request.get("user_id"));
    }

    @PostMapping("/api/v1/mypage/film")
    public List<MyPageFilmsWithMembersResponse> getMyPageFilms(@RequestBody Map<String, String> request){
        return myPageService.getMyPageFilms(request.get("user_id"));
    }

    @PostMapping("/api/v1/mypage/articles")
    public List<MyPageArticlesResponse> getMyPageArticles(@RequestBody Map<String, String> request){
        return myPageService.getMyPageArticle(request.get("user_id"));
    }
}
