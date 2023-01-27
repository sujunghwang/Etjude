package offworkseekers.unnamed.api.controller;

import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.service.StoryService;
import offworkseekers.unnamed.db.entity.Story;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @GetMapping(value = "/api/v1/story/recommended/popular")
    public List<Story> storyListByLike() {
        List<Story> stories = storyService.storyListByLike();
        return stories;
    }


}
