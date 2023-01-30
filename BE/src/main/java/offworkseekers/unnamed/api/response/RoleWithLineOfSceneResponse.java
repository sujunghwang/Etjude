package offworkseekers.unnamed.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Getter
@ToString
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
