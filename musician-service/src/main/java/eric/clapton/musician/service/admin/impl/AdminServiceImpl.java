package eric.clapton.musician.service.admin.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.admin.Admin;
import eric.clapton.musician.repository.admin.AdminRepository;
import eric.clapton.musician.service.admin.AdminService;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, AdminRepository> implements AdminService {

	@Override
	@Resource
	protected void baseSetRepository(AdminRepository repository) {
		this.setRepository(repository);
	}

	@Override
	public Admin login(String username, String password) {

		return repository.login(username, password);
	}

	@Override
	public Admin userinf(String username) {
		
		return null;
	}

}
