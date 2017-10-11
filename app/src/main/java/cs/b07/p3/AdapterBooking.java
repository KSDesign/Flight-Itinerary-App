package cs.b07.p3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import userinfocenter.Booking;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Created by Shiqi on 2015/12/1.
 */
public class AdapterBooking extends BaseAdapter {
  
  /** The inflater. */
  private LayoutInflater inflater;
  
  /** The item views. */
  View[] itemViews;

  /**
   * Instantiates a new adapter booking.
   *
   * @param context the context
   * @param bookings the bookings
   */
  public AdapterBooking(Context context, List<Booking> bookings) {
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    itemViews = new View[bookings.size()];
    for (int i = 0; i < bookings.size(); i++) {
      itemViews[i] = makeItemView(bookings.get(i));
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
   * @param book the book
   * @return the view
   */
  private View makeItemView(Booking book) {
    View itemView = inflater.inflate(R.layout.item_book, null);
    TextView date = (TextView) itemView.findViewById(R.id.Date);
    TextView place = (TextView) itemView.findViewById(R.id.place);
    TextView time = (TextView) itemView.findViewById(R.id.time);
    date.setText("Date booked: " + book.bookingDate());
    place.setText(book.getItinerary().placeString());
    time.setText(book.getItinerary().timeString());
    return itemView;
  }

  /* (non-Javadoc)
   * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
   */
  public View getView(int position, View convertView, ViewGroup parent) {
    return itemViews[position];
  }
}