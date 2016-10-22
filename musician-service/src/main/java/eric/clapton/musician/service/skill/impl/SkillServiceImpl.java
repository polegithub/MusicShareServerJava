package eric.clapton.musician.service.skill.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.skill.Skill;
import eric.clapton.musician.repository.skill.SkillRepository;
import eric.clapton.musician.service.skill.SkillService;

@Service
public class SkillServiceImpl extends BaseServiceImpl<Skill, SkillRepository> implements SkillService {
	@Override
	@Resource
	protected void baseSetRepository(SkillRepository repository) {
		super.setRepository(repository);
	}
}
