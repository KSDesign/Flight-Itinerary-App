package cs.b07.p3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import flightinfocenter.Itinerary;

// TODO: Auto-generated Javadoc
/**
 * Created by Shiqi on 2015/12/1.
 */
public class AdapterItinerary extends BaseAdapter {
  
  /** The inflater. */
  private LayoutInflater inflater;
  
  /** The item views. */
  View[] itemViews;

  /**
   * Instantiates a new adapter itinerary.
   *
   * @param context the context
   * @param itineraries the itineraries
   */
  public AdapterItinerary(Context context, List<Itinerary> itineraries) {
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    itemViews = new View[itineraries.size()];
    for (int i = 0; i < itineraries.size(); i++) {
      itemViews[i] = makeItemView(i + 1,itineraries.get(i));
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
   * @param i the i
   * @param it the it
   * @return the view
   */
  private View makeItemView(int position,Itinerary it) {
    View itemView = inflater.inflate(R.layout.item_itinerary, null);
    TextView number = (TextView) itemView.findViewById(R.id.flightNumber);
    TextView cost = (TextView) itemView.findViewById(R.id.cost);
    TextView time = (TextView) itemView.findViewById(R.id.time);
    number.setText("" + position);
    cost.setText("total cost: $" + String.format("%.2f", it.totalCost()));
    time.setText("total time: " + it.totalTime());
    return itemView;
  }

  /* (non-Javadoc)
   * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
   */
  public View getView(int position, View convertView, ViewGroup parent) {
    return itemViews[position];
  }
}