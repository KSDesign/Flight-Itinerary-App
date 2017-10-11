package cs.b07.p3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import driver.Server;
import flightinfocenter.Flight;
import userinfocenter.User;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;



// TODO: Auto-generated Javadoc
/**
 * The Class ShowFlightsActivity.
 */
public class ShowFlightsActivity extends AppCompatActivity {

  /** The user. */
  protected User user;
  
  /** The date. */
  protected String date;
  
  /** The origin. */
  protected String origin;
  
  /** The destination. */
  protected String destination;
  
  /** The flights. */
  protected List<Flight> flights;
  
  /** The list. */
  protected ListView list;
  
  /** The choice. */
  protected Flight choice;
  
  /** The adapter. */
  protected AdapterFlight adapter;
  
  /**
   * On create.
   *
   * @param savedInstanceState the saved instance state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_flights);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    user = (User) Server.getUser((String) intent.getSerializableExtra("user"));
    date = (String)intent.getSerializableExtra("date");
    origin = (String)intent.getSerializableExtra("origin");
    destination = (String)intent.getSerializableExtra("destination");
    list = (ListView) findViewById(R.id.listView);

    list.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
        choice = flights.get(position);
        booking();
      }
    });
    try {
      flights = Server.getFlights(date, origin, destination);
    } catch (ParseException e) {
      flights = new ArrayList<Flight>();
      Toast.makeText(this, "wrong format", Toast.LENGTH_LONG).show();
    }
    display();
  }

  /**
   * Booking.
   */
  public void booking() {
    try {
      new AlertDialog.Builder(this).setTitle("book this flight?").setPositiveButton("Yes",
              new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          try {
            user.bookFlight(choice);
            Toast.makeText(getBaseContext(), "booking success", Toast.LENGTH_LONG).show();
          } catch (IOException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
          }
        }
      }).setNegativeButton("No", null).create().show();
    } catch (Exception e) {
      Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
  }

  /**
   * Display.
   */
  private void display() {
    adapter = new AdapterFlight(this,flights);
    list.setAdapter(adapter);
  }

}
