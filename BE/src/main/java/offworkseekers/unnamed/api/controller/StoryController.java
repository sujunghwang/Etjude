package offworkseekers.unnamed.api.controller;

import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.response.StoryDetailResponse;
import offworkseekers.unnamed.api.response.StoryListRecommendedByLikeResponse;
import offworkseekers.unnamed.api.service.StoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @GetMapping(value = "/api/v1/story/recommended/popular")
    public List<StoryListRecommendedByLikeResponse> storyListByLike() {
        List<StoryListRecommendedByLikeResponse> response = storyService.storyListRecommendedByLike();
        return response;
    }

    @GetMapping(value = "/api/v1/story/detail")
    public StoryDetailResponse storyDetail(@RequestParam(value = "story_id") Long storyId) {
        StoryDetailResponse storyDetailResponse = storyService.storyDetailResponse(storyId);
        return storyDetailResponse;
    }

    @GetMapping(value = "/api/v1/story/detail/desc")
    public String storyDesc(@RequestParam(value = "story_id") Long storyId) {
        return storyService.storyDescResponse(storyId);
    }


}
