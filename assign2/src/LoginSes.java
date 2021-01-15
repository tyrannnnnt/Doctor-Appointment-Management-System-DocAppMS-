/**
 * The LoginSes class is to do the operation that user input.
 * for the user type is admin.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoginSes {
    private UserDB userDataBase;
    private AppointmentDB appointDataBase;

    public LoginSes(){
        this.userDataBase = new UserDB();
        this.appointDataBase = new AppointmentDB();
    }

    /**
     * Constructor to create a LoginSes with specified userDataBase and appointDataBase
     * @param userDataBase      the user data base that system have
     * @param appointDataBase   the appointment data base that system have
     */

    public LoginSes(UserDB userDataBase, AppointmentDB appointDataBase){
        this.userDataBase = userDataBase;
        this.appointDataBase = appointDataBase;
    }

    //return appointment database
    public AppointmentDB getAppointDataBase() {
        return appointDataBase;
    }

    //return userDB
    public UserDB getUserDataBase() {
        return userDataBase;
    }

    //input UserDB to set user database
    public void setUserDataBase(UserDB userDataBase) {
        this.userDataBase = userDataBase;
    }


    //input userDB and load filename to add more user database
    public void addUserDataBase(UserDB newUserDataBase, String loadFN){
        User[] newUserArray = newUserDataBase.getUserArray();
        this.userDataBase.addUserArray(newUserArray, loadFN);
    }

    //input appointmentDB and load filename to add more appointment database
    public void addAppointDataBase(AppointmentDB newUserDataBase, String loadFN){
        Appointment[] newAppointArray = newUserDataBase.getAppointArray();
        this.appointDataBase.addAppointArray(newAppointArray, loadFN);
    }

    //input AppointmentDB to set Appointment data base
    public void setAppointDataBase(AppointmentDB appointDataBase){
        this.appointDataBase = appointDataBase;
    }

    //print available commands for login stage
    public void availableCommand(){
        System.out.println("Available commands:\n Login User\n Quit\n");
    }

    //get the string which is input by user
    public String getInputStr()throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.print("ready>");
        String input = in.nextLine();
        return input;
    }

    //input userID to check whether such user is in user database
    public boolean hasUser(String inputUserStr){
        User tempUser = searchUser(inputUserStr);
        if(tempUser != null){
            return true;
        }else {
            return false;
        }
    }

    //input userID and return the User class, if not exit, return null
    private User searchUser(String userID){
        for(int i = 0; i < this.userDataBase.getAllUsers().length; i++){
            if(this.userDataBase.getAllUsers()[i].getUserID().equals(userID)){
                return this.userDataBase.getAllUsers()[i];
            }
        }
        return null;
    }

    //ask to input the password
    private String getPassword()throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.print("Password: ");
        while(!in.hasNext()) {
            System.out.print("Password: ");
        }
        String input = in.nextLine();
        return input;
    }

    //check whether the password is correct
    private boolean passwordCorrect(String userID, String password){
        if(searchUser(userID).getPassword().equals(password)){
            return true;
        }else {
            return false;
        }
    }

    //do the login session
    public boolean loginSys(LoginSes login, User[] user, boolean[] hasQuit)throws Exception{
        drawSquare(45,"Doctor Appointment Management System");
        String inputStr = "";

        //print available commands
        login.availableCommand();
        //get input string
        inputStr = login.getInputStr();
        String command = strBeforeSpace(inputStr);
        String userID = getRestStr(inputStr, command);
        userID = userID.toLowerCase();

        //Login + command
        if(command.equals("Login")) {
            //get correct password
            String passWord = login.getPassword();
            //correct userid and password
            if (login.hasUser(userID) && login.passwordCorrect(userID, passWord)) {
                System.out.println("Login success!  Welcome to Doctor Appointment Management System.");
                user[0].setUserID(userID);
                return true;
            //have no such userID
            } else if (!login.hasUser(userID)) {
                System.out.println("Invalid UserID or Password.");
                return false;
            // input wrong password
            }else if(!login.passwordCorrect(userID, passWord)){
                System.out.println("Invalid UserID or Password.");
                return false;
            }
        //Quit
        } else if (command.equals("Quit")){
            System.out.println("Goodbye!");
            hasQuit[0] = true;
            return false;
        //Unknown command
        }else {
            System.out.println("Unknown command: " + command);
            return false;
        }
        return false;
    }

    //System beginning print
    public void programBegin(){
        System.out.println("*** System Startup: done! ***");
    }

    //draw square with specify input string
    public void drawSquare(int length, String strInput){
        for (int i=0;i<5;i++) {
            for (int j=0;j<length;j++) {
                //cross
                if ( i == 0 && j == 0 || i == 4 && j == 0
                        || i == 0 && j == length-1 || i == 4 && j == length-1) {
                    System.out.print("+");
                }
                //"-"
                else if (i == 4 || i == 0) {
                    System.out.print("-");
                }
                //lines "|"
                else if(j == 0 || j == length-1){
                    System.out.print("|");
                }
                //string line
                else if (i == 2) {
                    //has input
                    if(strInput != "") {
                        if(j != (length-strInput.length()+1)/2){
                            System.out.print(" ");
                        }else {
                            System.out.print(strInput);
                            j += strInput.length()-1;
                        }
                    //have no input
                    }else{
                        System.out.print(" ");
                    }
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

    }

    //total lines of the file
    public int totalFileLineNum(String fileName)throws Exception{
        File inputFile = new File(fileName);
        try {
            Scanner input = new Scanner(inputFile);
            int count = 0;
            while (input.hasNextLine()) {
                count++;
                input.nextLine();
            }
            input.close();
            return count;
        }catch (FileNotFoundException e){
            System.out.println("Input file does not exist!");
            e.printStackTrace();
            System.exit(0);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
            return 0;
        }
    }

    //read a file and return string array of every lines
    public String[] readFile(String fileName)throws Exception{
        int totalLines = totalFileLineNum(fileName);
        String[] linesArray = new String[totalLines];
        File inputFile = new File(fileName);

        try {
            //scan input file
            Scanner input = new Scanner(inputFile);
            boolean hasNullLine = false;
            int nullCount = 0;
            int index = 0;

            for (int i = 0; i < totalLines; i++) {
                String str = input.nextLine();
                if (str.length() >= 1) {
                    linesArray[index] = str;
                    index++;
                } else {
                    hasNullLine = true;
                    nullCount++;
                    continue;
                }
            }
            input.close();

            //get string array without null lines
            String[] repeatLines = new String[totalLines - nullCount];
            for (int i = 0; i < repeatLines.length; i++) {
                repeatLines[i] = linesArray[i];
            }

            return repeatLines;
        }catch(FileNotFoundException e){
            System.out.println("Input file does not exist!");
            e.printStackTrace();
            System.exit(0);
            return null;
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    //delete space before the string
    public String strBeforeSpace(String inputStr){
        String returnStr ="";

        if(inputStr.length() == 0){
            return null;
        }
        //if character null, continue
        int index = 0;
        for(int i = 0; i < inputStr.length(); i++) {
            if (inputStr.charAt(i) != ' ') {
                index = i;
                break;
            }
        }
        //get string before space
        for(int i = index; i < inputStr.length(); i++) {
            if (inputStr.charAt(i) != ' '){
                returnStr += inputStr.charAt(i);
            }else{
                break;
            }
        }
        return returnStr;
    }

    // get string from the specified index
    private String getStrFromSpecifiedIndex(int index, String inputStr){
        String str = "";
        for(int j = index; j < inputStr.length(); j++) {
            str += inputStr.charAt(j);
        }
        return str;
    }

    //get the position of the target string
    private int posOfEndStr(String wholeStr, String targetStr){
        int count = 0;
        int index = 0;
        int spacePos = 1;

        for(int i = 0; i < wholeStr.length(); i++){
            if(index < targetStr.length()) {
                //whether first character same
                if (targetStr.charAt(index) != wholeStr.charAt(i)) {
                    continue;
                }

                //if not single character
                if(targetStr.length() != 1) {
                    if (targetStr.charAt(index) == wholeStr.charAt(i)) {
                        String str = "";
                        int pos = i;
                        for(int j = 0; j < targetStr.length(); j++){
                            str += wholeStr.charAt(pos);
                            pos++;
                        }
                        //if two strings are equal
                        if(targetStr.equals(str)){
                            if(i >= 1 ) {
                                if (wholeStr.charAt(i - 1) == ' ') {
                                    i = pos;
                                    count = i;
                                    break;
                                }
                            }else {
                                i = pos;
                                count = i;
                                break;
                            }
                        }else {
                            continue;
                        }
                    }
                //single char with space with front and backward
                }else if(i >= 1){
                    if(targetStr.charAt(index) == wholeStr.charAt(i) && wholeStr.charAt(i-1) == ' ' && wholeStr.charAt(i+1) == ' '){
                        count = i+1;
                        index++;
                    }
                }
            }else {
                break;
            }
        }
        return count;
    }

    //get string delete front space
    private String deleteSpaceBeforeSpecStr(String specStr){
        String returnStr ="";

        //if space
        int index = 0;
        for(int i = 0; i < specStr.length(); i++) {
            if (specStr.charAt(i) != ' ') {
                index = i;
                break;
            }
        }

        for(int i = index; i < specStr.length(); i++) {
            returnStr += specStr.charAt(i);
        }
        return returnStr;
    }

    //input user string and return UserDB
    public UserDB createUserDB(String[] InputString, String filename){
        String[] userInputString = deleteRepeatIdStr(InputString, filename);
        //create user array with string array
        User[] userArray = new User[userInputString.length];
        int deleteNum = InputString.length-userInputString.length;
        int count = 0;
        int index = 0;
        for(int i = 0; i < userInputString.length; i++) {
            //if is right argument number
            if(totalArgumentNum(userInputString[i]) >= 4) {
                String strOfID = strBeforeSpace(userInputString[i]);
                String strOfPassword = getMiddleStr(userInputString[i], strOfID);
                String strOfUserType = getMiddleStr(userInputString[i], strOfPassword);
                String strOfUserName = getRestStr(userInputString[i], strOfUserType);
                strOfID = strOfID.toLowerCase();
                //if user type is right
                if(isCorrectType(strOfUserType)) {
                    User tempUser = new User(strOfID, strOfPassword, strOfUserType, strOfUserName);
                    userArray[index] = tempUser;
                    index++;
                //wrong user type
                }else {
                    System.out.println("UserDB.loadDB: error adding record on line "
                            + (i+1+deleteNum) +" of "+ filename +"-- Invalid user type");
                    count++;
                    continue;
                }
            //wrong argument number
            }else {
                System.out.println("UserDB.loadDB: error adding record on line "
                        + (i+1+deleteNum) +" of "+ filename +"-- Invalid user type");
                count++;
                continue;
            }
        }

        User[] newUserArray = new User[userInputString.length-count];
        for(int i = 0; i < newUserArray.length; i++){
            newUserArray[i]  = userArray[i];
        }
        UserDB allUserDB = new UserDB(newUserArray);

        return allUserDB;
    }

    //delete the repeated string with the same user id
    private String[] deleteRepeatIdStr(String[] inputStrArray, String inputFN){
        String[] ArrayStr = new String[inputStrArray.length];
        ArrayStr[0] = inputStrArray[0];
        int count = 0;
        int index = 1;
        boolean repeat = false;
        for(int i = 1; i < inputStrArray.length; i++){
            for(int j = 0; j < i; j++){
                String id = strBeforeSpace(inputStrArray[i]);
                if(id.equals(strBeforeSpace(inputStrArray[j]))){
                    count++;
                    repeat = true;
                    System.out.println("UserDB.loadDB: error adding record on line "+ (i+1) +
                            " of " + inputFN +"-- Duplicated user record ("+ id +")");
                    break;
                }
            }
            if(!repeat) {
                ArrayStr[index] = inputStrArray[i];
                index++;
            }else {
                repeat = false;
                continue;
            }
        }
        String[] newArrayStr = new String[inputStrArray.length-count];
        for(int i = 0; i < newArrayStr.length; i++){
            newArrayStr[i] = ArrayStr[i];
        }
        return newArrayStr;
    }

    //if the input str is the correct user type
    private boolean isCorrectType(String inputStr){
        if(inputStr.charAt(0) == 'a' || inputStr.charAt(0) == 'd' || inputStr.charAt(0) == 'p'){
            return true;
        }else {
            return false;
        }
    }

    //get string after the front string before space
    public String getMiddleStr(String wholeStr, String frontStr){
        return strBeforeSpace(getStrFromSpecifiedIndex(posOfEndStr(wholeStr, frontStr), wholeStr));
    }

    //get string after the front string
    public String getRestStr(String wholeStr, String frontStr){
        return deleteSpaceBeforeSpecStr(getStrFromSpecifiedIndex(posOfEndStr(wholeStr, frontStr), wholeStr));
    }

    //create appointmentDB with input string array
    public AppointmentDB createAppointmentDB(String[] AppointmentInputStr, String appointFN){
        System.out.print("Loading appointment db from " + appointFN + "...\n");
        Appointment[] appointArray = new Appointment[AppointmentInputStr.length];
        int count = 0;
        int index = 0;
        for(int i = 0; i < AppointmentInputStr.length; i++){
            //if argument number is 3
            if(totalArgumentNum(AppointmentInputStr[i]) == 3) {
                String strOfDocID = strBeforeSpace(AppointmentInputStr[i]);
                String strOfPatientID = getMiddleStr(AppointmentInputStr[i], strOfDocID);
                String strOfTime = getRestStr(AppointmentInputStr[i], strOfPatientID);
                //if have such doctor
                if(this.getUserDataBase().hasSuchDoc(strOfDocID)) {
                    //if have such patient
                    if(this.getUserDataBase().hasSuchPat(strOfPatientID)) {
                        //if it is correct time format
                        if (isCorrectTimeFormat(strOfTime)) {
                            TimeSlot time = createTimeSlot(strOfTime);
                            Appointment newAppoint = new Appointment(strOfDocID, strOfPatientID, time);
                            if(i != 0) {
                                boolean isDocTimeClash = false;
                                boolean isPatTimeClash = false;
                                for(int j = 0; j < i; j++){
                                    if(appointArray[j] != null) {
                                        if (appointArray[j].docTimeClash(newAppoint)) {
                                            isDocTimeClash = true;
                                            break;
                                        }
                                        if (appointArray[j].patTimeClash(newAppoint)) {
                                            isPatTimeClash = true;
                                            break;
                                        }
                                    }
                                }
                                //if no time clash
                                if(!isDocTimeClash && !isPatTimeClash){
                                    appointArray[index] = newAppoint;
                                    index++;
                                //if doctor time clash
                                }else if(isDocTimeClash){
                                    System.out.println("AppointmentDB.loadDB: error adding record on line " +
                                            (i + 1) + " of " + appointFN + " -- Doctor appointment time clash ("
                                            + strOfDocID + strOfTime + ")");
                                    count++;
                                    continue;
                                //if patient time clash
                                }else if(isPatTimeClash){
                                    System.out.println("AppointmentDB.loadDB: error adding record on line " +
                                            (i + 1) + " of " + appointFN + " -- Patient appointment time clash ("
                                            + strOfPatientID + strOfTime + ")");
                                    count++;
                                    continue;
                                }
                            }else {
                                appointArray[0] = newAppoint;
                                index++;
                            }
                        //wrong time format
                        } else {
                            //wrong time length
                            if(!isRightLengthOfTimeS(strOfTime)){
                                System.out.println("AppointmentDB.loadDB: error adding record on line " +
                                        (i + 1) + " of " + appointFN + "--  Corrupted time slot (" + strOfTime + ")");
                            //wrong day format
                            } else if (!isRightDay(strOfTime)) {
                                System.out.println("AppointmentDB.loadDB: error adding record on line " +
                                        (i + 1) + " of " + appointFN + "-- Invalid day for time slot (" + noCorrectDay(strOfTime) + ")");
                            //wrong hour format
                            } else if (!isRightHour(strOfTime)) {
                                System.out.println("AppointmentDB.loadDB: error adding record on line " +
                                        (i + 1) + " of " + appointFN + "-- Invalid time for time slot (" + noCorrectHour(strOfTime) + ")");
                            }
                            count++;
                            continue;
                        }
                    //no such patient
                    }else {
                        System.out.println("AppointmentDB.loadDB: error adding record on line "+ (1+i) +
                                " of " + appointFN +" -- No such patient (" + strOfPatientID + ")");
                        count++;
                        continue;
                    }
                //no such doctor
                }else {
                    System.out.println("AppointmentDB.loadDB: error adding record on line "+ (1+i) +
                            " of " + appointFN +" -- No such doctor (" + strOfDocID + ")");
                    count++;
                    continue;
                }
            //wrong argument number
            }else {
                System.out.println("AppointmentDB.loadDB: error adding record on line " +
                        (i+1) + " of "+ appointFN + "-- Corrupted appointment record");
                count++;
                continue;
            }
        }

        Appointment[] newAppointArray = new Appointment[AppointmentInputStr.length-count];
        for(int i = 0; i < newAppointArray.length; i++){
            newAppointArray[i] = appointArray[i];
        }

        return new AppointmentDB(newAppointArray);
    }

    //input string is not a correct day format and return the wrong day
    private char noCorrectDay(String inputStr){
        return inputStr.charAt(0);
    }

    //input string is not a correct hour format and return the integer hour
    private int noCorrectHour(String inputStr){
        int num = 0;
        for(int i = 1; i < inputStr.length(); i++){
            num = num*10 + (inputStr.charAt(i) - '0');
        }
        return num;
    }

    //if it is right length of time slot
    private boolean isRightLengthOfTimeS(String timeStr){
        if(timeStr.length() == 3){
            return true;
        }else {
            return false;
        }
    }

    //create timeslot with string
    public TimeSlot createTimeSlot(String timeslotStr){
        TimeSlot time = new TimeSlot();
        time.setDay(timeslotStr.charAt(0));
        int hour = (timeslotStr.charAt(1) - '0')*10 + (timeslotStr.charAt(2) - '0');
        time.setHour(hour+"");
        return time;
    }

    //input user to draw the specify timetable
    public void drawTimetable(User user){
        System.out.println("Time Table for "+ user.getUserName()+" (" + user.getUserID() + "): ");
        int appointNum = 0;
        printFiveDay();//print 5 days

        for(int i = 0; i < 41; i++){
            int hourNum = 9;
            if(i >2){
                hourNum = (i/3)+8;
            }

            for(int j = 0; j < 66; j++){
                boolean cross = false; //if cross
                if(j == 3 || j == 15
                        || j == 27 || j == 39 || j == 51
                        || j == 63){
                    cross = true;
                }

                //every first line
                if(i % 4 == 0){
                    if(j <= 2 || j >= 64) {
                        System.out.print(" ");
                    }else if(cross){
                        System.out.print("+");
                    }else {
                        System.out.print("-");
                    }

                //every second and forth line
                }else if(i % 4 == 1 || i % 4 ==3){
                    if(j <= 2 || j >= 64) {
                        System.out.print(" ");
                    }else if(cross){
                        System.out.print("|");
                    }else {
                        System.out.print(" ");
                    }

                //every third line
                }else if(i % 4 == 2){
                    if(cross){
                        System.out.print("|");
                    }else if(j == 0 || j == 65){
                        System.out.printf("%2s",hourNum);
                        j++;
                    }else {
                        //if has appointment
                        if (this.getAppointDataBase().hasAppointInDB(user)) {
                            //get the user's appointment
                            Appointment[] allAppoint = this.getAppointDataBase().getAppoint(user.getUserID());
                            appointNum = allAppoint.length;

                            //if is doctor
                            boolean isDoc = false;
                            if(user.getUserType().equals("d")){
                                isDoc = true;
                            }

                            String inputStr = "";
                            boolean hasAppointInLine = false;
                            for(int n = 0; n < allAppoint.length; n++){
                                //get doc or patient id
                                if(isDoc){
                                    inputStr = allAppoint[n].getPatientID();
                                }else {
                                    inputStr = allAppoint[n].getDoctorID();
                                }
                                //if name too long
                                if(inputStr.length() >= 10){
                                    for(int m = 0; m < 11; m++){
                                        inputStr += inputStr.charAt(m);
                                    }
                                }
                                //get corresponding timeslot
                                if(allAppoint[n].getSpeTimeSlot().getHour().equals(hourNum+"")){
                                    hasAppointInLine = true;
                                    //if Monday
                                    if(allAppoint[n].getSpeTimeSlot().getDay() == 'M') {
                                        if (j != ((12 - inputStr.length() + 1) / 2) + 3) {
                                            System.out.print(" ");
                                        } else {
                                            System.out.print(inputStr);
                                            j += inputStr.length() - 1;
                                        }
                                    //if Tuesday
                                    }else if(allAppoint[n].getSpeTimeSlot().getDay() == 'T'){
                                        if (j != ((12 - inputStr.length() + 1) / 2) + 3 + 12) {
                                            System.out.print(" ");
                                        } else {
                                            System.out.print(inputStr);
                                            j += inputStr.length() - 1;
                                        }
                                    //if Wednesday
                                    }else if(allAppoint[n].getSpeTimeSlot().getDay() == 'W'){
                                        if (j != ((12 - inputStr.length() + 1) / 2) + 3 + 12 + 12) {
                                            System.out.print(" ");
                                        } else {
                                            System.out.print(inputStr);
                                            j += inputStr.length() - 1;
                                        }
                                    //if Thursday
                                    }else if(allAppoint[n].getSpeTimeSlot().getDay() == 'R'){
                                        if (j != ((12 - inputStr.length() + 1) / 2) + 3 + 12 +12 + 12) {
                                            System.out.print(" ");
                                        } else {
                                            System.out.print(inputStr);
                                            j += inputStr.length() - 1;
                                        }
                                    //if Friday
                                    }else if(allAppoint[n].getSpeTimeSlot().getDay() == 'F'){
                                        if (j != ((12 - inputStr.length() + 1) / 2) + 3 + 12 +12 + 12 +12) {
                                            System.out.print(" ");
                                        } else {
                                            System.out.print(inputStr);
                                            j += inputStr.length() - 1;
                                        }
                                    }
                                }
                            }
                            if(!hasAppointInLine){
                                System.out.print(" ");
                            }
                        }else {
                            System.out.print(" ");
                        }
                    }
                }
            }
                System.out.println();
        }
        printFiveDay();

        System.out.println(user.getUserName() + " (" + user.getUserID() + ")" + " has "+ appointNum +" appointments.");
    }

    //print five days
    private void printFiveDay(){
        System.out.printf("\t\t %s\t\t %s\t\t %s\t\t %s\t\t %s",
                "Mon", "Tue", "Wed", "Thu", "Fri");
        System.out.println();
    }

    //print doc and patient "help" command
    public void printDPHelp(){
        System.out.println("\n" +
                "Available commands:\n" +
                "  show   [ reminder | timetable ]\n" +
                "  add    appointment\n" +
                "  delete appointment\n" +
                "  help   [ command ]\n" +
                "  logout\n");
    }

    //print doc and patient "help show" command
    public void printDPHelpShow(){
        System.out.println("\n" +
                "\"show\" -- shows reminders or timetable.\n" +
                "Usage of \"show\":\n" +
                "  show reminder\n" +
                "  show timetable\n" +
                "\n");
    }

    //print doc and patient "help add" command
    public void printDPHelpAdd(){
        System.out.println("\n" +
                "\"add\" -- adds a new appointment to the system.\n" +
                "Usage of \"add\":\n" +
                "  add patientID timeslot\n" +
                "\n");
    }

    //print doc and patient "help delete" command
    public void printDPHelpDelete(){
        System.out.println("\n" +
                "\"delete\" -- delete an appointment from the system.\n" +
                "Usage of \"delete\":\n" +
                "  delete patientID timeslot\n" +
                "\n");
    }

    //print doc and patient "help help" command
    public void printDPHelpHelp(){
        System.out.println("\"help\" -- shows this help message.\n" +
                "Usage of \"help\":\n" +
                "  help\n" +
                "  help command\n" +
                "\n");
    }

    //print doc and patient "help logout" command
    public void printDPHelpLogout(){
        System.out.println("\n" +
                "\"logout\" -- logout from Room Booking System.\n" +
                "Usage of \"logout\":\n" +
                "  logout\n" +
                "\n");
    }

    //if the time string format is correct
    public boolean isCorrectTimeFormat(String timeStr){
        boolean dayTrue = isRightDay(timeStr);
        boolean lengthTrue = isRightLengthOfTimeS(timeStr);

        if(dayTrue && lengthTrue && isRightHour(timeStr)){
            return true;
        }else {
            return false;
        }
    }

    //if input string have the right day format
    private boolean isRightDay(String timeStr){
        //if first char correct
        if(timeStr.charAt(0) == 'M' ||timeStr.charAt(0) == 'T' ||
                timeStr.charAt(0) == 'R' ||timeStr.charAt(0) == 'W'
                || timeStr.charAt(0) == 'F') {
            return true;
        }else {
            return false;
        }
    }

    //if input string have the right hour format
    private boolean isRightHour(String timeStr){
        if(noCorrectHour(timeStr) >= 9 && noCorrectHour(timeStr) <= 18){
            return true;
        }else {
            return false;
        }
    }

    //if has next string after the space
    public int totalArgumentNum(String wholeStr){
        int count = 0;
        boolean isSpace = false;
        for(int i = 0; i < wholeStr.length(); i++){
            if(wholeStr.charAt(i) == ' ' && isSpace == false){
                count++;
                isSpace = true;
            }else if(wholeStr.charAt(i) != ' '){
                isSpace = false;
            }
        }
        count = count + 1;
        return count;
    }


}
