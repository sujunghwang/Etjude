package offworkseekers.unnamed.api.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MyPageLikesArticlesResponse {

    Long articeId;
    String articleTitle;
    String userId;
    String userPhotoUrl;
    int articleViewCount;
    LocalDate articleCreatedDate;

    public MyPageLikesArticlesResponse(Long articeId, String articleTitle, String userId, String userPhotoUrl, int articleViewCount, LocalDate articleCreatedDate) {
        this.articeId = articeId;
        this.articleTitle = articleTitle;
        this.userId = userId;
        this.userPhotoUrl = userPhotoUrl;
        this.articleViewCount = articleViewCount;
        this.articleCreatedDate = articleCreatedDate;
    }
}
