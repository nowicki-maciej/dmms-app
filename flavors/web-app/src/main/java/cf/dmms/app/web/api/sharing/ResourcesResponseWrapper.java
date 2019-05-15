package cf.dmms.app.web.api.sharing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResourcesResponseWrapper {

	private List<SharedResourceDto> sharedResourceDtos;

	@JsonCreator
	public ResourcesResponseWrapper(@JsonProperty("sharedResourceDtos") List<SharedResourceDto> sharedResourceDtos) {
		this.sharedResourceDtos = sharedResourceDtos;
	}

	public List<SharedResourceDto> getSharedResourceDtos() {
		return sharedResourceDtos;
	}


}
