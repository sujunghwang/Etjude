package offworkseekers.unnamed.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.response.StoryDetailResponse;
import offworkseekers.unnamed.api.response.StoryListResponse;

import java.util.ArrayList;
import java.util.List;

import static offworkseekers.unnamed.db.entity.QCategory.category;
import static offworkseekers.unnamed.db.entity.QStory.story;
import static offworkseekers.unnamed.db.entity.QStudio.studio;
import static offworkseekers.unnamed.db.entity.QWork.work;

@RequiredArgsConstructor
public class StoryRepositoryImpl implements StoryRepositorySupport{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<StoryListResponse> getStoryListRecommendedByLike() {
        List<Tuple> fetch = queryFactory
                .select(story.storyId, story.storyThumbnailUrl, story.storyLike, story.storyTitle, category.categoryName, work.workTitle)
                .from(story, category, work)
                .where(story.category.categoryId.eq(category.categoryId),
                        story.work.workId.eq(work.workId))
                .orderBy(
                        story.storyLike.desc()
                )
                .fetch();

        List<StoryListResponse> storyListRecommendedByLikeResponse = new ArrayList<>();

        for (Tuple tuple : fetch) {
            storyListRecommendedByLikeResponse.add(StoryListResponse.builder()
                    .storyId(tuple.get(story.storyId))
                    .storyTitle(tuple.get(story.storyTitle))
                    .storyThumbnailUrl(tuple.get(story.storyThumbnailUrl))
                    .likeCount(tuple.get(story.storyLike))
                    .categoryName(tuple.get(category.categoryName))
                    .workTitle(tuple.get(work.workTitle))
                    .build()
            );
        }

        return storyListRecommendedByLikeResponse;
    }

    @Override
    public StoryDetailResponse getStoryDetail(Long storyId) {
        Tuple tuple = queryFactory
                .select(story.storyVideoUrl, category.categoryName, work.workTitle, story.storyTitle, story.storySummary, story.storyLike)
                .from(story, work, category)
                .where(story.storyId.eq(storyId),
                        story.category.categoryId.eq(category.categoryId),
                        story.work.workId.eq(work.workId)
                )
                .fetchOne();

        Long studioStack = queryFactory
                .select(studio.count())
                .from(studio)
                .where(studio.story.storyId.eq(storyId))
                .fetchOne();

        StoryDetailResponse storyDetail = StoryDetailResponse.builder()
                .storyLikeCount(tuple.get(story.storyLike))
                .studioStack(Math.toIntExact(studioStack))
                .storySummary(tuple.get(story.storySummary))
                .storyVideoUrl(tuple.get(story.storyVideoUrl))
                .storyTitle(tuple.get(story.storyTitle))
                .categoryName(tuple.get(category.categoryName))
                .workTitle(tuple.get(work.workTitle))
                .build();

        return storyDetail;
    }

    @Override
    public List<StoryListResponse> getStorySearchList(String keyword, String categoryName) {
        List<Tuple> fetch = queryFactory
                .select(story.storyId, story.storyThumbnailUrl, story.storyLike, story.storyTitle, category.categoryName, work.workTitle)
                .from(story, category, work)
                .where(
                        story.category.categoryName.eq(categoryName),
                        story.storyTitle.contains(keyword)
                )
                .groupBy(story.storyId)
                .fetch();

        List<StoryListResponse> storySearchResult = new ArrayList<>();

        for (Tuple tuple : fetch) {
            storySearchResult.add(
                    StoryListResponse.builder()
                    .storyId(tuple.get(story.storyId))
                    .storyTitle(tuple.get(story.storyTitle))
                    .storyThumbnailUrl(tuple.get(story.storyThumbnailUrl))
                    .likeCount(tuple.get(story.storyLike))
                    .categoryName(tuple.get(category.categoryName))
                    .workTitle(tuple.get(work.workTitle))
                    .build()
            );
        }
        return storySearchResult;

    }
}
