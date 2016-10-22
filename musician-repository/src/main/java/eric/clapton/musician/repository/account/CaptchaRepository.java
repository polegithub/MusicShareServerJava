package eric.clapton.musician.repository.account;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.account.Captcha;

public interface CaptchaRepository extends BaseRepository<Captcha, Long> {
	@Query("select c from Captcha c where c.phoneNumber = ?1 and c.created >= ?2 order by c.created")
	List<Captcha> findByPhoneNumberAndCreatedGreaterThanOrEqual(String phoneNumber, Calendar c);
}
