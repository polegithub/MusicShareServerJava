package eric.clapton.infrastructure.web;

import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperEx extends com.fasterxml.jackson.databind.ObjectMapper {

	private static final long serialVersionUID = -354745165712033006L;

	public ObjectMapperEx() {
		super.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}
}
