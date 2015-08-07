package interfaceAdapter;
//建立测试客户端
public class Client {
	public static void main(String[] args) {
		AMDmainBorad main = new AMDmainBorad();//建立一个AMD主板
		Intel cpu = new I7();
		IntelToAMD toAMD = new IntelToAMD(cpu);//Intel到AMD的适配器,参数为适配器的数据
		main.setCpu(toAMD);//要想转为AMD，必须要设计一个AMD接口，将适配器插入主板
		toAMD.ac();//适配器运行
	}
}
