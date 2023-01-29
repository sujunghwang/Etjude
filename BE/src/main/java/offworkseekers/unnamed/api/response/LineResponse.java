package offworkseekers.unnamed.api.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalTime;

@Data
@Getter
public class LineResponse {

    private LocalTime lineTimeStamp;
    private String line;

    @Builder
    public LineResponse(LocalTime lineTimeStamp, String line) {
        this.lineTimeStamp = lineTimeStamp;
        this.line = line;
    }
}
