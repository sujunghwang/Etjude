package offworkseekers.unnamed.api.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class StoryListRecommendedByLikeResponse {
    private Long storyId;
    private String StoryThumbnailUrl;
    private String CategoryName;
    private String StoryTitle;
    private String workTitle;
    private int likeCount;

    @Builder
    public StoryListRecommendedByLikeResponse(Long storyId, String storyThumbnailUrl, String categoryName, String storyTitle, String workTitle, int likeCount) {
        this.storyId = storyId;
        StoryThumbnailUrl = storyThumbnailUrl;
        CategoryName = categoryName;
        StoryTitle = storyTitle;
        this.workTitle = workTitle;
        this.likeCount = likeCount;
    }
}
