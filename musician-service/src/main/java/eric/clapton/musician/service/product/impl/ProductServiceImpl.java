package eric.clapton.musician.service.product.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.product.Product;
import eric.clapton.musician.repository.product.ProductRepository;
import eric.clapton.musician.service.product.ProductService;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, ProductRepository> implements ProductService {

	@Override
	@Resource
	protected void baseSetRepository(ProductRepository repository) {
		this.setRepository(repository);
	}

	

}
