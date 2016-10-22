package eric.clapton.musician.repository.home;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.order.OrderGenre;

public interface HomeSearchRepository extends BaseRepository<OrderGenre, Long> {
//	@Query("select c from Captcha c where c.phoneNumber = ?1 and c.created >= ?2 order by c.created")
//	List<Captcha> findByPhoneNumberAndCreatedGreaterThanOrEqual(String phoneNumber, Calendar c);
}
