package cs.b07.p3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import driver.Server;
import userinfocenter.User;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangeUserInfoActivity.
 */
public class ChangeUserInfoActivity extends AppCompatActivity {
  
  /** The user. */
  private User user;
  
  /** The list. */
  private ListView list;
  
  /** The adapter. */
  private ListViewAdapter adapter;
  
  /**
   * On create.
   *
   * @param savedInstanceState the saved instance state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_change_info);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    Intent intent = getIntent();
    user = Server.getUser((String) intent.getSerializableExtra("user"));
    list = (ListView) findViewById(R.id.listView);
    display();
  }

  /**
   * Display.
   */
  private void display() {
    LinkedHashMap<String, String> infoMap = user.getInfoMap();
    adapter = new ListViewAdapter(infoMap);
    list.setAdapter(adapter);
  }

  /**
   * Save change.
   *
   * @param view the view
   */
  public void saveChange(View view) {
    try {
      String [] info = new String[adapter.getCount()];
      for (int i = 0; i < info.length; i++) {
        info[i] = ((EditText)adapter.getItem(i).findViewById(R.id.value)).getText().toString();
      }
      user.editInfomations(info[1], info[2], info[3], info[4], info[5]);
      user.setPassword(info[6]);
      user.changed();
      Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
    } catch (Exception e) {
      Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
  }

  /**
   * The Class ListViewAdapter.
   */
  public class ListViewAdapter extends BaseAdapter {
    
    /** The item views. */
    View[] itemViews;

    /**
     * Instantiates a new list view adapter.
     *
     * @param infoMap the info map
     */
    public ListViewAdapter(LinkedHashMap<String, String> infoMap) {
      itemViews = new View[infoMap.size()];
      int count = 0;
      Iterator<Entry<String, String>> iterator = infoMap.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
        itemViews[count] = makeItemView((String) entry.getKey(), (String) entry.getValue());
        count++;
      }
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {
      return itemViews.length;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    public View getItem(int position) {
      return itemViews[position];
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int position) {
      return position;
    }

    /**
     * Make item view.
     *
     * @param name the name
     * @param value the value
     * @return the view
     */
    private View makeItemView(String name, String value) {
      LayoutInflater inflater = (LayoutInflater) ChangeUserInfoActivity.this
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View itemView = inflater.inflate(R.layout.item_personal_info, null);
      TextView title = (TextView) itemView.findViewById(R.id.name);
      title.setText(name);
      EditText info = (EditText) itemView.findViewById(R.id.value);
      info.setText(value);

      if (name.equals("email")) {
        info.setKeyListener(null);
      }

      return itemView;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
      return itemViews[position];
    }
  }

}
