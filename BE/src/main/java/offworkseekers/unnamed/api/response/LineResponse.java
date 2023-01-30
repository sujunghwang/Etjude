package offworkseekers.unnamed.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;


@Getter
@ToString
public class LineResponse {

    private LocalTime lineTimeStamp;
    private String line;

    @Builder
    public LineResponse(LocalTime lineTimeStamp, String line) {
        this.lineTimeStamp = lineTimeStamp;
        this.line = line;
    }
}
