package offworkseekers.unnamed.db.repository;

import offworkseekers.unnamed.api.response.StoryDetailResponse;
import offworkseekers.unnamed.api.response.StoryListRecommendedByLikeResponse;

import java.util.List;

public interface StoryRepositorySupport {

    List<StoryListRecommendedByLikeResponse> getStoryListRecommendedByLike();

    StoryDetailResponse getStoryDetail(Long storyId);
}
