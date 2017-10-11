package userinfocenter;

import flightinfocenter.Flight;
import flightinfocenter.Itinerary;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public abstract class User extends Observable implements Observer, Serializable {

  private static final long serialVersionUID = -6343185364637706199L;
  private String firstName;
  private String lastName;
  private String email;
  private String address;
  private String cardNumber;
  private String expiryDate;
  private String password = null;
  private List<Booking> bookings;

  /**
   * Constructor for User.
   *
   * @param lastName the last name of the user
   * @param firstName the first name of the user
   * @param email the Email of the user
   * @param address the address of the userDate
   * @param cardNumber the credit card number of the user
   * @param expiryDate the expiry date of the user's credit card
   */

  public User(String lastName, String firstName, String email, String address, 
      String cardNumber, String expiryDate) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.address = address;
    this.cardNumber = cardNumber;
    this.expiryDate = expiryDate.replace(" ", "");
    bookings = new ArrayList<Booking>();
  }
  
  /**
   * change personal info.
   * @param lastName lastName
   * @param firstName firstName
   * @param address address
   * @param cardNumber cardNumber
   * @param expiryDate expiryDate
   */
  public void editInfomations(String lastName, String firstName, String address,
      String cardNumber, String expiryDate) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.address = address;
    this.cardNumber = cardNumber;
    this.expiryDate = expiryDate;
  }

  public void changed() {
    setChanged();
    notifyObservers();
  }

  /**
   * get info map.
   * @return LinkedHashMap
   */
  public LinkedHashMap<String, String> getInfoMap() {
    LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
    result.put("email",getEmail());
    result.put("last name",getLastName());
    result.put("first name",getFirstName());
    result.put("address",getAddress());
    result.put("card number",getCardNumber());
    result.put("expiry date", getExpiryDate());
    result.put("password", getPassword());
    return result;
  }

  /**
   * get the first name.
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * get the last name.
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * get the Email address.
   * @return the email
   */
  public String getEmail() {
    return email;
  }


  /**
   * get the address.
   * 
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * get the credit card number.
   * 
   * @return the cardNumber
   */
  public String getCardNumber() {
    return cardNumber;
  }

  /**
   * get the expiry date of the credit card.
   * 
   * @return the expiryDate
   */
  public String getExpiryDate() {
    return expiryDate;
  }

  /**
   * get the password of the user's account.
   * 
   * @return the password
   */
  protected String getPassword() {
    return password;
  }

  /**
   * set the password of the user's account.
   * 
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * get list of bookings.
   * @return the bookings
   */
  public List<Booking> getBookings() {
    return bookings;
  }
  
  protected void addBooking(Booking book) {
    bookings.add(book);
  }
  
  /**
   * book a itinerary.
   * @param it itinerary-itself
   * @throws IOException IOException
   */
  public void bookItinerary(Itinerary it) throws IOException {
    Booking book = new Booking(this, it);
    addBooking(book);
    book.addObserver(this);
    changed();
  }

  /**
   * book a flight.
   * @param flight flight
   * @throws IOException IOException
   */
  public void bookFlight(Flight flight) throws IOException {
    Itinerary it = new Itinerary();
    it.addFlight(flight);
    bookItinerary(it);
  }

  /**
   * build observe relationship.
   */
  public void newObserveLinks() {
    for (Booking book:getBookings()) {
      book.addObserver(this);
      book.newObserveLinks();
    }
  }

  public void cleanBookingHistory() {
    bookings.clear();
    changed();
  }
  
  @Override
  public String toString() {
    return getLastName() + "," + getFirstName() + "," + getEmail() + "," + getAddress() + "," 
      + getCardNumber() + "," + getExpiryDate();
  }
  
  public void update(Observable book, Object fliht) {

  }

}
