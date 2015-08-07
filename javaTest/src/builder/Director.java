package builder;
/**
 * 导演类,建房子的顺序不一样
 * @author zhouyueyue
 *
 */
public class Director {
//	LoverNestBuilder builder = new LoverNestBuilder();
	public void makeHouse(HouseBuilder buider){
		buider.makeFloor();
		buider.makeWall();
		buider.makeHouseTop();
	}
}
