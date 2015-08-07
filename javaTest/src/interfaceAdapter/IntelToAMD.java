package interfaceAdapter;
//建立Intel到AMD的适配器
public class IntelToAMD implements AMD{
	private Intel cpu;
	public IntelToAMD(Intel intel) {
		this.setCpu(intel);//I7CPU插入适配器
		// TODO Auto-generated constructor stub
	}
	public void ac() {
		// TODO Auto-generated method stub
		cpu.power();
	}
	public Intel getCpu() {
		return cpu;
	}
	public void setCpu(Intel cpu) {
		this.cpu = cpu;
	}
	
}
