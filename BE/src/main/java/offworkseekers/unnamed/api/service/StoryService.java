package offworkseekers.unnamed.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import offworkseekers.unnamed.api.response.RoleWithLineOfSceneResponse;
import offworkseekers.unnamed.api.response.StoryDetailResponse;
import offworkseekers.unnamed.api.response.StoryListRecommendedByLikeResponse;
import offworkseekers.unnamed.api.response.StoryRoleResponse;
import offworkseekers.unnamed.db.entity.Role;
import offworkseekers.unnamed.db.entity.Story;
import offworkseekers.unnamed.db.repository.RoleRepository;
import offworkseekers.unnamed.db.repository.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StoryService {

    private final StoryRepository storyRepository;

    private final RoleRepository roleRepository;

    public List<StoryListRecommendedByLikeResponse> storyListRecommendedByLike() {
        List<StoryListRecommendedByLikeResponse> storyListRecommendedByLike = storyRepository.getStoryListRecommendedByLike();
        
        return storyListRecommendedByLike;
    }

    public StoryDetailResponse storyDetailResponse(Long storyId) {
        StoryDetailResponse storyDetail = storyRepository.getStoryDetail(storyId);
        return storyDetail;
    }

    public String storyDescResponse(Long storyId) {
        Optional<Story> story = storyRepository.findById(storyId);
        return story.get().getStoryDesc();
    }

    public List<StoryRoleResponse> storyRoleList(Long storyId) {
        List<Role> roles = roleRepository.getStoryRoleList(storyId);

        List<StoryRoleResponse> roleList = new ArrayList<>();
        for (Role role : roles) {
            roleList.add(StoryRoleResponse.builder()
                    .roleId(role.getRoleId())
                    .roleName(role.getRoleName())
                    .roleDesc(role.getRoleDesc())
                    .rolePhotoUrl(role.getRolePhotoUrl())
                    .build());
        }

        return roleList;
    }

    public List<RoleWithLineOfSceneResponse> roleWithLineOfSceneResponseList(Long storyId) {
        List<RoleWithLineOfSceneResponse> roleWithLines = roleRepository.getRoleWithLines(storyId);
        return roleWithLines;
    }



}
