package netP5;

public interface NetP5 {
	
  String VERSION = "0.9.5";
  
  boolean DEBUG = true;
  
  /**
   * @related setNetworkProtocol ( )
   */
  public final static int UDP = 0;

  /**
   * @related setNetworkProtocol ( )
   */
  public final static int MULTICAST = 1;


  /**
   * @related setNetworkProtocol ( )
   */
  public final static int TCP = 2;
  /**
   * TODO
   * authentification in AbstractTcpServer and  AbstractUdpServer. 
   * TcpServer.authentificationRequired(true/false); 
   * UdpServer.authentificationRequired(true/false);
   */
}
