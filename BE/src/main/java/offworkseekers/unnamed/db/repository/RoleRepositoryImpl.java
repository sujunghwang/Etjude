package offworkseekers.unnamed.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepositorySupport{

    private final JPAQueryFactory queryFactory;
}
