package offworkseekers.unnamed.api.response;

import lombok.Getter;

@Getter
public class MyPageFollowResponse {
    String userId;
    String userPhotoUrl;

    public MyPageFollowResponse(String userId, String userPhotoUrl) {
        this.userId = userId;
        this.userPhotoUrl = userPhotoUrl;
    }
}
