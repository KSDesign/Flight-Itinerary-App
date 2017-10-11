package flightinfocenter;

import driver.Server;
import driver.SortbyDepartureTime;

import java.io.FileInputStream;
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



public class FlightInfoCenter {
   
  private Map<String,List<Flight>> flights;
  // slightly increase the maximum waiting time, to accept flights on the boundary
  protected static double maxWaitingHours = 6.001;
  protected static double minWaitingHours = 0.5;
  private static String filesDir = "fic.txt";
  
  /**
   * build flight information center.
   */
  @SuppressWarnings("unchecked")
  public FlightInfoCenter() {
    ObjectInputStream input = null;  
    try {
      input = new ObjectInputStream(new FileInputStream(Server.innerDir + filesDir));
      flights = (HashMap<String,List<Flight>>)input.readObject(); 
      input.close();  
    } catch (Exception e) {
      flights = new HashMap<String,List<Flight>>();
    }
  }
  /**
   * Read csv from giving file.
   *
   * @param path the path of file
   * @throws IOException IOException
   */
  public void readCsv(String path) throws IOException {
    Scanner scanner = new Scanner(new FileInputStream(path));
    String [] thisLine;

    while (scanner.hasNextLine()) {
      thisLine = scanner.nextLine().split(",",8);
      if (thisLine.length < 8) {
        continue;
      }
      Flight flight = deleteFlightFromMap(thisLine[0]);
      
      try {
        if (flight == null) {
          flight = new Flight(thisLine[0], thisLine[1], thisLine[2], thisLine[3], 
              thisLine[4], thisLine[5], thisLine[6], thisLine[7]);
        } else {
          flight.updateFlight(thisLine[1], thisLine[2], thisLine[3], thisLine[4], 
              thisLine[5], thisLine[6], thisLine[7]);
        }
        if (flights.containsKey(thisLine[4])) {
          flights.get(thisLine[4]).add(flight);
        } else {
          // if key doesn't exist
          List<Flight> list = new ArrayList<Flight>();
          list.add(flight);
          flights.put(thisLine[4], list);
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }

      for (List<Flight> flis:flights.values()) {
        Collections.sort(flis, new SortbyDepartureTime());
      }
    }
    scanner.close();
    saveData();
  }
  
  private Flight deleteFlightFromMap(String number) {
    for (List<Flight> flis:flights.values()) {
      for (Flight flight:flis) {
        if (flight.getNumber().equals(number)) {
          flis.remove(flight);
          return flight;
        }
      }
    }
    return null;
  }
  
  /**
   * get flight.
   * @param origin origin
   * @param number number
   * @return Flight
   */
  public Flight getFlight(String origin, String number) {
    List<Flight> flis = flights.get(origin);
    for (Flight flight:flis) {
      if (flight.getNumber().equals(number)) {
        return flight;
      }
    }
    return null;
  }
  
  /**
   * Returns all flights that depart from origin and arrive at destination on
   * the given time period. 
   * @param date departure date
   * @param origin flight origin
   * @param destination flight destination
   * @param roughSearch if true, the destination check disable
   * @param maxStopOver number of maximum hour to wait
   * @return the list of flights that depart from origin
   */
  public List<Flight> searchFlight(Date date, String origin, String destination,
      boolean roughSearch, double maxStopOver, double minStopOver) {
    List<Flight> result = new ArrayList<Flight>();
    if (flights.containsKey(origin)) {
      for (Flight flight:flights.get(origin)) {
        //timeCompare(flight.getDepartureDateTime(), date) < 0.0
        if (timeCompare(flight.getDepartureDateTime(), date) < minStopOver) {
          continue;
        } else if (timeCompare(flight.getDepartureDateTime(), date) >= maxStopOver) {
          break;
        } else if (roughSearch || flight.getDestination().equals(destination)) {
          result.add(flight);
        }
      }
    }
    return result;
  }
  
  /**
   * Returns all flights that depart from origin and arrive at destination on
   * the given date. 
   * @param date departure date
   * @param origin flight origin
   * @param destination flight destination
   * @param roughSearch if true, the destination check disable
   * @return the list of flights that depart from origin
   */
  public List<Flight> searchFlight(Date date, String origin, String destination,
      boolean roughSearch) {
    return searchFlight(date, origin, destination, roughSearch, 24.0, 0.0);
  }
  
  /**
   * Returns all flights that depart from origin and arrive at destination on
   * the given date. 
   * @param date departure date
   * @param origin flight origin
   * @param destination flight destination
   * @return the list of flights that depart from origin and arrive at destination on the date. 
   */
  public List<Flight> searchFlight(Date date, String origin, String destination) {
    return searchFlight(date, origin, destination, false, 24.0, 0.0);
  }
  
  /**
   * Returns all itineraries that depart from origin and arrive at
   * destination on the given date. If an itinerary contains two consecutive
   * flights F1 and F2, then the destination of F1 should match the origin of
   * F2. To simplify our task, if there are more than 6 hours between the
   * arrival of F1 and the departure of F2, then we do not consider this
   * sequence for a possible itinerary (we judge that the stopover is too
   * long).
   * @param date a departure date
   * @param origin a flight original
   * @param destination a flight destination
   * @return list of itineraries that depart from origin and arrive at
   *     destination on the given date with stopovers at or under 6 hours.
   */
  public List<Itinerary> searchItinerary(Date date, String origin, String destination) {
    List<Flight> flights = searchFlight(date, origin, destination, true);
    List<Itinerary> result = new ArrayList<Itinerary>();
    for (Flight flight:flights) {
      if (flight.getNumSeats() <= 0) {
        continue;
      }
      result.addAll(searchItineraryHelper(new Itinerary(flight), destination));
    }
    return result;
  }
  
  /**
   * Returns list of itineraries that extend given itinerary to next position 
   * with stopovers at or under 6 hours. or return the given itinerary in a list
   * if its already at destination
   * @param itinerary a itinerary
   * @param destination a destination
   * @return list of itineraries that extend given itinerary
   */
  public List<Itinerary> searchItineraryHelper(Itinerary itinerary, String destination) {
    List<Itinerary> result = new ArrayList<Itinerary>();
    Flight lastFlight = itinerary.getLastFlight();
    if (lastFlight.getDestination().equals(destination)) {
      result.add(itinerary);
    } else {
      List<Flight> flights = itinerary.getFlights();
      List<Flight> avliableflights = searchFlight(lastFlight.getArrivalDateTime(), 
          lastFlight.getDestination(), destination, true, maxWaitingHours, minWaitingHours);
      for (Flight flight:avliableflights) {
        if (itinerary.avliableToAddFlight(flight)) {
          Itinerary newItinerary = new Itinerary(flights);
          newItinerary.addFlight(flight);
          result.addAll(searchItineraryHelper(newItinerary, destination));
        }
      }
    }
    return result;
  }

  /**
   * save Date.
   * @throws IOException IOException
   */
  public void saveData() throws IOException {
    ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream(Server.innerDir + filesDir));  
    out.writeObject(flights);  
    out.close();
  }
  
  /**
   * Returns hours different between given time.
   * @param d1 the earlier time
   * @param d2 the later time
   * @return the different.
   */
  public static double timeCompare(Date d1, Date d2) {
    return (d1.getTime() - d2.getTime()) / (1000 * 60 * 60.0);
  }
  
}
