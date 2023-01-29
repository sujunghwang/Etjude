package offworkseekers.unnamed.api.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import offworkseekers.unnamed.db.entity.Line;

import java.util.List;

@Data
@Getter
public class RoleWithLineOfSceneResponse {

    private Long roleId;
    private String rolePhotoUrl;
    private String roleName;
    private List<LineResponse> lines;

    @Builder
    public RoleWithLineOfSceneResponse(Long roleId, String rolePhotoUrl, String roleName, List<LineResponse> lines) {
        this.roleId = roleId;
        this.rolePhotoUrl = rolePhotoUrl;
        this.roleName = roleName;
        this.lines = lines;
    }
}
