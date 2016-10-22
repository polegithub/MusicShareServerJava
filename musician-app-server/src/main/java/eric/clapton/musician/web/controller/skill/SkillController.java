package eric.clapton.musician.web.controller.skill;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eric.clapton.infrastructure.data.jpa.search.Searchable;
import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.dto.skill.SkillInfo;
import eric.clapton.musician.core.entity.po.skill.Skill;
import eric.clapton.musician.service.skill.SkillService;
import eric.clapton.musician.web.controller.ApiControllerSupport;

@Controller
@RequestMapping("/skill")
@ResponseBody
public class SkillController extends ApiControllerSupport {
	@Resource
	private SkillService skillService;

	@RequestMapping()
	public ApiResponse list() {
		Searchable searchable = Searchable.newSearchable();
		searchable.isFalse("deleted").isFalse("disabled");
		searchable.orderBy("displayOrder");

		List<Skill> skills = skillService.findAllWithSort(searchable);
		List<SkillInfo> resultList = skills.stream().map(s -> new SkillInfo(s.getId(), s.getName()))
				.collect(Collectors.<SkillInfo> toList());

		return ApiResponse.succeed(resultList);
	}
}
