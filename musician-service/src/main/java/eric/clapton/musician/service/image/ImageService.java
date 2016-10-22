package eric.clapton.musician.service.image;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.po.image.ImageStore;

public interface ImageService extends BaseService<ImageStore> {

	ImageStore findByToken(String token);
	
	ImageStore findByFile(String file);
}
