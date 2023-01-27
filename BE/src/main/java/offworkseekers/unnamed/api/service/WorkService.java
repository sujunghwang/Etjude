package offworkseekers.unnamed.api.service;

import offworkseekers.unnamed.db.entity.Work;
import offworkseekers.unnamed.db.entity.WorkSearch;
import offworkseekers.unnamed.db.repository.WorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService {

    WorkRepository workRepository;


    public List<Work> test(WorkSearch workSearch) {

        Long workId = 1L;
        workRepository.findByWorkId(workId);

        return null;
    }
}
