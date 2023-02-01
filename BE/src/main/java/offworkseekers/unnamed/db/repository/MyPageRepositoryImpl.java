package offworkseekers.unnamed.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.dto.MyPageFollowDto;
import offworkseekers.unnamed.api.dto.MyPageLikesArticlesDto;
import offworkseekers.unnamed.api.dto.MyPageLikesStoriesDto;
import offworkseekers.unnamed.api.response.*;

import java.util.*;

import static offworkseekers.unnamed.db.entity.QArticle.article;
import static offworkseekers.unnamed.db.entity.QComment.comment;
import static offworkseekers.unnamed.db.entity.QFilm.film;
import static offworkseekers.unnamed.db.entity.QFollow.follow;
import static offworkseekers.unnamed.db.entity.QLikes.likes;
import static offworkseekers.unnamed.db.entity.QStory.story;
import static offworkseekers.unnamed.db.entity.QStudio.studio;
import static offworkseekers.unnamed.db.entity.QTeamMember.teamMember;
import static offworkseekers.unnamed.db.entity.QUser.user;

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

    @Override
    public MyPageLikesResponse getMyPageLikes(String userId) {
        List<MyPageLikesArticlesDto> articles = queryFactory
                .select(Projections.constructor(MyPageLikesArticlesDto.class,
                        article.articleId,
                        article.articleTitle,
                        article.user.userId,
                        article.user.picture,
                        article.articleViewCount,
                        article.articleCreatedDate))
                .from(article, likes)
                .where(likes.division.eq(0), likes.user.userId.eq(userId), likes.articleStoryId.eq(article.articleId.castToNum(Integer.class)))
                .fetch();

        List<MyPageLikesStoriesDto> stories = queryFactory
                .select(Projections.constructor(MyPageLikesStoriesDto.class,
                        story.storyId,
                        story.storyThumbnailUrl,
                        story.category.categoryName,
                        story.work.workTitle,
                        JPAExpressions.select(likes.count())
                                .from(likes)
                                .where(likes.division.eq(1),
                                        likes.articleStoryId.eq(story.storyId.castToNum(Integer.class)))))
                .from(story, likes)
                .where(likes.division.eq(1), likes.user.userId.eq(userId), likes.articleStoryId.eq(story.storyId.castToNum(Integer.class)))
                .fetch();

        return new MyPageLikesResponse(articles, stories);
    }

    @Override
    public List<MyPageCommentsResponse> getMyPageComments(String userId) {
        return queryFactory
                .select(Projections.constructor(MyPageCommentsResponse.class,
                        comment.user.userId,
                        comment.user.picture,
                        comment.commentContents,
                        comment.commentCreateTime,
                        comment.article.articleId))
                .from(comment)
                .where(comment.user.userId.eq(userId))
                .fetch();
    }

    @Override
    public MyPageResponse getMyPage(String userId) {

        MyPageSimpleResponse myPageSimpleResponse = queryFactory
                .select(Projections.constructor(MyPageSimpleResponse.class,
                        user.picture,
                        user.nickName
                        ))
                .from(user)
                .where(user.userId.eq(userId))
                .fetchOne();

        List<MyPageFollowDto> followings = queryFactory
                .select(Projections.constructor(MyPageFollowDto.class,
                        follow.following.userId,
                        follow.following.picture))
                .from(follow)
                .where(follow.follower.userId.eq(userId))
                .fetch();

        List<MyPageFollowDto> followers = queryFactory
                .select(Projections.constructor(MyPageFollowDto.class,
                        follow.follower.userId,
                        follow.follower.picture))
                .from(follow)
                .where(follow.following.userId.eq(userId))
                .fetch();

        return new MyPageResponse(myPageSimpleResponse, followings.size(), followers.size(), followings, followers);

    }
}
