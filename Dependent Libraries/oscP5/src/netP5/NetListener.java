package netP5;

public interface NetListener {
	
	public void netEvent(NetMessage theNetMessage);
	
	public void netStatus(NetStatus theStatus);
	
}
