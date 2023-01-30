package offworkseekers.unnamed.db.repository;

import offworkseekers.unnamed.api.response.StudioNavBarResponse;
import offworkseekers.unnamed.api.response.StudioSettingResponse;

public interface StudioRepositorySupport {

    StudioNavBarResponse findStudioNavbar(Long studioId, String userId);

    StudioSettingResponse findStudioSetting(Long studioId, String userId);
}
