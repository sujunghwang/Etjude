package offworkseekers.unnamed.db.repository;

import offworkseekers.unnamed.db.entity.Role;

import java.util.List;

public interface RoleRepositorySupport {

    List<Role> getStoryRoleList(Long storyId);
}
