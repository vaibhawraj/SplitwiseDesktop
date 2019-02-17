/*
 * Oauth.java: Handles OAuth authentication
 */
package splitwisesdk.auth;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/*
 * Following JAR files must be added to class path:
 * commons-codec-1.11.jar
 * httpclient-4.5.7.jar
 * httpcore-4.4.11.jar
 */

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import splitwisesdk.Http;

public class OAuth {
	private String consumerKey;
	private String consumerSecret;
	private String tokenSecret;
	private String oauth_token;
	private String oauth_secret;
	
	protected String requestTokenUrl;
	protected String accessTokenUrl;
	protected String authorizeUrl;
	
	OAuth(String consumerKey, String consumerSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.tokenSecret = "";
	}
	
	/*
	 * getAuthoriationURL : Returns authorization url which is used to get access
	 * token
	 */
	protected String getAuthorizationURL() {
		String authorizationURL = "";
		
		// Generate Request
		OAuthRequest req = new OAuthRequest();
		req.setConsumerKey(consumerKey);
		
		hmac_sha1_sign(req);
		
		String body = req.getRequestBody();
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type","application/x-www-form-urlencoded");
		
		String response = Http.sendPostRequest(requestTokenUrl, headers, body);
		
		String[] rArr = response.split("&");
		HashMap<String,String> params = new HashMap<String, String>();
		
		for(String r : rArr) {
			params.put(r.split("=")[0], r.split("=")[1]);
		}
		oauth_token = params.get("oauth_token");
		oauth_secret = params.get("oauth_secret");
		
		authorizationURL = this.authorizeUrl + "?oauth_token=" + this.oauth_token;
		return authorizationURL;
	}
	
	private void hmac_sha1_sign(OAuthRequest req) {
		req.setOauthSignatureMethod(OAuthRequest.HMAC_SHA1);
		String method = "POST";
		String hashedUrl = URLEncoder.encode(this.requestTokenUrl);
		String hashedBody = req.getRequestHash();
		String signature_base_string = method + "&" + hashedUrl + "&" + hashedBody;
		String key = consumerSecret + "&" + tokenSecret;
		String signature = new String(
				Base64.encodeBase64(
						new HmacUtils(
								HmacAlgorithms.HMAC_SHA_1, 
								key
								).hmac(signature_base_string)
						)
				);
		req.setSignature(signature);
	}
	
}
