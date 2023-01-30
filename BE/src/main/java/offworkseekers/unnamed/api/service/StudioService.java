package offworkseekers.unnamed.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import offworkseekers.unnamed.api.response.StudioNavBarResponse;
import offworkseekers.unnamed.db.repository.StudioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StudioService {

    private final StudioRepository studioRepository;

    public StudioNavBarResponse getStudioNavbar(Long studioId, Long userId){
        return studioRepository.findStudioNavbar(studioId, userId);
    }


}
