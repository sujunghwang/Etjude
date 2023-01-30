package offworkseekers.unnamed.api.response;

import lombok.Getter;

import java.util.List;

@Getter
public class MyPageResponse {

    String userPhotoUrl;
    String userNickName;
    List<MyPageFollowResponse> followings;
    List<MyPageFollowResponse> followers;

    public MyPageResponse(String userPhotoUrl, String userNickName, List<MyPageFollowResponse> followings, List<MyPageFollowResponse> followers) {
        this.userPhotoUrl = userPhotoUrl;
        this.userNickName = userNickName;
        this.followings = followings;
        this.followers = followers;
    }
}
