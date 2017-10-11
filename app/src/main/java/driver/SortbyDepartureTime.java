package driver;

import flightinfocenter.Flight;

import java.util.Comparator;

public class SortbyDepartureTime implements Comparator<Flight> {
  @Override
  public int compare(Flight arg0, Flight arg1) {
    return (int) (arg0.getDepartureDateTime().getTime() - arg1.getDepartureDateTime().getTime());
  }
}
