package cs.b07.p3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import driver.Server;
import driver.SortbyTotalCost;
import driver.SortbyTotalTime;
import flightinfocenter.Itinerary;

public class ShowItinerariesActivity extends AppCompatActivity {
  protected String userEmail;
  protected String date;
  protected String origin;
  protected String destination;
  protected List<Itinerary> its;
  protected ListView list;
  private Itinerary choice;
  private AdapterItinerary adapter;
  RadioGroup radioGroup;
  RadioButton timeButton, costButton;
  //RadioAdapter ra;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_itineraries);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    userEmail = (String) intent.getSerializableExtra("user");
    //user = (User) Server.getUser((String) intent.getSerializableExtra("user"));
    date = (String)intent.getSerializableExtra("date");
    origin = (String)intent.getSerializableExtra("origin");
    destination = (String)intent.getSerializableExtra("destination");
    radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
    timeButton = (RadioButton)findViewById(R.id.timeButton);
    costButton = (RadioButton)findViewById(R.id.costButton);
    list = (ListView) findViewById(R.id.listView);

    list.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
        choice = (Itinerary)its.get(position);
        goItinerayPage();
      }
    });
    try {
      its = Server.getItineraries(date, origin, destination);
    } catch (ParseException e) {
      its = new ArrayList<Itinerary>();
      Toast.makeText(this, "wrong input format", Toast.LENGTH_LONG).show();
    }
    display();

    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == timeButton.getId()) {
          Collections.sort(its, new SortbyTotalTime());
        } else {
          Collections.sort(its, new SortbyTotalCost());
        }
        display();
      }
    });
  }

  private void goItinerayPage() {
    Intent itineraryPage = new Intent(this, ItineraryActivity.class);
    itineraryPage.putExtra("itinerary", choice);
    itineraryPage.putExtra("user", userEmail);
    startActivity(itineraryPage);
  }

  private void display() {
    adapter = new AdapterItinerary(this, its);
    list.setAdapter(adapter);
  }

}
