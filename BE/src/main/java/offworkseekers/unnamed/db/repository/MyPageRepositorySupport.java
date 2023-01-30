package offworkseekers.unnamed.db.repository;

import offworkseekers.unnamed.api.response.MyPageStudiosResponse;

import java.util.List;

public interface MyPageRepositorySupport {

    List<MyPageStudiosResponse> getMyPageStudios(String userId);
}
