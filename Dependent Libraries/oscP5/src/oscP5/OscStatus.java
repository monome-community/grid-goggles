package oscP5;

/**
 * @invisible
 */
public class OscStatus {


  public static int ERROR = -1;

  public static int DEFAULT = 0;

  public static int CONNECTION_CLOSED = 1;

  public static int CONNECTION_REFUSED = 2;

  public static int CONNECTION_TERMINATED = 4;

  public static int CONNECTION_FAILED = 8;

  public static int SERVER_CLOSED = 16;

  public static int CLIENT_CLOSED = 32;

  public static int SEND_FAILED = 64;

  public static int OSCP5_CLOSED = 1024;

  private int _myIndex = DEFAULT;


  public OscStatus(int theIndex) {
    _myIndex = theIndex;
  }


  public int id() {
    return _myIndex;
  }

}
