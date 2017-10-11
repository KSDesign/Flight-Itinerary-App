package userinfocenter;

import driver.Server;

import java.io.IOException;

public class Admin extends User {

  /**
   * serialVersionUID.
   */
  private static final long serialVersionUID = 3100886350578840165L;

  /**
   * Admin.
   * @param lastName lastName
   * @param firstName firstName
   * @param email email
   * @param address address
   * @param cardNumber cardNumber
   * @param expiryDate expiryDate
   */
  public Admin(String lastName, String firstName, String email, String address,
      String cardNumber, String expiryDate) {
    super(lastName, firstName, email, address, cardNumber, expiryDate);
    // TODO Auto-generated constructor stub
  }
  
  /**
   * upload personal info.
   * @param path path
   * @throws IOException IOException
   */
  protected void uploadPersonalInfo(String path) throws IOException {
    Server.uploadClientInfo(path);
  }
  
  /**
   * upload flights info.
   * 
   * @param path path
   * @throws IOException IOException
   */
  protected void uploadFlightsInfo(String path) throws IOException {
    Server.uploadFlightInfo(path);
  }
  
  /**
   * view client info.
   * @param email email
   * @return String client
   */
  public Client getClient(String email) {
    try {
      return (Client) Server.getUser(email);
    } catch (ClassCastException e) {
      return null;
    }
    
        
  }

}
