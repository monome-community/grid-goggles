package netP5;

/**
 * @author andreas schlegel
 */
public class NetStatus {


  public static int ERROR = -1;

  public static int DEFAULT = 0;

  public static int CONNECTION_CLOSED = 1;

  public static int CONNECTION_REFUSED = 2;

  public static int CONNECTION_TERMINATED = 4;

  public static int CONNECTION_FAILED = 8;

  public static int SERVER_CLOSED = 16;

  public static int CLIENT_CLOSED = 32;

  public static int SEND_FAILED = 64;

  private int _myIndex = DEFAULT;


  public NetStatus(int theIndex) {
    _myIndex = theIndex;
  }



  public int id() {
    return _myIndex;
  }

}
