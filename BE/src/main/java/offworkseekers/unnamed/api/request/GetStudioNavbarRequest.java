package offworkseekers.unnamed.api.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GetStudioNavbarRequest {

    @JsonProperty(value = "studio_id")
    private Long studioId;

    @JsonProperty(value = "user_id")
    private Long userId;

    public GetStudioNavbarRequest(Long studioId, Long userId) {
        this.studioId = studioId;
        this.userId = userId;
    }
}
