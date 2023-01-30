package offworkseekers.unnamed.api.response;

import lombok.Getter;

import java.util.List;

@Getter
public class MyPageLikesResponse {

    List<MyPageLikesArticlesResponse> articles;
    List<MyPageLikesStoriesResponse> stories;

    public MyPageLikesResponse(List<MyPageLikesArticlesResponse> articles, List<MyPageLikesStoriesResponse> stories) {
        this.articles = articles;
        this.stories = stories;
    }
}
