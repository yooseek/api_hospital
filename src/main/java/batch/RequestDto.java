package batch;

import org.yaml.snakeyaml.util.UriEncoder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestDto {
	private final String serviceKey = "cd%2FYh69sjlw4yDYGYjzwtIsDBvxvvULRl3U6rI%2Bofh%2F774Vbx%2FbRBh14DKzxJmSRQ9WKxEEZF5ME01PunggJgA%3D%3D";
	private int pageNo;
	private int numOfRows;
	private String spclAdmTyCd;
	
	public String toString() {
		return "?seviceKey="+serviceKey+"&pageNo="+this.pageNo+"&numOfRows="+this.numOfRows+"&spclAdmTyCd="+this.spclAdmTyCd;
	}
}