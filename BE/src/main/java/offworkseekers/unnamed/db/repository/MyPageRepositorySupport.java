package offworkseekers.unnamed.db.repository;

import offworkseekers.unnamed.api.response.MyPageArticlesResponse;
import offworkseekers.unnamed.api.response.MyPageFilmsResponse;
import offworkseekers.unnamed.api.response.MyPageFilmsWithMembersResponse;
import offworkseekers.unnamed.api.response.MyPageStudiosResponse;

import java.util.List;

public interface MyPageRepositorySupport {

    List<MyPageStudiosResponse> getMyPageStudios(String userId);
    List<MyPageFilmsWithMembersResponse> getMyPageFilms(String userId);
    List<MyPageArticlesResponse> getMyPageArticles(String userId);

}
