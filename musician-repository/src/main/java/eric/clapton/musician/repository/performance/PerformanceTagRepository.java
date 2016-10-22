package eric.clapton.musician.repository.performance;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.performance.PerformanceTag;

public interface PerformanceTagRepository extends BaseRepository<PerformanceTag, Long> {
	@Query("select t from PerformanceTag t where t.performance.id = ?1 order by t.displayOrder")
	List<PerformanceTag> findByPerformanceIdOrderByDisplayOrder(Long performanceId);
	
	@Query(value="select tag from ms_perf_tag where perf_id = ?1",nativeQuery = true)
	String findPerformanceNameByid(Long tagId);
	
	@Query(value="select a.tag from ms_perf_tag a,ms_publish_style b where b.orderid =  ?1 and b.type = a. perf_id ",nativeQuery = true)
	List<String> findOrderPerformance(Long orderId);
	
}
