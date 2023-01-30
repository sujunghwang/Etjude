package offworkseekers.unnamed.api.service;

import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.response.MyPageStudiosResponse;
import offworkseekers.unnamed.db.repository.MyPageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepository myPageRepository;

    public List<MyPageStudiosResponse> getMyPageStudios(String userId){
        return myPageRepository.getMyPageStudios(userId);
    }
}
