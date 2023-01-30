package offworkseekers.unnamed.api.response;

import lombok.Getter;
import offworkseekers.unnamed.api.dto.MyPageFollowDto;

import java.util.List;

@Getter
public class MyPageResponse {

    String userPhotoUrl;
    String userNickName;
    List<MyPageFollowDto> followings;
    List<MyPageFollowDto> followers;

    public MyPageResponse(String userPhotoUrl, String userNickName, List<MyPageFollowDto> followings, List<MyPageFollowDto> followers) {
        this.userPhotoUrl = userPhotoUrl;
        this.userNickName = userNickName;
        this.followings = followings;
        this.followers = followers;
    }
}
