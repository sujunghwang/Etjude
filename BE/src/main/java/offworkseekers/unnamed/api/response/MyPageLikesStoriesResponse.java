package offworkseekers.unnamed.api.response;

import lombok.Getter;

@Getter
public class MyPageLikesStoriesResponse {

    Long storyId;
    String storyThumbnailUrl;
    String categoryName;
    String workName;
    Long storyLikeCount;

    public MyPageLikesStoriesResponse(Long storyId, String storyThumbnailUrl, String categoryName, String workName, Long storyLikeCount) {
        this.storyId = storyId;
        this.storyThumbnailUrl = storyThumbnailUrl;
        this.categoryName = categoryName;
        this.workName = workName;
        this.storyLikeCount = storyLikeCount;
    }
}
