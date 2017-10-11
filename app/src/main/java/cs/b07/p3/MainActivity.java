package cs.b07.p3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import driver.Server;
import userinfocenter.Admin;
import userinfocenter.Client;
import userinfocenter.User;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends AppCompatActivity  {
  
  /** The data field1. */
  private EditText dataField1;
  
  /** The data field2. */
  private EditText dataField2;
  
  /** The user. */
  private User user;
  
  /**
   * On create.
   *
   * @param savedInstanceState the saved instance state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //this.findViewById().setAlpha(100);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Server.innerDir = this.getApplicationContext().getFilesDir().getAbsolutePath() + "/";
    Server.extraDir = this.getApplicationContext().getExternalFilesDir("").getAbsolutePath() + "/";
    Server.ma = this;
    dataField1 = (EditText) findViewById(R.id.username);
    dataField2 = (EditText) findViewById(R.id.password);
    //put initiate below!!!
    Server.initiate();

  }

  /**
   * On create options menu.
   *
   * @param menu the menu
   * @return true, if successful
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  /**
   * On options item selected.
   *
   * @param item the item
   * @return true, if successful
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * Login.
   *
   * @param view the view
   */
  public void login(View view) {
    String username = dataField1.getText().toString();
    String password = dataField2.getText().toString();
    User user = Server.login(username, password);
    if (user == null) {
      Toast.makeText(this, "wrong username or password", Toast.LENGTH_LONG).show();
    } else {
      try {
        Admin admin = (Admin) user;
        Intent adminPage = new Intent(this, AdminActivity.class);
        adminPage.putExtra("admin", admin.getEmail());
        startActivity(adminPage);
      } catch (Exception e) {
        Client client = (Client) user;
        Intent clientPage = new Intent(this, ClientActivity.class);
        clientPage.putExtra("client", client.getEmail());
        startActivity(clientPage);
      }
    }
  }
}
