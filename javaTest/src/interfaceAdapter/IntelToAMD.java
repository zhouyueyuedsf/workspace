package interfaceAdapter;

public class IntelToAMD implements AMD{
	private Intel cpu;
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
