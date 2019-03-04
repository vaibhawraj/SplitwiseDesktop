package com.splitwise.splitwisesdk.responses;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.splitwise.splitwisesdk.APIException;

public class Friend extends Response {
	public long id;
	public String first_name;
	public String last_name;
	public Date updated_at;
	
	final private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public Friend(String jsonText) throws APIException {
		super(jsonText);
		fetchValues();
	}
	
	public Friend(JSONObject friendObj) {
		this.jsonObj = friendObj;
		fetchValues();
	}

	public void fetchValues() {
		this.first_name = (String) jsonObj.get("first_name");
		this.last_name = (String) jsonObj.get("last_name");
		this.id = (long) jsonObj.get("id");
		
		DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		
		try {
			this.updated_at = m_ISO8601Local.parse((String) jsonObj.get("updated_at"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			this.updated_at = new Date(System.currentTimeMillis());
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return String.valueOf(id);
	}

	public static List<Friend> parseFriendsList(String response) throws APIException {
		Response res = new Response(response);
		JSONArray friends = (JSONArray) res.jsonObj.get("friends");
		ArrayList<Friend> friendsList = new ArrayList<Friend>();
		for(int i = 0; i< friends.size(); i++) {
			JSONObject friendObj = (JSONObject) friends.get(i);
			friendsList.add(new Friend(friendObj));
		}
		return friendsList;
	}
	
}
