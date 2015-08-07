package facade;
//基金类
public class Fund {
	Futures futures;
	NationalDebt nationalDebt;
	Stock stock;
	public Fund() {
		// TODO Auto-generated constructor stub
		this.futures = new Futures();
		this.nationalDebt = new NationalDebt();
		this.stock = new Stock();
	}
	//核心
	/**
	 * 够买期货和国债
	 */
	public void FundA(){
		this.futures.buy();
		this.nationalDebt.buy();
	}
	/**
	 * 购买国债和股票
	 */
	public void FundB(){
		this.nationalDebt.buy();
		this.stock.buy();
	}
	/**
	 * 购买期货和股票
	 */
	public void FundC(){
		this.futures.buy();
		this.stock.buy();
	}
}
