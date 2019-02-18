package splitwisesdk.responses;

public class User {
	private String jsonText;
	public User(String jsonText) {
		this.jsonText = jsonText;
		parseJSON();
	}
	
	private void parseJSON() {
		// Logic to parse JSON
	}
	
	public String toString() {
		return jsonText;
	}
}
