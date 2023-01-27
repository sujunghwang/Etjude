package offworkseekers.unnamed.api.controller;

import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.api.service.WorkService;
import offworkseekers.unnamed.db.entity.Work;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;

//    @GetMapping("/api/v1/work/recommended-random")
//    public List<Work> getWorkList(){
//        List
//
//    }
}
