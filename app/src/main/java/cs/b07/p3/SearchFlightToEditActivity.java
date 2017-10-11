package cs.b07.p3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import driver.Server;
import flightinfocenter.Flight;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchFlightToEditActivity.
 */
public class SearchFlightToEditActivity extends AppCompatActivity {

  /** The data field1. */
  private TextView dataField1;
  
  /** The data field2. */
  private EditText dataField2;
  
  /** The day. */
  private int year , month , day;
  
  /** The flights. */
  protected List<Flight> flights = new ArrayList<Flight>();
  
  /** The list. */
  protected ListView list;
  
  /** The choice. */
  private Flight choice;
  
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
    setContentView(R.layout.activity_search_flight_to_edit);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    dataField1 = (TextView) findViewById(R.id.date);
    dataField2 = (EditText) findViewById(R.id.place);
    Calendar ca = Calendar.getInstance();
    year = ca.get(Calendar.YEAR);
    month = ca.get(Calendar.MONTH);
    day = ca.get(Calendar.DAY_OF_MONTH);
    list = (ListView) findViewById(R.id.listView);
    list.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
        choice = flights.get(position);
        edit();
      }
    });
  }

  /**
   * Search flights.
   *
   * @param view the view
   */
  public void searchFlights(View view) {
    String date = dataField1.getText().toString();
    String origin = dataField2.getText().toString();
    try {
      flights = Server.getFlights(date, origin, "", true);
    } catch (ParseException e) {
      flights = new ArrayList<Flight>();
      Toast.makeText(this, "wrong format", Toast.LENGTH_LONG).show();
    }
    display();
  }

  /**
   * Display.
   */
  private void display() {
    adapter = new AdapterFlight(this,flights);
    list.setAdapter(adapter);
  }

  /**
   * Edits the.
   */
  private void edit() {
    Intent editPage = new Intent(this, ChangeFlightInfoActivity.class);
    editPage.putExtra("flight", choice);
    startActivity(editPage);
  }

  /**
   * Date picker.
   *
   * @param view the view
   */
  public void datePicker(View view) {
    new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear += 1;
        String strYear = Integer.toString(year);
        String strMonth = Integer.toString(monthOfYear);
        String strDay = Integer.toString(dayOfMonth);
        if (monthOfYear < 10) {
          strMonth = "0" + strMonth;
        }
        if (dayOfMonth < 10) {
          strDay = "0" + strDay;
        }
        dataField1.setText(strYear + "-" + strMonth + "-" + strDay);
      }
    },year,month,day).show();
  }

}
