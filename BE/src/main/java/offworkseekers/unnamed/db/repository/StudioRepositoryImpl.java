package offworkseekers.unnamed.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.response.StudioNavBarResponse;
import offworkseekers.unnamed.api.response.StudioSettingResponse;

import static offworkseekers.unnamed.db.entity.QStudio.studio;
import static offworkseekers.unnamed.db.entity.QUser.user;

@RequiredArgsConstructor
public class StudioRepositoryImpl implements StudioRepositorySupport{

    private final JPAQueryFactory queryFactory;


    @Override
    public StudioNavBarResponse findStudioNavbar(Long studioId, String userId) {
        Tuple tuple = queryFactory
                .select(studio.studioTitle, user.nickName, user.picture)
                .from(studio, user)
                .where(studio.studioId.eq(studioId),
                        user.userId.eq(userId))
                .fetchOne();

        return StudioNavBarResponse.builder()
                .userId(userId)
                .studioTitle(tuple.get(studio.studioTitle))
                .nickname(tuple.get(user.nickName))
                .userPhotoUrl(tuple.get(user.picture))
                .build();
    }

    @Override
    public StudioSettingResponse findStudioSetting(Long studioId, String userId) {
        Tuple tuple = queryFactory
                .select(studio.studioTitle, studio.studioEndDate, user.nickName, user.picture)
                .from(studio, user)
                .where(
                        studio.studioId.eq(studioId),
                        user.userId.eq(userId)
                )
                .fetchOne();

        return StudioSettingResponse.builder()
                .studioTitle(tuple.get(studio.studioTitle))
                .studioCreateDate(tuple.get(studio.studioEndDate).minusDays(7))
                .studioEndDate(tuple.get(studio.studioEndDate))
                .nickname(tuple.get(user.nickName))
                .userPhotoUrl(tuple.get(user.picture))
                .build();

    }
}
