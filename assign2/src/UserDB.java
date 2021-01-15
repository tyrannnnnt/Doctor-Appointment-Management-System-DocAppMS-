/**
 * The UserDB class presents a user array.
 */

public class UserDB {
    private User[] allUsers;

    public UserDB(){
        this.allUsers = new User[0];
    }

    public UserDB(User newUser){
        this.allUsers = new User[1];
        this.allUsers[0] = newUser;
    }

    /**
     * Constructor to create a UserDB with specified user array
     * @param newUserArray  the user array of a UserDB
     */

    public UserDB(User[] newUserArray){
        this.allUsers = newUserArray;
    }

    // get all users array
    public User[] getAllUsers(){
        return this.allUsers;
    }

    //add new user into user database
    public void addUser(User newUser){
        User[] newUserArray = new User[this.allUsers.length+1];
        for(int i = 0; i < this.allUsers.length; i++){
            newUserArray[i] = this.allUsers[i];
        }
        newUserArray[this.allUsers.length] = newUser;
        this.allUsers = newUserArray;
    }

    //add user array into user database
    public void addUserArray(User[] UserArray, String loadFN){
        User[] newUserArray = new User[this.allUsers.length+UserArray.length];
        int pos = this.allUsers.length;
        int repeatCount = 0;
        int addNum = this.allUsers.length;

        //get old user array
        for(int i = 0; i < this.allUsers.length; i++){
            newUserArray[i] = this.allUsers[i];
        }

        //add new array
        for(int i = 0; i < UserArray.length; i++){
            boolean repeated = false;
            int repeatPos = 0;
            for(int j = 0; j < newUserArray.length; j++) {
                if(newUserArray[j] != null) {
                    if (UserArray[i].equalUserID(newUserArray[j])) {
                        repeated = true;
                        repeatPos = i+1;
                        break;
                    }
                }
            }

            //if array repeated
            if(repeated){
                System.out.println("UserDB.loadDB: error adding record on line" + repeatPos
                        + " of " + loadFN + " -- Duplicated user record (" +
                        UserArray[repeatPos-1].getUserID() +")");
                repeatCount++;
                continue;
            }else {
                newUserArray[pos] = UserArray[i];
                pos++;
            }
        }
       if(repeatCount != 0){
           User[] returnUserArray = new User[this.allUsers.length+UserArray.length-repeatCount];
           for(int i = 0; i < returnUserArray.length; i++){
               returnUserArray[i] = newUserArray[i];
           }
           this.allUsers = returnUserArray;
       }else {
           this.allUsers = newUserArray;
       }
       addNum = UserArray.length - repeatCount;
       System.out.println(addNum + " user records loaded.");
    }

    //delete user with input userID string
    public void deleteUser(String userID){
        User[] newUserArray = new User[this.allUsers.length-1];
        int index = 0;
        for(int i = 0; i < this.allUsers.length; i++){
            if(this.allUsers[i].getUserID().equals(userID)){
                continue;
            }
            newUserArray[index] = this.allUsers[i];
            index++;
        }
        this.allUsers = newUserArray;
    }

    //to string function
    public String toString(){
        String str = "";
        for(int i = 0; i < this.allUsers.length; i++){
            str += allUsers[i] + "\n";
        }
        return str;
    }

    //print user database with format
    public void printFormat(){
        for(int i = 0; i < this.allUsers.length; i++){
            String printTp = "";
            if(this.allUsers[i].getUserType().charAt(0) == 'a'){
                printTp = "Admin";
            }else if (this.allUsers[i].getUserType().charAt(0) == 'p'){
                printTp = "Patient";
            }else if(this.allUsers[i].getUserType().charAt(0) == 'd'){
                printTp = "Doctor";
            }
            System.out.printf("%-10s%-12s%-12s%s\n", this.allUsers[i].getUserID(),this.allUsers[i].getPassword(),
                    printTp, this.allUsers[i].getUserName());
        }
    }

    //get user with input userID
    public User getUser(String userID){
        for(int i = 0; i < this.getAllUsers().length; i++){
            if(this.getAllUsers()[i].getUserID().equals(userID)){
                return this.getAllUsers()[i];
            }
        }
        return null;
    }

    //get user array
    public User[] getUserArray(){
        return this.allUsers;
    }

    //if has repeat id in user database
    public boolean hasRepeatId(String userId){
        for(int i = 0; i < this.allUsers.length; i++){
            if(this.allUsers[i].getUserID().equals(userId)){
                return true;
            }
        }
        return false;
    }

    //if such doctor exist
    public boolean hasSuchDoc(String userId){
        for(int i = 0; i < this.allUsers.length; i++){
            if(this.allUsers[i].getUserType().equals("d")){
                if(this.allUsers[i].getUserID().equals(userId)){
                    return true;
                }
            }
        }
        return false;
    }

    //if such patient exist
    public boolean hasSuchPat(String userId){
        for(int i = 0; i < this.allUsers.length; i++){
            if(this.allUsers[i].getUserType().equals("p")){
                if(this.allUsers[i].getUserID().equals(userId)){
                    return true;
                }
            }
        }
        return false;
    }

    //if such admin exist
    public boolean hasSuchAdmin(String userId){
        for(int i = 0; i < this.allUsers.length; i++){
            if(this.allUsers[i].getUserType().equals("a")){
                if(this.allUsers[i].getUserID().equals(userId)){
                    return true;
                }
            }
        }
        return false;
    }

    //if such user exist
    public boolean hasSuchUser(String user){
        for(int i = 0; i < this.allUsers.length; i++){
            if(this.allUsers[i].getUserID().equals(user)){
                return true;
            }
        }
        return false;
    }
}
