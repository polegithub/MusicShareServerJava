package eric.clapton.musician.service.admin;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.po.admin.Admin;

public interface AdminService extends BaseService<Admin> {
	Admin login(String username, String password);
	Admin userinf(String username);
}
