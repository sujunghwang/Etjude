package offworkseekers.unnamed.db.repository;

import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import offworkseekers.unnamed.db.entity.QCategory;
import offworkseekers.unnamed.db.entity.QWork;
import offworkseekers.unnamed.db.entity.Work;
import offworkseekers.unnamed.db.entity.WorkSearch;
import offworkseekers.unnamed.db.repository.WorkRepositorySupport;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

// 쿼리 구현
public class WorkRepositoryImpl extends QuerydslRepositorySupport implements WorkRepositorySupport {

    public WorkRepositoryImpl (){
        super(Work.class);
    }

    @Override
    public List<Work> search(WorkSearch workSearch) {

        QWork work = QWork.work;
        QCategory category = QCategory.category;

        JPQLQuery query = from(work);

        if(StringUtils.hasText(workSearch.getTitleKeyword())){
            query.where(work.workTitle.contains(workSearch.getTitleKeyword()));
        }

        if(workSearch.getCategoryId() != null){
            query.leftJoin(work.category, category)
                    .where(category.categoryId.eq(workSearch.getCategoryId()));
        }

        return query.fetch();
    }
}
