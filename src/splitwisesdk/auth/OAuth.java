package splitwisesdk.auth;

public class OAuth {
	protected String consumerKey;
	protected String consumerSecret;
	protected String tokenSecret;
	
	private String requestTokenUrl;
	private String accessTokenUrl;
	
	OAuth(String consumerKey, String consumerSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.tokenSecret = "";
	}
	
	private void signRequest(OAuthRequest req) {
		
	}
	
	private String getAuthorizationURL() {
		String authorizationURL = "";
		
		// Generate Request
		OAuthRequest req = new OAuthRequest();
		req.setConsumerKey(consumerKey);
		
		hmac_sha1_sign(req, requestTokenUrl, consumerSecret, tokenSecret);
		
		//return authorizationURL;
		return req.getRequestHash();
	}
	
	private void hmac_sha1_sign(OAuthRequest req, String requestTokenUrl, String consumerSecret, String tokenSecret) {
		req.setOauthSignatureMethod(OAuthRequest.HMAC_SHA1);
		String method = "POST";
		String hashedUrl = "";
	}
	
	public static void main(String args[]) {
		// Testing
		String consumerKey = "cCFUP5oGYVNJJAF9PlOR2qqBDcnzzEnPx5hofrh4";
		String consumerSecret = "uwGfeBgdVlx2vAz4HwT3sPQgZ3Ib4PcYeZXyFiE2";
		
		OAuth oauth = new OAuth(consumerKey,consumerSecret);
		System.out.println("Authorization url\n" + oauth.getAuthorizationURL());
		
	}
	
}
