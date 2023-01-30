package offworkseekers.unnamed.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.response.MyPageArticlesResponse;
import offworkseekers.unnamed.api.response.MyPageFilmsResponse;
import offworkseekers.unnamed.api.response.MyPageFilmsWithMembersResponse;
import offworkseekers.unnamed.api.response.MyPageStudiosResponse;

import java.util.*;

import static offworkseekers.unnamed.db.entity.QArticle.article;
import static offworkseekers.unnamed.db.entity.QFilm.film;
import static offworkseekers.unnamed.db.entity.QStory.story;
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

    @Override
    public List<MyPageFilmsWithMembersResponse> getMyPageFilms(String userId) {

        List<MyPageFilmsResponse> myFilms = queryFactory
                .select(Projections.constructor(MyPageFilmsResponse.class,
                        film.filmId,
                        story.category.categoryName,
                        story.work.workTitle,
                        story.storyTitle,
                        film.studio.studioTitle,
                        film.filmVideoUrl,
                        film.filmCreatedDate,
                        film.studio.studioEndDate))
                .from(film, story)
                .where(film.user.userId.eq(userId), film.studio.story.eq(story))
                .fetch();

        List<MyPageFilmsWithMembersResponse> result = new ArrayList<>();

        for (int i = 0, len = myFilms.size(); i < len; i++) {
            MyPageFilmsResponse temp = myFilms.get(i);
            List<String> Members = queryFactory
                    .select(teamMember.user.nickName)
                    .from(studio, teamMember)
                    .where(studio.studioTitle.eq(temp.getStudioTitle()), teamMember.studio.eq(studio))
                    .fetch();

            result.add(i, new MyPageFilmsWithMembersResponse(temp, Members));
        }
        return result;
    }

    @Override
    public List<MyPageArticlesResponse> getMyPageArticles(String userId) {

        return queryFactory
                .select(Projections.constructor(MyPageArticlesResponse.class,
                        article.articleId,
                        article.articleTitle,
                        article.user.userId,
                        article.user.picture,
                        article.articleViewCount,
                        article.articleCreatedDate))
                .from(article)
                .where(article.user.userId.eq(userId))
                .fetch();

    }
}
