package offworkseekers.unnamed.db.repository;

import offworkseekers.unnamed.db.entity.Work;
import offworkseekers.unnamed.db.entity.WorkSearch;

import java.util.List;

public interface WorkRepositorySupport {

    // 조회용 쿼리 인터페이스만 작성, Impl에서 구현, JPARepository로 만들 수 없는 쿼리만..?

    public List<Work> search(WorkSearch workSearch);

}
