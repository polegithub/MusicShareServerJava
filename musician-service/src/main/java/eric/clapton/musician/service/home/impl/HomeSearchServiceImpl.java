package eric.clapton.musician.service.home.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Charsets;

import eric.clapton.infrastructure.data.jpa.search.Searchable;
import eric.clapton.infrastructure.entity.po.BaseEntity;
import eric.clapton.infrastructure.service.ServiceException;
import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.infrastructure.util.Assure;
import eric.clapton.infrastructure.util.Enumerable;
import eric.clapton.musician.core.entity.po.account.Captcha;
import eric.clapton.musician.core.entity.po.account.CaptchaState;
import eric.clapton.musician.core.entity.po.order.OrderGenre;
import eric.clapton.musician.repository.account.CaptchaRepository;
import eric.clapton.musician.repository.home.HomeSearchRepository;
import eric.clapton.musician.service.account.CaptchaRequestTooFrequentExceptionException;
import eric.clapton.musician.service.account.CaptchaService;
import eric.clapton.musician.service.account.HomeSearchService;
import eric.clapton.musician.service.security.CaptchaGenerationException;
import eric.clapton.musician.service.security.CaptchaGenerator;
import eric.clapton.musician.service.sms.provider.yuntongxun.YuntongxunException;
import eric.clapton.musician.service.sms.provider.yuntongxun.YuntongxunSmsProvider;

@Service
public class HomeSearchServiceImpl extends BaseServiceImpl<OrderGenre, HomeSearchRepository> implements HomeSearchService {
	
	@Resource
	private YuntongxunSmsProvider smsProvider;


	@Override
	public List getTypeList() {
		return null;
	}



	@Override
	public List showList() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List serach(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public OrderGenre save(OrderGenre t) {
		return null;
	}



	@Override
	public OrderGenre update(OrderGenre t) {
	
		return null;
	}



	@Override
	public OrderGenre delete(OrderGenre m) {
		// TODO Auto-generated method stub
		return null;
	}







	@Override
	public OrderGenre getOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public OrderGenre findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public OrderGenre delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected void baseSetRepository(HomeSearchRepository repository) {
		// TODO Auto-generated method stub
		
	}



	
}
