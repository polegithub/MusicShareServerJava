package eric.clapton.musician.service.product.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.product.ProductType;
import eric.clapton.musician.repository.product.ProductTypeRepository;
import eric.clapton.musician.service.product.ProductTypeService;

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType, ProductTypeRepository> implements ProductTypeService {

	@Override
	@Resource
	protected void baseSetRepository(ProductTypeRepository repository) {
		this.setRepository(repository);
	}

	

}
