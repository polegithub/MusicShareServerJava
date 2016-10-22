package eric.clapton.musician.web.controller.my;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.service.account.AccountService;
import eric.clapton.musician.util.CurrentUser;
import eric.clapton.musician.web.controller.ApiControllerSupport;

@Controller
@RequestMapping("/my/skill")
@ResponseBody
public class MySkillController extends ApiControllerSupport {
	@Resource
	private AccountService accountService;

	@RequestMapping("/update")
	@Transactional(propagation = Propagation.REQUIRED)
	public ApiResponse update(@RequestBody() List<Long> skillIdList) {
		if (skillIdList == null) {
			return parameterMissing();
		}

		accountService.updateSkills(CurrentUser.getAccountId(), skillIdList);

		return ApiResponse.succeed();
	}

}
