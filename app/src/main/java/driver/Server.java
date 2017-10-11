package driver;

import cs.b07.p3.MainActivity;
import flightinfocenter.Flight;
import flightinfocenter.FlightInfoCenter;
import flightinfocenter.Itinerary;
import userinfocenter.User;
import userinfocenter.UserInfoCenter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;




public class Server {
  public static DateFormat flightDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
  public static DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
  public static DateFormat durationFormat = new SimpleDateFormat("HH:mm");
  protected static FlightInfoCenter fic;
  protected static UserInfoCenter uic;
  public static String innerDir = "";
  public static String extraDir = "";
  public static MainActivity ma;

  public static void initiate() {
    fic = new FlightInfoCenter();
    uic = new UserInfoCenter();
  }
  /**
   * Uploads client information to the application from the file at the
   * given path.
   * @param path the path to an input csv file of client information with
   *     lines in the format: 
   *     LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
   *     (the ExpiryDate is stored in the format YYYY-MM-DD)
   * @throws IOException IOException
   */
  public static void uploadClientInfo(String path) throws IOException  {
    uic.readCsv(path);
  }

  /**
   * Uploads flight information to the application from the file at the
   * given path.
   * @param path the path to an input csv file of flight information with 
   *     lines in the format: 
   *     Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination,Price
   *     (the dates are in the format YYYY-MM-DD; the price has exactly two
   *      decimal places)
   * @throws IOException IOException
   */
  public static void uploadFlightInfo(String path) throws IOException {
    fic.readCsv(path);
  }

  /**
   * Returns the client with the given email. 
   * @param email the email address of a client
   * @return the client
   */  
  public static User getUser(String email) {
    return uic.searchUser(email);
  }

  /**
   * list of flights to string.
   *
   * @param flights the list of flights
   * @return the string
   */
  public static String flightsToString(List<Flight> flights) {
    if (flights.isEmpty()) {
      return "";
    }
    String result = "";
    for (Flight flight:flights) {
      result += flight.toSearchFlight() + "\n";
    }
    return result.substring(0, result.length() - 1);
  }

  /**
   * Gets the flights that depart from origin and arrive at destination on
   * the given date.
   *
   * @param date the date
   * @param origin the origin
   * @param destination the destination
   * @return the list of flights
   * @throws ParseException the parse exception
   */
  public static List<Flight> getFlights(String date, String origin, String destination) 
      throws ParseException {
    return getFlights(date, origin, destination, false);
  }

  public static List<Flight> getFlights(String date, String origin, String destination,
      boolean roughSearch) throws ParseException {
    Date departureDate = DateFormat.parse(date);
    return fic.searchFlight(departureDate, origin, destination, roughSearch);
  }

  public static Flight getFlight(String origin, String number) {
    return fic.getFlight(origin, number);
  }

  /**
   * list of itineraries to string.
   *
   * @param itineraries the list of itineraries
   * @return the string
   */
  public static String itinerariesToString(List<Itinerary> itineraries) {
    if (itineraries.isEmpty()) {
      return "";
    }
    String result = "";
    for (Itinerary it:itineraries) {
      result += it.toString() + "\n";
    }
    return result.substring(0, result.length() - 1);
  }

  /**
   * Returns all itineraries that depart from origin and arrive at
   * destination on the given date. If an itinerary contains two consecutive
   * flights F1 and F2, then the destination of F1 should match the origin of
   * F2. To simplify our task, if there are more than 6 hours between the
   * arrival of F1 and the departure of F2, then we do not consider this
   * sequence for a possible itinerary (we judge that the stopover is too
   * long).
   *
   * @param date the date
   * @param origin the origin
   * @param destination the destination
   * @param sortType the sort type
   * @return the list of itineraries
   * @throws ParseException the parse exception
   */
  public static List<Itinerary> getItineraries(String date, String origin, String destination, 
      String sortType) throws ParseException {
    Date departureDate = DateFormat.parse(date);
    List<Itinerary> result = fic.searchItinerary(departureDate, origin, destination);
    if (sortType.equals("time")) {
      Collections.sort(result, new SortbyTotalTime());
    } else if (sortType.equals("cost")) {
      Collections.sort(result, new SortbyTotalCost());
    }
    return result;
  }

  /**
   * get itineraries in list.
   * @param date date
   * @param origin origin
   * @param destination destination
   * @return List
   * @throws ParseException ParseException
   */
  public static List<Itinerary> getItineraries(String date, String origin, String destination) 
          throws ParseException {
    return getItineraries(date, origin, destination, "");
  }
  
  /**
   * save Data.
   * @throws IOException IOException
   */
  public static void saveData() throws IOException {
    if (fic != null && uic != null) {
      fic.saveData();
      uic.saveData();
    }
  }

  /**
   * saveUserData.
   * @throws IOException IOException
   */
  public static void saveUserData() throws IOException {
    if (uic != null) {
      uic.saveData();
    }
  }

  public static User login(String emial, String password) {
    return uic.login(emial, password);
  }

}
