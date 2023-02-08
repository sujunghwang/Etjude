package offworkseekers.unnamed.api.request;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateFilmRequest {
    int studioId;
    int storyId;

    @Builder
    public CreateFilmRequest(int studioId, int storyId) {
        this.studioId = studioId;
        this.storyId = storyId;
    }
}
