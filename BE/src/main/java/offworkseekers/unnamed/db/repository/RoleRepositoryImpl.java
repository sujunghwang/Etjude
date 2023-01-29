package offworkseekers.unnamed.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.db.entity.Role;

import java.util.List;

import static offworkseekers.unnamed.db.entity.QRole.role;

@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepositorySupport{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Role> getStoryRoleList(Long storyId) {
        List<Role> roleList = queryFactory
                .selectFrom(role)
                .where(role.story.storyId.eq(storyId))
                .fetch();
        return roleList;
    }
}
