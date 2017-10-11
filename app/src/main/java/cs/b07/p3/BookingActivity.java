package cs.b07.p3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import userinfocenter.Booking;

public class BookingActivity extends AppCompatActivity {

  private Booking book;
  private ListView list;
  private TextView date;
  private TextView change;
  private AdapterFlight adapter;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_booking);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    list = (ListView) findViewById(R.id.listView);
    book = (Booking)intent.getSerializableExtra("book");
    date = (TextView) findViewById(R.id.date);
    change = (TextView) findViewById(R.id.change);
    date.setText("Date booked: " + book.bookingDate());
    if (book.getChanged()) {
      change.setVisibility(View.VISIBLE);
    } else {
      change.setVisibility(View.INVISIBLE);
    }
    display();
  }

  private void display() {
    adapter = new AdapterFlight(this,book.getItinerary().getFlights());
    list.setAdapter(adapter);
  }
}
