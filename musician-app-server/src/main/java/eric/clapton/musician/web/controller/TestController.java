package eric.clapton.musician.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.service.account.AccountService;

@Controller
@RequestMapping()
public class TestController extends ApiControllerSupport {
	@Resource
	private AccountService userService;

	@RequestMapping("/test")
	@ResponseBody
	public String hello() {
		return "hello!!!!";
	}

	@RequestMapping("/test/db")
	@ResponseBody
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object test(String userName, boolean remove) {
		logger.debug("Retrieving user with name : {}", userName);

		Account user = userService.findByAccountName(userName);

		logger.debug("Retrieved user with name : {}", userName);

		if (user != null) {
			if (remove) {
				logger.debug("Removing user with name : {}", userName);

				userService.delete(user.getId());

				logger.debug("Removed user with name : {}", userName);
			} else {
				logger.debug("Updating user name from  {} to {}", userName, userName + "-2");

				user.setUserName(userName + "-2");
				userService.save(user);

				logger.debug("Updated user name from  {} to {}", userName, userName + "-2");
			}
		} else {
			logger.debug("Adding user with name : {}", userName);

			user = new Account();
			user.setUserName(userName);
			userService.save(user);

			logger.debug("Added user with name : {}", userName);
		}

		return ApiResponse.succeed(user);
	}
}
