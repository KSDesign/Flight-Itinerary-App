package cs.b07.p3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import driver.Server;
import userinfocenter.Admin;
import userinfocenter.Client;

// TODO: Auto-generated Javadoc
/**
 * The Class AdminActivity.
 */
public class AdminActivity extends AppCompatActivity {
  
  /** The data field1. */
  private EditText dataField1;
  
  /** The data field3. */
  private EditText dataField3;
  
  /** The admin. */
  protected Admin admin;
  
  /**
   * On create.
   *
   * @param savedInstanceState the saved instance state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    admin = (Admin) Server.getUser((String)intent.getSerializableExtra("admin"));
    dataField1 = (EditText) findViewById(R.id.editText);
    dataField3 = (EditText) findViewById(R.id.editText3);
  }
  
  /**
   * Upload flights.
   *
   * @param view the view
   */
  public void uploadFlights(View view) {
    String path = dataField1.getText().toString();
    try {
      Server.uploadFlightInfo(Server.extraDir + path);
      Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
    } catch (Exception e) {
      Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
  }
  
  /**
   * Upload users.
   *
   * @param view the view
   */
  public void uploadUsers(View view) {
    String path = dataField1.getText().toString();
    try {
      Server.uploadClientInfo(Server.innerDir + "/" + path);
      Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
    } catch (Exception e) {
      Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
  }

  /**
   * Search client.
   *
   * @param view the view
   */
  public void searchClient(View view) {
    String email = dataField3.getText().toString();
    Client client = admin.getClient(email);
    if (client == null) {
      Toast.makeText(this, "client not found", Toast.LENGTH_LONG).show();
    } else {
      Intent clientPage = new Intent(this, ClientActivity.class);
      clientPage.putExtra("client", client.getEmail());
      startActivity(clientPage);
    }

  }

  /**
   * Search itineraries.
   *
   * @param view the view
   */
  public void searchItineraries(View view) {
    Intent searchPage = new Intent(this, SearchItineryToBookActivity.class);
    searchPage.putExtra("user", admin.getEmail());
    startActivity(searchPage);

  }

  /**
   * Show bookings.
   *
   * @param view the view
   */
  public void showBookings(View view) {
    Intent showBookPage = new Intent(this, ShowBookingsActivity.class);
    showBookPage.putExtra("user", admin.getEmail());
    startActivity(showBookPage);
  }

  /**
   * Show infos.
   *
   * @param view the view
   */
  public void showInfos(View view) {
    Intent showInfoPage = new Intent(this, ChangeUserInfoActivity.class);
    showInfoPage.putExtra("user", admin.getEmail());
    startActivity(showInfoPage);
  }

  /**
   * Search flights.
   *
   * @param view the view
   */
  public void searchFlights(View view) {
    Intent searchPage = new Intent(this, SearchFlightToEditActivity.class);
    startActivity(searchPage);
  }
}
