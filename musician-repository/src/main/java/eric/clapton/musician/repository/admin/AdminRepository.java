package eric.clapton.musician.repository.admin;

import org.springframework.data.jpa.repository.Query;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.admin.Admin;

public interface AdminRepository extends BaseRepository<Admin, Long> {
	@Query("select c from Admin c where c.username = ?1 ")
	Admin findByUsername(String username);

	@Query("select c from Admin c where c.username = ?1 and c.password = ?2 ")
	Admin login(String username, String password);
	
}
