package builder;

public class builderTest {
	public static void main(String[] args) {
		HouseBuilder builder = new LoverNestBuilder();//意思是这个房屋建造者是专门的爱巢装修公司
		Director director = new Director();
		director.makeHouse(builder);//房屋建造完成
	
		//返回房屋
		LoverNest loverNest = builder.getHouse();
		System.out.println(loverNest.getFloor());
		System.out.println(loverNest.getWall());
		System.out.println(loverNest.getHouseTop());
	}
}
