package flightinfocenter;

import driver.Server;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


// TODO: Auto-generated Javadoc
/**
 * The Class Itinerary.
 */
public class Itinerary extends Observable implements Observer, Serializable {
  
  /**
   * serialVersionUID.
   */
  private static final long serialVersionUID = -2093576596472509376L;

  /** list of flights. */
  List<Flight> flights = new ArrayList<Flight>();
  
  /** The cities that passed. */
  List<String> cities = new ArrayList<String>();
  
  /**
   *  a new empty itinerary.
   */
  public Itinerary(){
  }
  
  /**
   * a new itinerary with one flight.
   *
   * @param flight the fisrt flight
   */
  public Itinerary(Flight flight) {
    flights.add(flight);
    cities.add(flight.getOrigin());
    cities.add(flight.getDestination());
  }

  /**
   *  a new itinerary with given flights.
   *
   * @param flights the list of flights
   */
  public Itinerary(List<Flight> flights) {
    this.flights.addAll(flights);
    cities.add(getFirstFlight().getOrigin());
    for (Flight flight:flights) {
      cities.add(flight.getDestination());
    }
  }

  /**
   * build observe relationship.
   */
  public void newObserveLinks() {
    for (int i = 0;i < getFlights().size();i++) {
      Flight flight = getFlights().get(i);
      Flight newFlight = Server.getFlight(flight.getOrigin(), flight.getNumber());
      newFlight.addObserver(this);
      getFlights().set(i, newFlight);
    }
  }

  /**
   * notify observer.
   */
  public void update(Observable flight, Object arg) {
    setChanged();
    notifyObservers(flight);
  }
  
  /**
   * check if avliable to add this flight.
   *
   * @param flight the flight
   * @return true, if able to add
   */
  public boolean avliableToAddFlight(Flight flight) {
    if (flight.getNumSeats() <= 0) {
      return false;
    } else if (flights.isEmpty()) {
      return true;
    } else if (cities.contains(flight.getDestination())
        || (!getLastFlight().getDestination().equals(flight.getOrigin()))) {
      return false; 
    } else if (FlightInfoCenter.timeCompare(flight.getDepartureDateTime(), 
        getLastFlight().getArrivalDateTime()) < FlightInfoCenter.minWaitingHours
        || (FlightInfoCenter.timeCompare(flight.getDepartureDateTime(), 
            getLastFlight().getArrivalDateTime()) > FlightInfoCenter.maxWaitingHours)) {
      return false;
    }
    return true;
  }
   
  /**
   * Adds the flight into itinerary.
   *
   * @param flight the new flight
   * @return true, if successful
   */
  public boolean addFlight(Flight flight) {
    if (avliableToAddFlight(flight)) {
      if (flights.isEmpty()) {
        cities.add(flight.getOrigin());
      }
      flights.add(flight);
      cities.add(flight.getDestination());
      return true;
    }
    return false;
  }
  
  /**
   * Gets the last flight.
   *
   * @return the last flight
   */
  public Flight getLastFlight() {
    return flights.get(flights.size() - 1);
  }
  
  public Flight getFirstFlight() {
    return flights.get(0);
  }
  
  /**
   * Gets the flights.
   *
   * @return the flights
   */
  public List<Flight> getFlights() {
    return flights;
  }

  /**
   * book flight.
   */
  public void book() {
    for (Flight flight:flights) {
      flight.book();
    }
  }
  
  /**
   * Total cost.
   * @return the cost in double
   */
  public double totalCost() {
    double cost = 0.0;
    for (Flight fli:flights) {
      cost += fli.getPrice();
    }
    return cost;
  }
  
  /**
   * Total time in durationFormat.
   *
   * @return the total time
   */
  @SuppressWarnings("deprecation")
  public String totalTime() {
    int time = totalTimeInMinute();
    Date duration = null;
    try {
      duration = Server.durationFormat.parse("00:00");
      duration.setMinutes(time % 60);
      duration.setHours(time / 60);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return Server.durationFormat.format(duration);
  }
  
  /**
   * Total time in minute.
   *
   * @return the total time
   */
  public int totalTimeInMinute() {
    if (flights.isEmpty()) {
      return 0;
    }
    return (int) ((getLastFlight().getArrivalDateTime().getTime()
        - getFirstFlight().getDepartureDateTime().getTime()) / (1000 * 60));
  }

  public String timeString() {
    return Server.flightDateFormat.format(getFirstFlight().getDepartureDateTime()) + " -> "
      + Server.flightDateFormat.format(getLastFlight().getArrivalDateTime());
  }

  public String placeString() {
    return getFirstFlight().getOrigin() + " -> " + getLastFlight().getDestination();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String result = "";
    for (Flight flight:flights) {
      result += flight.toSearchItinerary() + "\n";
    }
    result += String.format("%.2f", totalCost()) + "\n";
    result += totalTime();
    return result;
  }

}
