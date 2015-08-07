package com.example.birthdaytree.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.birthdaytree.bean.TransportPerson;

public class JsonUtil {

	static public String TransportPersonToJson(TransportPerson transportPerson){
		String chatId = transportPerson.getChatId();//发起聊天的唯一标识
		 String name = transportPerson.getName(); 
		 String date = transportPerson.getDate();
		 String sex = transportPerson.getSex();
		 String hobby = transportPerson.getHobby();
		 JSONObject jsonStr = new JSONObject();
		 try {
			 jsonStr.put("chatId", chatId );
			 jsonStr.put("name", name );
			 jsonStr.put("date", date );
			 jsonStr.put("sex", sex );
			 jsonStr.put("hobby", hobby );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr.toString();
		
	}
	
	static public TransportPerson JsonToTransportPerson(String jsonStr){
		TransportPerson transportPerson = new TransportPerson();
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			transportPerson.setChatId(jsonObject.getString("chatId"));
			transportPerson.setName(jsonObject.getString("name"));
			transportPerson.setDate(jsonObject.getString("date"));
			transportPerson.setSex(jsonObject.getString("sex"));
			transportPerson.setHobby(jsonObject.getString("hobby"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transportPerson;
	}

}
