package offworkseekers.unnamed.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import offworkseekers.unnamed.api.request.CreateFilmRequest;
import offworkseekers.unnamed.api.request.StudioCreateRequest;
import offworkseekers.unnamed.api.request.StudioIdWithUserIdRequest;
import offworkseekers.unnamed.api.response.*;
import offworkseekers.unnamed.api.service.StoryService;
import offworkseekers.unnamed.api.service.StudioService;
import offworkseekers.unnamed.db.entity.Studio;
import offworkseekers.unnamed.db.repository.StudioRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StudioController {


    private final StudioService studioService;
    private final StoryService storyService;

    private final StudioRepository studioRepository;

    @PostMapping(value = "/api/v1/studio/navbar")
    public StudioNavBarResponse getStudioNavbar(@RequestBody @Valid StudioIdWithUserIdRequest request) {
        log.debug("request = {}", request);
        return studioService.getStudioNavbar(request.getStudioId(), request.getUserId());
    }


    @PostMapping(value = "/api/v1/studio/create")
    public void createStudio(@RequestBody @Valid StudioCreateRequest studioCreateRequest) {
        studioService.saveStudio(studioCreateRequest);
    }

    @PostMapping(value = "/api/v1/studio/search")
    public List<UserSearchResponse> searchUser(@RequestBody @Valid Map<String, String> param){
        String keyword = param.get("keyword");
        return studioService.searchUser(keyword);
    }

    @PostMapping(value = "/api/v1/studio/story/video")
    public String getStudioVideoUrl(@RequestBody @Valid Map<String, Long> param) {
        Long studioId = param.get("studio_id");
        return studioService.getStudioStoryVideoUrl(studioId);
    }

    /**
     * studio ID로 studio를 조회하므로 service layer로의 이동이 불필요하다고 판단하여,
     * studio controller에서 repository를 참조하여 studio를 조회해옴.
     */
    @PostMapping(value = "/api/v1/studio/story/scripts")
    public List<RoleWithLineOfSceneResponse> getStudioScripts(@RequestBody @Valid Map<String, Long> param) {
        Long studioId = param.get("studio_id");
        Studio studio = studioRepository.findById(studioId).orElse(null);
        Long storyId = studio.getStory().getStoryId();
        return storyService.roleWithLineOfSceneResponseList(storyId);
    }

    @PostMapping(value = "/api/v1/studio/scripts")
    public StudioSettingResponse getStudioSetting(@RequestBody @Valid StudioIdWithUserIdRequest studioIdWithUserIdRequest) {
        StudioSettingResponse studioSetting = studioService.getStudioSetting(studioIdWithUserIdRequest.getStudioId(), studioIdWithUserIdRequest.getUserId());
        return studioSetting;
    }

    @GetMapping(value = "/api/v1/studio/film")
    public List<StudioFilmListResponse> getStudioFilmList(@RequestBody @Valid Map<String, Long> param) {
        Long studioId = param.get("studio_id");
        return studioService.getStudioFilmList(studioId);
    }

    @PostMapping(value = "/api/v1/studio/recording")
    public List<StudioRecordListResponse> getStudioRecordList(@RequestBody @Valid Map<String, Long> param){
        Long studioId = param.get("studio_id");
        return studioService.getStoryRecordingList(studioId);
    }

    @PostMapping(value="/api/v1/studio/film")
    public void createFilm(@RequestBody CreateFilmRequest createFilmRequest) throws Exception {
        // 요청 들어오면 s3 위치 찾아가서 merge.txt 파일 작성
        // ffmpeg 활용해서 비디오 병합
        // 병합된 비디오의 presigned url 리턴
        // int teamId, int storyId 이렇게만 넘겨주면 될 듯
//        String filePath = studioService.createMergeTxt(createFilmRequest.getStudioId(), createFilmRequest.getStoryId());

        studioService.test();

//        return studioService.createFilm("");

    }
}
