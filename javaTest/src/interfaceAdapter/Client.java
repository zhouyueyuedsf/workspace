package interfaceAdapter;

public class Client {
	public static void main(String[] args) {
		AMDmainBorad main = new AMDmainBorad();
		IntelToAMD toAMD = new IntelToAMD();
		Intel cpu = new I7();
		toAMD.setCpu(cpu);//I7CPU插入适配器
		main.setCpu(toAMD);//要想转为AMD，必须要设计一个AMD接口，将适配器插入主板
		toAMD.ac();//适配器运行
	}
}