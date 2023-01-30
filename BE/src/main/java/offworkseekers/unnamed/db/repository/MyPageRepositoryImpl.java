package offworkseekers.unnamed.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.response.MyPageStudiosResponse;
import offworkseekers.unnamed.db.entity.QTeamMember;
import offworkseekers.unnamed.db.entity.Story;
import org.hibernate.SQLQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static offworkseekers.unnamed.db.entity.QStudio.studio;
import static offworkseekers.unnamed.db.entity.QTeamMember.teamMember;

@RequiredArgsConstructor
public class MyPageRepositoryImpl implements MyPageRepositorySupport{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyPageStudiosResponse> getMyPageStudios(String userId) {
        List<MyPageStudiosResponse> result = queryFactory
                .select(Projections.constructor(MyPageStudiosResponse.class,
                        studio.studioId,
                        studio.story.storyThumbnailUrl,
                        studio.studioTitle,
                        studio.story.storyTitle,
                        studio.studioEndDate,
                        studio.studioEndDate
                ))
                .from(studio, teamMember)
                .where(teamMember.studio.studioId.eq(studio.studioId), teamMember.user.userId.eq(userId))
                .fetch();

        for (MyPageStudiosResponse temp : result) {
            temp.setStudioCreatedDate(temp.getStudioCreatedDate().minusDays(7));
        }

        return result;
    }
}
