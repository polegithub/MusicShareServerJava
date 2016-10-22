package eric.clapton.musician.core.entity.dto.performance;

import java.io.Serializable;
import java.util.Calendar;

import eric.clapton.musician.core.entity.dto.geolocation.Geoposition;

public class PerformanceInfo implements Serializable {
	private static final long serialVersionUID = 3246547553099334092L;

	private Long id;
	private String name;
	private String[] styles;
	private String date;
	private Calendar created;
	private String publisher;
	private Geoposition position;
	private String summary;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getStyles() {
		return styles;
	}

	public void setStyles(String[] styles) {
		this.styles = styles;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Geoposition getPosition() {
		return position;
	}

	public void setPosition(Geoposition position) {
		this.position = position;
	}
}
