package offworkseekers.unnamed.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import offworkseekers.unnamed.api.response.StoryDetailResponse;
import offworkseekers.unnamed.api.response.StoryListRecommendedByLikeResponse;
import offworkseekers.unnamed.db.entity.Story;
import offworkseekers.unnamed.db.repository.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StoryService {

    private final StoryRepository storyRepository;

    public List<StoryListRecommendedByLikeResponse> storyListRecommendedByLike() {
        List<StoryListRecommendedByLikeResponse> storyListRecommendedByLike = storyRepository.getStoryListRecommendedByLike();
        
        return storyListRecommendedByLike;
    }

    public StoryDetailResponse storyDetailResponse(Long storyId) {
        StoryDetailResponse storyDetail = storyRepository.getStroyDetail(storyId);
        return storyDetail;
    }

    public String storyDescResponse(Long storyId) {
        Optional<Story> story = storyRepository.findById(storyId);
        return story.get().getStoryDesc();
    }

}
