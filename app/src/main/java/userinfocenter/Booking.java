package userinfocenter;

import android.util.Log;

import driver.Server;
import flightinfocenter.Flight;
import flightinfocenter.Itinerary;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;


public class Booking extends Observable implements Observer, Serializable {

  private static final long serialVersionUID = -6608569938776472486L;
  private User owner;
  private Itinerary it;
  private boolean changed = false;
  private Date orderDate;
  
  
  /**
   * order a booking.
   * @param owner owner
   * @param itinerary itinerary
   */
  public Booking(User owner, Itinerary itinerary) {
    orderDate = new Date();
    this.owner = owner;
    it = itinerary;
    it.book();
    it.addObserver(this);
  }

  protected void newObserveLinks() {
    it.addObserver(this);
    it.newObserveLinks();
  }

  /**
   * get itinerary.
   * @return the it
   */
  public Itinerary getItinerary() {
    return it;
  }

  public String bookingDate() {
    return Server.DateFormat.format(orderDate);
  }

  public boolean getChanged() {
    return changed;
  }

  @Override
  public void update(Observable it, Object flight) {
    changed = true;
    setChanged();
    notifyObservers();
  }
  
  /**
   * toString.
   */
  public String toString() {
    String str = getItinerary().toString();
    if (changed) {
      str += "\nFlight info has been changed";
    }
    return str;
  }
  
}
