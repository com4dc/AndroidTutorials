package jp.classmethod.android.sample.textureviewsample.adapter;

public class SampleItemDto {

	public SampleItemDto() {
	}
	
	public SampleItemDto(String message) {
		this.message = message;
	}
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
