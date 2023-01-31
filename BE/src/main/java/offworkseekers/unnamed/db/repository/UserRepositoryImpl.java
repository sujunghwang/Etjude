package offworkseekers.unnamed.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.response.UserSearchResponse;
import offworkseekers.unnamed.db.entity.User;

import java.util.ArrayList;
import java.util.List;

import static offworkseekers.unnamed.db.entity.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositorySupport{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<UserSearchResponse> findUserSimple(String keyword) {
        List<User> fetch = queryFactory
                .selectFrom(user)
                .where(user.email.contains(keyword))
                .fetch();

        List<UserSearchResponse> userSearchResponses = new ArrayList<>();
        for (User fetch1 : fetch) {
            userSearchResponses.add(
                    UserSearchResponse.builder()
                            .userId(fetch1.getUserId())
                            .nickname(fetch1.getUserId())
                            .userEmail(fetch1.getEmail())
                            .build()
            );
        }
        return userSearchResponses;

    }
}
