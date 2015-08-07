package flyweight;
//测试类
public class FlyWeightTest {
	public static void main(String[] args) {
		MySweetWordFactory factory = new MySweetWordFactory();
		MySweetWord word = factory.getMyCharacter("你好,宝贝");
		MySweetWord word2 = factory.getMyCharacter("你是我的天使");
		MySweetWord word3 = factory.getMyCharacter("你好,宝贝");
		MySweetWord word4 = factory.getMyCharacter("宝贝,我永远爱你");
		System.out.println(factory.getPool().size());//输出是3,说明有两个共享了内存,如果讲word3改成你好,值就变成了4
		//另一种检测方法
		if(word == word3){
			System.out.println("这俩个共享了内存");
		}
//		SweetWordFactory factory2 = new SpecialSweetWordFactory();
//		BaseSweetWord sweetWord = factory2.getMyCharacter("美女呀,来起撒");
//		BaseSweetWord sweetWord2 = factory2.getMyCharacter("美女呀,来起撒");
//		if(sweetWord == sweetWord2){
//			System.out.println("这俩个共享了内存");
//		}
	}
}
