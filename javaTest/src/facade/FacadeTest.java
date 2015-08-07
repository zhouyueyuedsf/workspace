package facade;
//建立一个测试类
public class FacadeTest {
	public static void main(String[] args) {
		//直接与外观Fund进行交互
		Fund fund = new Fund();
		fund.FundA();
		fund.FundB();
		fund.FundC();
	}
}
