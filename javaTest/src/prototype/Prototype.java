package prototype;

import java.util.ArrayList;
import java.util.List;



public class Prototype implements Cloneable {
	private String content;
	private List<String> contents = new ArrayList<String>();
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getContents() {
		return contents;
	}
	public void setContents(List<String> contents) {
		this.contents = contents;
	}
	public Prototype clone(){
		try {
			Prototype prototype = (Prototype)super.clone();//clone为关键函数
			List<String> newContents = new ArrayList<String>();
			for(String word:this.getContents()){
				newContents.add(word);
				
			}		
			prototype.setContents(newContents);
			return prototype;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
				
	}
	
}
