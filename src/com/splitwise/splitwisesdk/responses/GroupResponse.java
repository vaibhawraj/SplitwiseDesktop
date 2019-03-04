package com.splitwise.splitwisesdk.responses;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.splitwise.splitwisesdk.APIException;

public class GroupResponse extends Response {
	public long id;
	public String name;
	public Date updated_at;
	final private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public GroupResponse(String jsonText) throws APIException {
		super(jsonText);
		fetchValues();
	}
	
	public GroupResponse(JSONObject groupObj) {
		this.jsonObj = groupObj;
		fetchValues();
	}

	public void fetchValues() {
		this.name = (String) jsonObj.get("name");
		
		this.id = (long) jsonObj.get("id");
		//2019-03-03T23:28:15Z
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

	public static List<GroupResponse> parseGroupList(String response) throws APIException {
		Response res = new Response(response);
		JSONArray groups = (JSONArray) res.jsonObj.get("groups");
		ArrayList<GroupResponse> groupsList = new ArrayList<GroupResponse>();
		for(int i = 0; i< groups.size(); i++) {
			JSONObject groupObj = (JSONObject) groups.get(i);
			groupsList.add(new GroupResponse(groupObj));
		}
		System.out.println(groupsList.size());
		return groupsList;
	}
}
