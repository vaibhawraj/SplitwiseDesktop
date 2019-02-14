package splitwisesdk.auth;

import java.net.URLEncoder;

public class OAuthRequest {
	final static String HMAC_SHA1 = "HMAC-SHA1";
	
	private String oauth_consumer_key;
	private String oauth_signature_method;
	private String oauth_signature = null;
	private String oauth_timestamp;
	private String oauth_nonce;
	final private String oauth_version = "1.0";
	
	OAuthRequest() {
		this.oauth_timestamp = String.valueOf(this.getCurrentTimestamp());
		this.oauth_nonce = String.valueOf(this.getNonce());
	}
	private void setConsumerKey(String consumerKey) {
		this.oauth_consumer_key = consumerKey;
	}
	
	private void setOauthSignatureMethod(String signatureMethod) {
		this.oauth_signature_method = signatureMethod;
	}
	
	private String getRequestBody() {
		return getRequestBody(true);
	}
	
	private String getRequestBody(boolean includeSignature) {
		StringBuilder sb = new StringBuilder();
		sb.append("oauth_consumer_key" + "=" + oauth_consumer_key);
		sb.append("&");
		sb.append("oauth_nonce" + "=" + oauth_nonce);
		sb.append("&");
		if(includeSignature) {
			sb.append("oauth_signature" + "=" + oauth_signature);
			sb.append("&");
		}
		sb.append("oauth_signature_method" + "=" + oauth_signature_method);
		sb.append("&");
		sb.append("oauth_timestamp" + "=" + oauth_timestamp);
		sb.append("&");
		sb.append("oauth_version" + "=" + oauth_version);
		
		return sb.toString();
	}
	
	private String getRequestHash() {
		return URLEncoder.encode(getRequestBody(false));
	}
	
	private long getCurrentTimestamp() {
		return System.currentTimeMillis()/1000;
	}
	
	private long getNonce() {
		return (long)(Math.random() * 100000000);
	}
	
	public String toString() {
		return getRequestBody(true).replace("&", "\n");
	}
}
