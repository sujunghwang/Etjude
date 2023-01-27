package offworkseekers.unnamed.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StoryRepositoryImpl implements StoryRepositorySupport{

    private final JPAQueryFactory queryFactory;

}
