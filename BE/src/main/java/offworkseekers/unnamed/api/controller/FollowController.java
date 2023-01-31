package offworkseekers.unnamed.api.controller;

import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.request.CreateFollowRequest;
import offworkseekers.unnamed.api.service.FollowService;
import offworkseekers.unnamed.db.entity.Follow;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/api/v1/follow")
    public Follow createFollow(@RequestBody CreateFollowRequest request){

        Follow follow = followService.createFollow(request.getMyUserId(), request.getYourUserId());

        return Follow.builder()
                .following(follow.getFollowing())
                .follower(follow.getFollower())
                .build();
    }

    @DeleteMapping("/api/v1/follow")
    public void deleteFollow(@RequestBody CreateFollowRequest request){
        followService.deleteFollow(request.getMyUserId(), request.getYourUserId());
    }

}
