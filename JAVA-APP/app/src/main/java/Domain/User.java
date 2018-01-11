package Domain;

/**
 * Created by Bogdan on 09-Jan-18.
 */

public class User {
    private String Username;
    private String Password;
    private String Email;

    public User(String username, String password, String email) {
        Username = username;
        Password = password;
        Email = email;
    }

    public User() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
