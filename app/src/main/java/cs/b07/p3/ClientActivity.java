package cs.b07.p3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import driver.Server;
import userinfocenter.Client;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientActivity.
 */
public class ClientActivity extends AppCompatActivity {

  /** The client. */
  protected Client client;
  
  /**
   * On create.
   *
   * @param savedInstanceState the saved instance state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    client = (Client) Server.getUser((String) intent.getSerializableExtra("client"));
  }

  /**
   * Search itineraries.
   *
   * @param view the view
   */
  public void searchItineraries(View view) {
    Intent searchPage = new Intent(this, SearchItineryToBookActivity.class);
    searchPage.putExtra("user", client.getEmail());
    startActivity(searchPage);

  }

  /**
   * Show bookings.
   *
   * @param view the view
   */
  public void showBookings(View view) {
    Intent showBookPage = new Intent(this, ShowBookingsActivity.class);
    showBookPage.putExtra("user", client.getEmail());
    startActivity(showBookPage);
  }

  /**
   * Show infos.
   *
   * @param view the view
   */
  public void showInfos(View view) {
    Intent showInfoPage = new Intent(this, ChangeUserInfoActivity.class);
    showInfoPage.putExtra("user", client.getEmail());
    startActivity(showInfoPage);
  }

}
