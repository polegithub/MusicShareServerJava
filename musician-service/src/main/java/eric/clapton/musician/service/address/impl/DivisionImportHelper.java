package eric.clapton.musician.service.address.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import eric.clapton.musician.core.entity.po.address.Division;
import eric.clapton.musician.service.address.DivisionService;

@Component
public class DivisionImportHelper {
	public static class DivisionInfo {
		public Long id;
		public String code;
		public String name;
		public int level;
		public DivisionInfo parent;
		public int displayOrder;
	}

	@Resource
	private DivisionService divisionService;

	// @Transactional(propagation = Propagation.REQUIRED)
	public void importDivisions(List<DivisionInfo> divisions) {
		Collections.sort(divisions, (x, y) -> x.level - y.level);
		for (DivisionInfo d : divisions) {
			Division division = divisionService.findByCode(d.code);
			boolean isNew = division == null;
			if (isNew) {
				division = new Division();
				division.setCode(d.code);
				division.setDisplayOrder(d.displayOrder);
				division.setLevel(d.level);
				division.setName(d.name);
				division.setPinyin(null);
				division.setPostalCode(null);

				StringBuilder p = new StringBuilder();
				DivisionInfo parent = d.parent;
				while (parent != null) {
					p.insert(0, parent.id + "/");

					parent = parent.parent;
				}

				division.setPath(p.toString());
				// division.setPinyin(pinyin);
			} else {
				division.setName(d.name);
				division.setDisplayOrder(d.displayOrder);
			}

			if (isNew) {
				divisionService.save(division);
				division.setPath(division.getPath() + division.getId());
				divisionService.save(division);
			}

			d.id = division.getId();
		}
	}
}
