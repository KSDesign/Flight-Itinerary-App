package cs.b07.p3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import driver.Server;
import flightinfocenter.Itinerary;
import userinfocenter.User;

// TODO: Auto-generated Javadoc
/**
 * The Class ItineraryActivity.
 */
public class ItineraryActivity extends AppCompatActivity {
  
  /** The itinerary. */
  private Itinerary itinerary;
  
  /** The user. */
  private User user;
  
  /** The flights strings. */
  private List<String> flightsStrings;
  
  /** The list. */
  private ListView list;
  
  /** The adapter. */
  private AdapterFlight adapter;
  
  /**
   * On create.
   *
   * @param savedInstanceState the saved instance state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_itinerary);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    list = (ListView) findViewById(R.id.listView);
    user = (User) Server.getUser((String) intent.getSerializableExtra("user"));
    itinerary = (Itinerary)intent.getSerializableExtra("itinerary");
    itinerary.newObserveLinks();
    display();
    TextView totalTime = (TextView) findViewById(R.id.totalTime);
    TextView totalCost = (TextView) findViewById(R.id.totalCost);
    totalTime.setText("Total Time: " + itinerary.totalTime());
    totalCost.setText("Total Cost: $" + String.format("%.2f", itinerary.totalCost()));
  }

  /**
   * Display.
   */
  private void display() {
    adapter = new AdapterFlight(this,itinerary.getFlights());
    list.setAdapter(adapter);
  }

  /**
   * Booking.
   *
   * @param view the view
   */
  public void booking(View view) {
    try {
      new AlertDialog.Builder(this).setTitle("book this itinerary?").setPositiveButton("Yes", 
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              try {
                user.bookItinerary(itinerary);
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

}
