package builder;

public class LoverNestBuilder implements HouseBuilder {
	LoverNest loverNest  = new LoverNest();
	public void makeFloor() {
		// TODO Auto-generated method stub
		loverNest.setFloor("你的爱巢地板装修好了");
	}

	public void makeWall() {
		// TODO Auto-generated method stub

	loverNest.setWall("你的爱巢墙壁装修好了");
	}

	public void makeHouseTop() {
		// TODO Auto-generated method stub

		loverNest.setHouseTop("你的爱巢屋顶装修好了");
	}

	public LoverNest getHouse() {
		// TODO Auto-generated method stub
		return loverNest;
	}
	
}
