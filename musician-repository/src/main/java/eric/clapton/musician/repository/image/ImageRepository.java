package eric.clapton.musician.repository.image;

import org.springframework.data.jpa.repository.Query;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.image.ImageStore;

public interface ImageRepository extends BaseRepository<ImageStore, Long> {
	@Query("select c from ImageStore c where c.token = ?1 ")
	ImageStore findByToken(String token);
	
	@Query("select c from ImageStore c where c.file = ?1 ")
	ImageStore findByFile(String file);
}
