package offworkseekers.unnamed.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import offworkseekers.unnamed.api.request.StudioCreateRequest;
import offworkseekers.unnamed.api.response.StudioNavBarResponse;
import offworkseekers.unnamed.api.response.UserSearchResponse;
import offworkseekers.unnamed.db.repository.UserRepository;
import offworkseekers.unnamed.db.entity.Story;
import offworkseekers.unnamed.db.entity.Studio;
import offworkseekers.unnamed.db.entity.User;
import offworkseekers.unnamed.db.repository.StoryRepository;
import offworkseekers.unnamed.db.repository.StudioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StudioService {

    private final StudioRepository studioRepository;
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;

    public StudioNavBarResponse getStudioNavbar(Long studioId, String userId){
        return studioRepository.findStudioNavbar(studioId, userId);
    }

    @Transactional
    public void saveStudio(StudioCreateRequest request) {
        Story story = storyRepository.findById(request.getStoryId()).orElse(null);
        Studio studio = request.toStudioEntity();
        studio.connectStory(story);

        List<User> userList = new ArrayList<>();
        List<String> teamMembersIds = request.getTeamMembersIds();
        for (String teamMembersId : teamMembersIds) {
            User user = userRepository.findById(teamMembersId).orElse(null);
            userList.add(user);
        }
        studio.addTeamMember(userList);
        studioRepository.save(studio);
    }

    public List<UserSearchResponse> searchUser(String keyword) {
        return userRepository.findUserSimple(keyword);
    }

    public String getStudioStoryVideoUrl(Long studioId) {
        Studio studio = studioRepository.findById(studioId).orElse(null);
        Story story = storyRepository.findById(studio.getStory().getStoryId()).orElse(null);

        return story.getStoryVideoUrl();
    }

}
