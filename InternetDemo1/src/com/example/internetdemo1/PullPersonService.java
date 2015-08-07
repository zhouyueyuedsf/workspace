package com.example.internetdemo1;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class PullPersonService {
	public static List<Person> getPersons (InputStream inputStream) throws XmlPullParserException, IOException{
		List<Person> persons = null;
		Person person = null;
		XmlPullParser pullParser = Xml.newPullParser();//工厂模式
		pullParser.setInput(inputStream,"utf-8");
		int event = pullParser.getEventType();
		while(event != XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				persons = new ArrayList<Person>();
				break;

			case XmlPullParser.START_TAG:
				if("person".equals(pullParser.getName())){
					int id = new Integer(pullParser.getAttributeValue(0));
					person = new Person();
					person.setId(id);
				}
				if(person != null){
					if("name".equals(pullParser.getName())){
						person.setName(pullParser.nextText());
					}
					if("age".equals(pullParser.getName())){
						person.setAge(new Short(pullParser.nextText()));
					}
				}
				break;
				
			case XmlPullParser.END_TAG:
				if("person".equals(pullParser.getName())){
					persons.add(person);
					person = null;
				}
				break;
			}
			event = pullParser.next();
		}
		return persons;
		
	}

}
