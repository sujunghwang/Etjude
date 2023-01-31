package offworkseekers.unnamed.db.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "likes_id")
    private Long likesId;

    @Column(name = "division")
    private int division;

    @Column(name = "video_id")
    private int videoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Likes(
            Long likesId,
            int division,
            int videoId,
            User user
    ){
        this.likesId = likesId;
        this.division = division;
        this.videoId = videoId;
        this.user = user;
    }


}