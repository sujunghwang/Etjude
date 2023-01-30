package offworkseekers.unnamed.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import offworkseekers.unnamed.api.request.StudioIdWithUserIdRequest;
import offworkseekers.unnamed.api.response.StudioNavBarResponse;
import offworkseekers.unnamed.api.service.StudioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StudioController {


    private final StudioService studioService;

    @PostMapping(value = "/api/v1/studio/navbar")
    public StudioNavBarResponse getStudioNavbar(@RequestBody @Valid StudioIdWithUserIdRequest request) {
        log.debug("request = {}", request);
        return studioService.getStudioNavbar(request.getStudioId(), request.getUserId());
    }

}
