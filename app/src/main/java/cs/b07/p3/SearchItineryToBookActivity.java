package cs.b07.p3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchItineryToBookActivity.
 */
public class SearchItineryToBookActivity extends AppCompatActivity {
  
  /** The user email. */
  protected String userEmail;
  
  /** The data field1. */
  private TextView dataField1;
  
  /** The data field2. */
  private EditText dataField2;
  
  /** The data field3. */
  private EditText dataField3;
  
  /** The day. */
  private int year, month, day;
  
  /**
   * On create.
   *
   * @param savedInstanceState the saved instance state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_searchit_ineraries_to_book);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    userEmail = (String) intent.getSerializableExtra("user");
    dataField1 = (TextView) findViewById(R.id.date);
    dataField2 = (EditText) findViewById(R.id.place);
    dataField3 = (EditText) findViewById(R.id.destination);
    Calendar ca = Calendar.getInstance();
    year = ca.get(Calendar.YEAR);
    month = ca.get(Calendar.MONTH);
    day = ca.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Search itineraries.
   *
   * @param view the view
   */
  public void searchItineraries(View view) {
    String date = dataField1.getText().toString();
    String origin = dataField2.getText().toString();
    String destination = dataField3.getText().toString();
    Intent showItsPage = new Intent(this, ShowItinerariesActivity.class);
    showItsPage.putExtra("date", date);
    showItsPage.putExtra("origin", origin);
    showItsPage.putExtra("destination", destination);
    showItsPage.putExtra("user", userEmail);
    startActivity(showItsPage);

  }

  /**
   * Search flights.
   *
   * @param view the view
   */
  public void searchFlights(View view) {
    String date = dataField1.getText().toString();
    String origin = dataField2.getText().toString();
    String destination = dataField3.getText().toString();
    Intent showFlightsPage = new Intent(this, ShowFlightsActivity.class);
    showFlightsPage.putExtra("date", date);
    showFlightsPage.putExtra("origin", origin);
    showFlightsPage.putExtra("destination", destination);
    showFlightsPage.putExtra("user", userEmail);
    startActivity(showFlightsPage);
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
