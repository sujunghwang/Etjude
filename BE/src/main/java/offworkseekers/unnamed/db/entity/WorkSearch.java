package offworkseekers.unnamed.db.entity;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

@Getter
public class WorkSearch {

    private String titleKeyword;
    private Long categoryId;

//    public Specification<Work> toSpecification(){
//        return where
//    }

}
