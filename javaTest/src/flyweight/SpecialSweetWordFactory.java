package flyweight;

import java.util.HashMap;
import java.util.Map;

public class SpecialSweetWordFactory implements SweetWordFactory{
	private Map<String, BaseSweetWord> pool;
	public SpecialSweetWordFactory() {
		// TODO Auto-generated constructor stub
		pool = new HashMap<String, BaseSweetWord>();
	}
	public BaseSweetWord getMyCharacter(String character) {
		// TODO Auto-generated method stub
		BaseSweetWord mychar = pool.get(character);//享元的关键实现
		if(mychar == null){
			mychar = new SpecialSweetWord(character);
			pool.put(character, mychar);
		}
		
		return mychar;
		
	}
	public Map<String, BaseSweetWord> getPool() {
		return pool;
	}
	public void setPool(Map<String, BaseSweetWord> pool) {
		this.pool = pool;
	}

}
