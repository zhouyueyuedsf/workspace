package factory;

public class CycleFactory {
	//工厂制造函数
	public static void construct(Cycle c){
		c.go();
	}
	public static void main(String[] args) {
		construct(new Bicycle());//制造一个自行车
		construct(new Tricycle());
		construct(new Unicycle());
	}
}
