package eric.clapton.musician.repository.address;

import org.springframework.data.jpa.repository.Query;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.address.Division;

public interface DivisionRepository extends BaseRepository<Division, Long> {
	@Query("select d from Division d where d.code = ?1")
	Division findByCode(String code);
}
