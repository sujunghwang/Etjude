package offworkseekers.unnamed.db.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.response.GetWorkResponse;
import offworkseekers.unnamed.api.response.StoriesOfWork;
import offworkseekers.unnamed.api.response.WorkOrderByRandomResponse;
import offworkseekers.unnamed.api.response.WorkSearchResponse;

import java.util.List;

import static offworkseekers.unnamed.db.entity.QLikes.likes;
import static offworkseekers.unnamed.db.entity.QStory.story;
import static offworkseekers.unnamed.db.entity.QWork.work;

// 쿼리 구현
@RequiredArgsConstructor
public class WorkRepositoryImpl implements WorkRepositorySupport {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WorkOrderByRandomResponse> getWorkListRandom() {
        List<WorkOrderByRandomResponse> result = queryFactory
                .select(Projections.constructor(WorkOrderByRandomResponse.class,
                        work.workId,
                        work.workThumbnailUrl,
                        work.category.categoryName,
                        work.workTitle))
                .from(work)
                .fetch();
        return result;
    }

    @Override
    public List<WorkSearchResponse> getWorkSearchList(String keyword, Long categoryId) {

        List<WorkSearchResponse> result = queryFactory
                    .select(Projections.constructor(WorkSearchResponse.class,
                            work.workId,
                            work.workTitle,
                            work.category.categoryName))
                    .from(work)
                    .where(work.workTitle.contains(keyword),work.category.categoryId.eq(categoryId))
                    .fetch();
        return result;
    }


    @Override
    public GetWorkResponse getWork(Long workId) {
        GetWorkResponse result = queryFactory.
                select(Projections.constructor(GetWorkResponse.class,
                        work.workId,
                        work.workThumbnailUrl,
                        work.workTitle,
                        work.workDesc))
                .from(work)
                .where(work.workId.eq(workId))
                .fetchOne();
        return result;
    }

    @Override
    public List<StoriesOfWork> getStoriesByWorkId(Long workId) {
        List<StoriesOfWork> result = queryFactory
                .select(Projections.constructor(StoriesOfWork.class,
                        story.storyId,
                        story.storyThumbnailUrl,
                        story.work.workTitle,
                        story.work.category.categoryName,
                        JPAExpressions.select(likes.count())
                                .from(likes)
                                .where(likes.division.eq(1),
                                        likes.articleStoryId.eq(story.storyId.castToNum(Integer.class)))))
                .from(story)
                .where(story.work.workId.eq(workId))
                .fetch();
        return result;
    }
}
