/**
 * The User class presents a user object
 */

public class User {
    private String userID;
    private String password;
    private String userType;
    private String userName;

    public User(){
        this.userID = "";
        this.password = "";
        this.userType = "";
        this.userName = "";
    }

    /**
     * Constructor to create a User with specified userID, password, uerType and userName.
     * @param userID    the userID of a user
     * @param password  the password of a user
     * @param uerType   the uerType of a user
     * @param userName  the userName of a user
     */

    public User(String userID, String password,String uerType, String userName ){
        this.userID = userID;
        this.password = password;
        this.userType = uerType;
        this.userName = userName;
    }

    //to string function
    public String toString(){
        return this.userID + " " + this.password + " " + this.userType + " " + this.userName;
    }

    //get userID
    public String getUserID(){
        return this.userID;
    }

    //get password
    public String getPassword(){
        return this.password;
    }

    //get user type string
    public String getUserType(){
        return this.userType;
    }

    //get user name
    public String getUserName(){
        return this.userName;
    }

    //set user ID
    public void setUserID(String userID){
        this.userID = userID;
    }

    //if two user equal with input user
    public boolean equalUserID(User compareUser){
        if(this.userID.equals(compareUser.getUserID())){
            return true;
        }else {
            return false;
        }
    }

}
