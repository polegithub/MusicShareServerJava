package eric.clapton.musician.service.image.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.image.ImageStore;
import eric.clapton.musician.repository.image.ImageRepository;
import eric.clapton.musician.service.image.ImageService;

@Service
public class ImageServiceImpl extends
		BaseServiceImpl<ImageStore, ImageRepository> implements ImageService {

	@Override
	@Resource
	protected void baseSetRepository(ImageRepository repository) {
		this.setRepository(repository);
	}
	

	public ImageStore findByToken(String token) {
		return getRepository().findByToken(token);
	}

	@Override
	@Transactional
	public ImageStore findByFile(String file) {
		return getRepository().findByFile(file);
	}
	
}
