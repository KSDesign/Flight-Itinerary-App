package flightinfocenter;

import driver.Server;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Observable;


// TODO: Auto-generated Javadoc
/**
 * The Class Flight.
 */
public class Flight extends Observable implements Serializable {
  
  /**
   * serialVersionUID.
   */
  private static final long serialVersionUID = -3984718729689094177L;

  /** The flight number. */
  private String number;
  
  /** The departure date time. */
  private Date departureDateTime;
  
  /** The arrival date time. */
  private Date arrivalDateTime;
  
  /** The airline. */
  private String airline;
  
  /** The origin. */
  private String origin;
  
  /** The destination. */
  private String destination;
  
  /** The price. */
  private double price;
  
  private int numSeats;


  /**
   * Instantiates a new flight.
   *
   * @param number the flight number
   * @param departureDateTime the departure date time
   * @param arrivalDateTime the arrival date time
   * @param airline the airline
   * @param origin the origin
   * @param destination the destination
   * @param price the price
   * @throws ParseException the parse exception
   */
  public Flight(String number, String departureDateTime, String arrivalDateTime, 
      String airline, String origin, String destination, String price,
      String numSeats) throws ParseException {
    this.number = number;
    this.departureDateTime = Server.flightDateFormat.parse(departureDateTime);
    this.arrivalDateTime = Server.flightDateFormat.parse(arrivalDateTime);
    this.airline = airline;
    this.origin = origin;
    this.destination = destination;
    this.price = Double.parseDouble(price);
    this.numSeats = Integer.parseInt(numSeats);
  }
  
  /**
   * update Flight information.
   * @param  departureDateTime departureDateTime
   * @param arrivalDateTime arrivalDateTime
   * @param airline airline
   * @param origin origin
   * @param destination destination
   * @param price price
   * @param numSeats numSeats
   * @throws ParseException ParseException
   */
  public void updateFlight(String departureDateTime, String arrivalDateTime, 
      String airline, String origin, String destination, String price,
      String numSeats) throws ParseException {
    this.departureDateTime = Server.flightDateFormat.parse(departureDateTime);
    this.arrivalDateTime = Server.flightDateFormat.parse(arrivalDateTime);
    this.airline = airline;
    this.origin = origin;
    this.destination = destination;
    this.price = Double.parseDouble(price);
    this.numSeats = Integer.parseInt(numSeats.replace(" ", ""));
    changed();
  }

  /**
   * get Information Map.
   * @return LinkedHashMap
   */
  public LinkedHashMap<String, String> getInfoMap() {
    LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
    result.put("number",getNumber());
    result.put("departureDateTime",Server.flightDateFormat.format(getDepartureDateTime()));
    result.put("arrivalDateTime",Server.flightDateFormat.format(getArrivalDateTime()));
    result.put("airline",getAirline());
    result.put("origin",getOrigin());
    result.put("destination", getDestination());
    result.put("price", Double.toString(price));
    result.put("numSeats", Integer.toString(getNumSeats()));
    return result;
  }

  public void changed() {
    setChanged();
    notifyObservers();
  }

  /**
   * Gets the flight number.
   *
   * @return the number
   */
  public String getNumber() {
    return number;
  }


  /**
   * Gets the departure date time.
   *
   * @return the departure date time
   */
  public Date getDepartureDateTime() {
    return departureDateTime;
  }


  /**
   * Gets the arrival date time.
   *
   * @return the arrival date time
   */
  public Date getArrivalDateTime() {
    return arrivalDateTime;
  }


  /**
   * Gets the airline.
   *
   * @return the airline
   */
  public String getAirline() {
    return airline;
  }


  /**
   * Gets the origin.
   *
   * @return the origin
   */
  public String getOrigin() {
    return origin;
  }


  /**
   * Gets the destination.
   *
   * @return the destination
   */
  public String getDestination() {
    return destination;
  }


  /**
   * Gets the price.
   *
   * @return the price
   */
  public double getPrice() {
    return price;
  }

  /**
   * set the number of seats.
   * @param numSeats the numSeats to set
   */
  public void setNumSeats(int numSeats) {
    this.numSeats = numSeats;
  }

  public boolean book() {
    numSeats -= 1;
    return (numSeats >= 0);
  }

  /**
   * get the number of seats.
   * @return the numSeats
   */
  public int getNumSeats() {
    return numSeats;
  }


  /**
   * Calculate travel time in minutes.
   *
   * @return the string
   * @throws ParseException the parse exception
   */
  @SuppressWarnings("deprecation")
  public String duration() throws ParseException {
    int time = (int) ((arrivalDateTime.getTime() - departureDateTime.getTime()) / (1000 * 60));
    Date duration = Server.durationFormat.parse("00:00");
    duration.setMinutes(time % 60);
    duration.setHours(time / 60);
    return Server.durationFormat.format(duration);
  }

  public String timeString() {
    return Server.flightDateFormat.format(getDepartureDateTime()) + " -> "
      + Server.flightDateFormat.format(getArrivalDateTime());
  }

  public String placeString() {
    return getOrigin() + " -> " + getDestination();
  }
  /**
   * To CSV string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return toSearchItinerary() + "," + Double.toString(price);
  }
  
  /**
   * To string for search flight.
   *
   * @return the string
   */
  public String toSearchFlight() {
    return toSearchItinerary() + "," + String.format("%.2f", price);
  }

  /**
   * To string for search itinerary.
   *
   * @return the string
   */
  public String toSearchItinerary() {
    return getNumber() + "," + Server.flightDateFormat.format(getDepartureDateTime()) + ","
      + Server.flightDateFormat.format(getArrivalDateTime()) + "," + getAirline() 
      + "," + getOrigin() + "," + getDestination();
  }
  
}
