package userinfocenter;

import driver.Server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class UserInfoCenter implements Observer {
  
  private Map<String, User> users = new HashMap<String, User>();
  
  private static String filesDir = "passwords.txt";
  
  /**
   * build user info center.
   */
  @SuppressWarnings("unchecked")
  public UserInfoCenter() {
    ObjectInputStream input = null;
    try {
      //File file = new File(path);
      input = new ObjectInputStream(new FileInputStream(Server.innerDir + filesDir));
      users = (HashMap<String, User>)input.readObject();

    } catch (Exception e) {
      users = new HashMap<String,User>();
    }

    if (!users.containsKey("admin@gmail.com")) {
      User admin = new Admin("Admin", "0464", "admin@gmail.com", "utsc", "0000", "2099-01-01");
      admin.setPassword("password");
      users.put("admin@gmail.com", admin);
    }
    
    newObserveLinks();
  }
  /**
   * Read csv from giving file.
   *
   * @param path the path of file
   * @throws IOException IOException 
   */
  public void readCsv(String path) throws IOException {
    Scanner scanner = new Scanner(new FileInputStream(path));
    String [] thisLine;
    
    while (scanner.hasNextLine()) {
      thisLine = scanner.nextLine().split(",",6);
      if (thisLine.length < 6) {
        continue;
      }
      
      if (users.containsKey(thisLine[2])) {
        users.get(thisLine[2]).editInfomations(thisLine[0], thisLine[1], thisLine[3],
            thisLine[4], thisLine[5]);
      } else {
        User user = new Client(thisLine[0], thisLine[1], thisLine[2], thisLine[3], 
            thisLine[4], thisLine[5]);
        user.addObserver(this);
        users.put(thisLine[2], user);
      }
    }
    scanner.close();
    saveData();
  }

  /**
   * save the data if find a change.
   */
  public void update(Observable user, Object email) {
    try {
      saveData();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void newObserveLinks() {
    for (User user:users.values()) {
      user.addObserver(this);
      user.newObserveLinks();
    }
  }
  
  /**
   * Returns the user with the given email. 
   * @param email the email address of a client
   * @return the user
   */
  public User searchUser(String email) {
    return users.get(email);
  }
  
  /**
   * login by email and password.
   * @param emial email
   * @param password password
   * @return user admin or client
   */
  public User login(String emial, String password) {
    if (users.containsKey(emial)) {
      User user = users.get(emial);
      if (user.getPassword() == null) {
        user.setPassword(password);
      } else if (!user.getPassword().equals(password)) {
        return null;
      }
      return user;
    }
    return null;
  }
  
  /**
   * save data.
   * @throws IOException IOException
   */
  public void saveData() throws IOException {
    ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream(Server.innerDir + filesDir));  
    out.writeObject(users);  
    out.close();
  }

}
