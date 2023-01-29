package offworkseekers.unnamed.api.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class StoryRoleResponse {

    private Long roleId;
    private String rolePhotoUrl;
    private String roleName;
    private String roleDesc;

    @Builder
    public StoryRoleResponse(Long roleId, String rolePhotoUrl, String roleName, String roleDesc) {
        this.roleId = roleId;
        this.rolePhotoUrl = rolePhotoUrl;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }
}
