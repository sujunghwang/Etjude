package offworkseekers.unnamed.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.response.LineResponse;
import offworkseekers.unnamed.api.response.RoleWithLineOfSceneResponse;
import offworkseekers.unnamed.db.entity.Line;
import offworkseekers.unnamed.db.entity.Role;

import java.util.ArrayList;
import java.util.List;

import static offworkseekers.unnamed.db.entity.QLine.line;
import static offworkseekers.unnamed.db.entity.QRole.role;
import static offworkseekers.unnamed.db.entity.QScene.scene;

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

    @Override
    public List<RoleWithLineOfSceneResponse> getRoleWithLines(Long storyId) {
        List<Tuple> fetch = queryFactory
                .select(scene.sceneNumber, scene.sceneId, role.roleId, role.roleName, role.rolePhotoUrl)
                .from(scene, role)
                .where(scene.story.storyId.eq(storyId),
                        role.story.storyId.eq(storyId))
                .orderBy(scene.sceneId.asc())
                .fetch();

        List<RoleWithLineOfSceneResponse> roleWithLineOfSceneResponse = new ArrayList<>();

        for (Tuple tuple : fetch) {
            roleWithLineOfSceneResponse.add(
                    RoleWithLineOfSceneResponse.builder()
                            .roleId(tuple.get(role.roleId))
                            .rolePhotoUrl(tuple.get(role.rolePhotoUrl))
                            .roleName(tuple.get(role.roleName))
                            .lines(
                                    getFetch(tuple)
                            )
                            .build()
            );
        }

        return roleWithLineOfSceneResponse;
    }

    private List<LineResponse> getFetch(Tuple tuple) {
        List<Tuple> fetch = queryFactory
                .select(line.lineScript, line.lineTimestamp)
                .from(line)
                .where(line.scene.sceneId.eq(tuple.get(scene.sceneId)))
                .orderBy(line.lineTimestamp.asc())
                .fetch();

        List<LineResponse> lines = new ArrayList<>();

        for (Tuple lineTuple : fetch) {
            lines.add(
                    LineResponse.builder()
                            .lineTimeStamp(lineTuple.get(line.lineTimestamp))
                            .line(lineTuple.get(line.lineScript))
                            .build()
            );
        }
        return lines;
    }
}
