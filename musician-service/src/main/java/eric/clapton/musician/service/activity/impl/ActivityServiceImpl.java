package eric.clapton.musician.service.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.musician.core.entity.po.activity.Activity;
import eric.clapton.musician.repository.activity.ActivityRepository;
import eric.clapton.musician.service.activity.ActivityService;

@Service
public class ActivityServiceImpl extends BaseServiceImpl<Activity, ActivityRepository> implements ActivityService {

	@Override
	@Resource
	protected void baseSetRepository(ActivityRepository repository) {
		this.setRepository(repository);
	}

	

}
