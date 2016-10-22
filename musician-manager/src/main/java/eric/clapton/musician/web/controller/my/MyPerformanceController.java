package eric.clapton.musician.web.controller.my;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eric.clapton.infrastructure.util.StringUtils;
import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.dto.BasePageResponse;
import eric.clapton.musician.core.entity.dto.account.AccountInfo;
import eric.clapton.musician.core.entity.dto.geolocation.Geoposition;
import eric.clapton.musician.core.entity.dto.performance.PerformanceInfo;
import eric.clapton.musician.core.entity.dto.performance.PublishedPerformancesSearchParams;
import eric.clapton.musician.core.entity.dto.performance.SellerPerformanceRequest;
import eric.clapton.musician.core.entity.po.performance.Performance;
import eric.clapton.musician.service.performance.PerformanceService;
import eric.clapton.musician.util.CurrentUser;
import eric.clapton.musician.web.controller.ApiControllerSupport;

@Controller
@RequestMapping("/my/performance")
@ResponseBody
public class MyPerformanceController extends ApiControllerSupport {
	@Resource
	private PerformanceService performanceService;

	@RequestMapping("")
	public ApiResponse searchAll(@RequestBody() SellerPerformanceRequest r) {
		AccountInfo account = CurrentUser.getAccountInfo();
		Long accountId = account.getId();

		PublishedPerformancesSearchParams searchParams = new PublishedPerformancesSearchParams(accountId);
		searchParams.setPageCountDisabled(r.isNoCount());

		Page<Performance> page = performanceService.findPublishedPerformances(searchParams, r.getPageable());

		List<PerformanceInfo> performances = new ArrayList<PerformanceInfo>(page.getNumberOfElements());

		for (Performance p : page) {
			PerformanceInfo pi = new PerformanceInfo();
			pi.setCreated(p.getCreated());

			String date;
			String dateFrom = toShortDateString(p.getDateFrom());
			String dateTo = toShortDateString(p.getDateTo());

			if (dateFrom.equals(dateTo)) {
				date = dateFrom;
			} else {
				date = String.format(Locale.CHINA, "%s 至 %s", dateFrom, dateTo);
			}
			pi.setDate(date);

			pi.setPosition(new Geoposition(p.getLatitude(), p.getLongitude(), p.getAddress(), p.getDetailAddress()));
			pi.setId(p.getId());
			pi.setName(p.getName());
			pi.setPublisher(p.getPublisher().getNickName());
			pi.setStyles(p.getTags().stream().map(t -> t.getTag()).toArray(String[]::new));

			pi.setSummary(StringUtils.truncate(p.getDescription(), 100));

			performances.add(pi);
		}

		BasePageResponse response = new BasePageResponse(page.getNumber(), page.getSize(), page.getTotalElements(),
				performances);

		return ApiResponse.succeed(response);
	}

	private static String toShortDateString(Calendar c) {
		if (c == null) {
			return "";
		}
		return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(c.getTime());
	}
}
