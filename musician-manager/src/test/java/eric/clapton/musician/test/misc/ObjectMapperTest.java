package eric.clapton.musician.test.misc;

import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperTest {
	private static class DateWrapper {
		private Date date;

		public Date getDate() {
			return date;
		}

		@SuppressWarnings("unused")
		public void setDate(Date date) {
			this.date = date;
		}
	}

	@Test
	public void testWriteJsonDate() throws Throwable {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
		String jsonString = "{\"date\":1452523732}";

		DateWrapper w = objectMapper.readValue(jsonString.getBytes(), DateWrapper.class);
		System.out.println(w.getDate());
	}
}
