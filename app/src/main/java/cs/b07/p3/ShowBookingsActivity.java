package cs.b07.p3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import driver.Server;
import userinfocenter.Booking;
import userinfocenter.User;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowBookingsActivity.
 */
public class ShowBookingsActivity extends AppCompatActivity {
  
  /** The user. */
  protected User user;
  
  /** The books. */
  protected List<Booking> books;
  
  /** The list. */
  protected ListView list;
  
  /** The choice. */
  protected Booking choice;
  
  /** The adapter. */
  protected AdapterBooking adapter;
  
  /**
   * On create.
   *
   * @param savedInstanceState the saved instance state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_bookings);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    user = (User) Server.getUser((String) intent.getSerializableExtra("user"));
    list = (ListView) findViewById(R.id.listView);
    books = user.getBookings();
    list.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
        choice = books.get(position);
        goBookingPage();
      }
    });
    display();
  }

  /**
   * Display.
   */
  private void display() {
    adapter = new AdapterBooking(this, books);
    list.setAdapter(adapter);
  }

  /**
   * Go booking page.
   */
  private void goBookingPage() {
    Intent bookingPage = new Intent(this, BookingActivity.class);
    bookingPage.putExtra("book", choice);
    startActivity(bookingPage);
  }

  /**
   * Clean booking.
   *
   * @param view the view
   */
  public void cleanBooking(View view) {
    try {
      user.cleanBookingHistory();
      Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
    } catch (Exception e) {
      Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
  }

}
