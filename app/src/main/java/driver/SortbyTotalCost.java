package driver;

import flightinfocenter.Itinerary;

import java.util.Comparator;

public class SortbyTotalCost implements Comparator<Itinerary> {

  @Override
  public int compare(Itinerary arg0, Itinerary arg1) {
    return (int)((arg0.totalCost() - arg1.totalCost()) * 100);
  }

}
