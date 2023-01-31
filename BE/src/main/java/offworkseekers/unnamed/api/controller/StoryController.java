package offworkseekers.unnamed.api.controller;

import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.response.RoleWithLineOfSceneResponse;
import offworkseekers.unnamed.api.response.StoryDetailResponse;
import offworkseekers.unnamed.api.response.StoryListResponse;
import offworkseekers.unnamed.api.response.StoryRoleResponse;
import offworkseekers.unnamed.api.service.StoryService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @GetMapping(value = "/api/v1/story/recommended/popular")
    public List<StoryListResponse> storyListByLike() {
        List<StoryListResponse> response = storyService.storyListRecommendedByLike();
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

    @GetMapping(value = "/api/v1/story/detail/roles")
    public List<StoryRoleResponse> storyRoles(@RequestParam(value = "story_id") Long storyId) {
        return storyService.storyRoleList(storyId);
    }

    @GetMapping(value = "/api/v1/story/detail/scripts")
    public List<RoleWithLineOfSceneResponse> storyRoleWithScript(@RequestParam(value = "story_id") Long storyId) {
        return storyService.roleWithLineOfSceneResponseList(storyId);
    }

    @GetMapping(value = "/api/v1/story/search")
    public List<StoryListResponse> storySearchList(@RequestParam(value = "keyword") String keyword, @RequestParam(value = "category") String categoryName) {
        return storyService.storySearchList(keyword, categoryName);
    }

    @PutMapping(value = "/api/v1/story/like")
    public void storyLike(@RequestBody @Valid Map<String, Object> param) {
        int storyId = (int) param.get("story_id");
        int division = (int) param.get("division");
        String userId = (String) param.get("user_id");

        storyService.editStoryLike(storyId, division, userId);
    }

    @DeleteMapping(value = "/api/v1/story/like")
    public void storyLikeCancel(@RequestBody @Valid Map<String, Object> param) {
        int storyId = (int) param.get("story_id");
        int division = (int) param.get("division");
        String userId = (String) param.get("user_id");

        storyService.deleteStoryLike(storyId, division, userId);
    }

    @PostMapping(value = "/api/v1/story/like")
    public boolean storyLikeStatus(@RequestBody @Valid Map<String, Object> param) {
        int storyId = (int) param.get("story_id");
        int division = (int) param.get("division");
        String userId = (String) param.get("user_id");

        if (storyService.getStoryLikeStatus(storyId, division, userId) == null) {
            return false;
        }
        return true;
    }

}
