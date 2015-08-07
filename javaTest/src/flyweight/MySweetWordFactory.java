package flyweight;

import java.util.HashMap;
import java.util.Map;
//享元工厂类
public class MySweetWordFactory {
	private Map<String, MySweetWord> pool;
	public MySweetWordFactory(){
		pool = new HashMap<String, MySweetWord>();
	}
	//核心
	public MySweetWord getMyCharacter(String character){
		MySweetWord mychar = pool.get(character);//享元的关键实现
		if(mychar == null){
			mychar = new MySweetWord(character);
			pool.put(character, mychar);
		}
		return mychar;
	}
	public Map<String, MySweetWord> getPool() {
		return pool;
	}
	public void setPool(Map<String, MySweetWord> pool) {
		this.pool = pool;
	}
	
	
}
