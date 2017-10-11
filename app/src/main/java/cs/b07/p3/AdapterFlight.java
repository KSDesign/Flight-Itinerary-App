package cs.b07.p3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import flightinfocenter.Flight;

// TODO: Auto-generated Javadoc
/**
 * Created by Shiqi on 2015/12/1.
 */
public class AdapterFlight extends BaseAdapter {
  
  /** The inflater. */
  private LayoutInflater inflater;
  
  /** The item views. */
  View[] itemViews;

  /**
   * Instantiates a new adapter flight.
   *
   * @param context the context
   * @param flights the flights
   */
  public AdapterFlight(Context context, List<Flight> flights) {
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    itemViews = new View[flights.size()];
    for (int i = 0; i < flights.size(); i++) {
      itemViews[i] = makeItemView(flights.get(i));
    }
  }

  /* (non-Javadoc)
   * @see android.widget.Adapter#getCount()
   */
  public int getCount() {
    return itemViews.length;
  }

  /* (non-Javadoc)
   * @see android.widget.Adapter#getItem(int)
   */
  public View getItem(int position) {
    return itemViews[position];
  }

  /* (non-Javadoc)
   * @see android.widget.Adapter#getItemId(int)
   */
  public long getItemId(int position) {
    return position;
  }

  /**
   * Make item view.
   *
   * @param flight the flight
   * @return the view
   */
  private View makeItemView(Flight flight) {
    View itemView = inflater.inflate(R.layout.item_flight, null);
    ((TextView) itemView.findViewById(R.id.flightNumber)).setText(flight.getNumber());
    ((TextView) itemView.findViewById(R.id.place)).setText(flight.placeString());
    ((TextView) itemView.findViewById(R.id.time)).setText(flight.timeString());
    ((TextView) itemView.findViewById(R.id.airLine)).setText(flight.getAirline());
    return itemView;
  }

  /* (non-Javadoc)
   * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
   */
  public View getView(int position, View convertView, ViewGroup parent) {
    return itemViews[position];
  }
}