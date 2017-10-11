package driver;

import flightinfocenter.Itinerary;

import java.util.Comparator;

public class SortbyTotalTime implements Comparator<Itinerary> {

  @Override
  public int compare(Itinerary arg0, Itinerary arg1) {
    return arg0.totalTimeInMinute() - arg1.totalTimeInMinute();
  }

}
