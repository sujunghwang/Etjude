package offworkseekers.unnamed.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import offworkseekers.unnamed.db.entity.Story;
import offworkseekers.unnamed.db.repository.StoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StoryService {

    private final StoryRepository storyRepository;

    public List<Story> storyListByLike() {
        List<Story> storyList = storyRepository.findAll(Sort.by(Sort.Direction.DESC, "storyLike"));
        log.debug("storyList = {}", storyList);
        return storyList;
    }

}
