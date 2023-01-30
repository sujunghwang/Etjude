package offworkseekers.unnamed.db.repository;

import offworkseekers.unnamed.api.response.StudioNavBarResponse;

public interface StudioRepositorySupport {

    StudioNavBarResponse findStudioNavbar(Long studioId, Long userId);
}
