/**
 * The AdminLoginSes class is the subclass of LoginSes which is to do the operation
 * for the user type is admin.
 */

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class AdminLoginSes extends LoginSes {
    private User user;

    public AdminLoginSes(){
        super();
        this.user = new User();
    }

    /**
     * Constructor to create a AdminLoginSes with specified User and LoginSes
     * @param user  the User which is login
     * @param login the LoginSes to get the database
     */

    public AdminLoginSes(User user, LoginSes login){
        super(login.getUserDataBase(), login.getAppointDataBase());
        this.user = user;
    }

    //load command
    private void loadCmd(String inputStr)throws Exception{
        //if 3 arguments
        if(this.totalArgumentNum(inputStr) == 3) {
            //get command type
            String commandType = this.getMiddleStr(inputStr, "load");

            switch (commandType){
                //if load user
                case "user" :
                    //get load file name
                    String userFN = this.getRestStr(inputStr, commandType);
                    //print loading user
                    System.out.println("Loading user db from " + userFN + "...");
                    //get user data base
                    String[] usersStr = this.readFileLoad(userFN);
                    if(usersStr == null){
                        System.out.println("UserDB.loadDB failed.");
                        break;
                    }
                    UserDB allUserDataBase = this.createUserDB(usersStr, userFN);
                    this.addUserDataBase(allUserDataBase, userFN);
                    break;

                //if load appointment
                case "appointment" :
                    String appointFN = this.getRestStr(inputStr, commandType);
                    //get appointment data base
                    String[] appointStr = this.readFileLoad(appointFN);

                    if(appointStr == null){
                        System.out.println("AppointmentDB.loadDB failed.");
                        break;
                    }

                    AppointmentDB allAppointDataBase = this.createAppointmentDB(appointStr, appointFN);
                    this.addAppointDataBase(allAppointDataBase, appointFN);
                    break;

                //if default
                default:
                    String unknownCommand = this.getRestStr(inputStr, commandType);
                    System.out.println("Unknown option, " + unknownCommand + ", for Load.");
                    break;
            }
        }else {
            System.out.println("Invalid number of arguments for Load.");
        }
    }

    //save command
    private void saveCmd(String inputStr)throws Exception{
        //if 3 arguments
        if(this.totalArgumentNum(inputStr) == 3) {
            String commandType = this.getMiddleStr(inputStr, "save");

            switch (commandType){
                //if save user
                case "user":
                    String outputFNUser = this.getRestStr(inputStr, commandType);
                    //write in the output file
                    PrintWriter outUser = new PrintWriter(outputFNUser);
                    try {
                        for (int i = 0; i < this.getUserDataBase().getAllUsers().length; i++) {
                            outUser.printf("%-10s%-12s%-3s%s\n", this.getUserDataBase().getAllUsers()[i].getUserID(), this.getUserDataBase().getAllUsers()[i].getPassword(),
                                    this.getUserDataBase().getAllUsers()[i].getUserType(), this.getUserDataBase().getAllUsers()[i].getUserName());
                        }
                        outUser.close();
                        System.out.println(this.getUserDataBase().getAllUsers().length +
                                " user records saved to " + outputFNUser);
                    }catch (Exception e){
                        e.printStackTrace();
                        System.exit(0);
                    }
                    break;

                //if save appointment
                case "appointment":
                    String outputFNAppoint = this.getRestStr(inputStr, commandType);
                    //write in the output file
                    PrintWriter outAppoint = new PrintWriter(outputFNAppoint);
                    try {
                        for (int i = 0; i < this.getAppointDataBase().getAppointArray().length; i++) {
                            outAppoint.printf("%-10s%-10s%-3s\n",
                                    this.getAppointDataBase().getAppointArray()[i].getDoctorID(),
                                    this.getAppointDataBase().getAppointArray()[i].getPatientID(),
                                    this.getAppointDataBase().getAppointArray()[i].getTimeslot());
                        }
                        outAppoint.close();
                        System.out.println(this.getAppointDataBase().getAppointArray().length +
                                " appointment records saved to " + outputFNAppoint);
                    }catch (Exception e){
                        e.printStackTrace();
                        System.exit(0);
                    }
                    break;

                //if default
                default:
                    System.out.println("Invalid number of arguments for Save.");
                    break;
            }
        }
    }

    //list command
    private void listCmd(String inputStr){
        if(this.totalArgumentNum(inputStr) == 2) {
            String commandType = this.getMiddleStr(inputStr, "list");

            switch (commandType){
                //if list user
                case "user" :
                    String testNull1 = this.getRestStr(inputStr, commandType);
                    //if wrong argument number
                    if (!testNull1.equals("")) {
                        System.out.println("Invalid number of arguments for List.");
                    //if right argument number
                    } else {
                        this.getUserDataBase().printFormat();
                        System.out.println(this.getUserDataBase().getUserArray().length +
                                " users in found.");
                    }
                    break;

                //if list appointment
                case  "appointment" :
                    String testNull2 = this.getRestStr(inputStr, commandType);
                    System.out.println("Listing all appointments:");
                    //if wrong argument number
                    if (!testNull2.equals("")) {
                        System.out.println("Invalid number of arguments for List.");
                        //if right argument number
                    } else {
                        this.getAppointDataBase().printFormat();
                        System.out.println(this.getAppointDataBase().getAppointArray().length + " appointments in found.");
                    }
                    break;

                //if default
                default:
                    System.out.println("Unknown option, " + commandType + ", for List.");
            }

        //wrong argument number
        }else {
            System.out.println("Invalid number of arguments for List.");
        }
    }

    //show command
    private void showCmd(String inputStr){
        if(this.totalArgumentNum(inputStr) == 3) {
            String commandType = this.getMiddleStr(inputStr, "show");

            switch (commandType){
                //if show timetable
                case "timetable" :
                    String userID1 = this.getRestStr(inputStr, commandType);
                    userID1 = userID1.toLowerCase();

                    //if user exist
                    if (this.hasUser(userID1)) {
                        User specifiedUser = this.getUserDataBase().getUser(userID1);
                        //if is not admin
                        if (!specifiedUser.getUserType().equals("a")) {
                            this.drawTimetable(specifiedUser);
                        //if is an admin
                        } else {
                            System.out.println(specifiedUser.getUserName() + "(" + specifiedUser.getUserID() + ")"
                                    + " is an admin, not a doctor or patient.");
                        }
                    //the user do not exist
                    } else {
                        System.out.println("No such user (" + userID1 + ")");
                    }
                    break;

                //if show reminder
                case "reminder" :
                    String userID2 = this.getRestStr(inputStr, commandType);
                    userID2 = userID2.toLowerCase();

                    //if user exist
                    if (this.hasUser(userID2)) {
                        User specifiedUser = this.getUserDataBase().getUser(userID2);
                        System.out.print(specifiedUser.getUserName() + "(" + specifiedUser.getUserID() + ")");

                        //if admin
                        if (specifiedUser.getUserType().equals("a")) {
                            System.out.println(" is an admin, not a doctor or a patient.");

                        //if doctor
                        } else if (specifiedUser.getUserType().equals("d")) {
                            System.out.println(" is a doctor, and has the following appointments:");
                            //all appointment that doctor have
                            Appointment[] appointArray = this.getAppointDataBase().getAppoint(userID2);
                            //print
                            for (int i = 0; i < appointArray.length; i++) {
                                System.out.print(appointArray[i].getTimeslot());
                                System.out.print(" -- Patient: ");
                                System.out.println(appointArray[i].getPatientID() + "(" +
                                        this.getUserDataBase().getUser(appointArray[i].getPatientID()).getUserName() + ")");
                            }
                            System.out.println(appointArray.length + " appointments found.");

                        //if patient
                        } else if (specifiedUser.getUserType().equals("p")) {
                            System.out.println(" is a patient, and has the following appointments:");
                            //all appointment for a patient
                            Appointment[] appointArray = this.getAppointDataBase().getAppoint(userID2);
                            //print
                            for (int i = 0; i < appointArray.length; i++) {
                                System.out.print(appointArray[i].getTimeslot());
                                System.out.print(" -- Doctor: ");
                                System.out.println(appointArray[i].getDoctorID() + "(" +
                                        this.getUserDataBase().getUser(appointArray[i].getDoctorID()).getUserName() + ")");
                            }
                            System.out.println(appointArray.length + " appointments found.");
                        }
                    //the user do not exist
                    } else {
                        System.out.println("No such user (" + userID2 + ")");
                    }
                    break;

                //if default
                default:
                    System.out.println("Unknown option, " + commandType + ", for Show.");
            }
        //wrong argument number
        }else {
            System.out.println("Invalid number of arguments for Show.");
        }
    }


    //add command
    private void addCmd(String inputStr){
        String userTp = this.getMiddleStr(inputStr, "add");
        boolean addingUser = strIsUser(userTp);
        boolean addingAppoint = strIsAppoint(userTp);

        //if do the add user command
        if(addingUser) {
            String userID = this.getMiddleStr(inputStr, userTp);
            String userPd = this.getMiddleStr(inputStr, userID);
            String userName = this.getRestStr(inputStr, userPd);
            userID = userID.toLowerCase();

            System.out.print("Adding new user, " + userName + "("
                    + userID +") as " + userTp + " :");

            //if have no repeat user
            if(!this.getUserDataBase().hasRepeatId(userID)) {
                User newUser = new User(userID, userPd, userTp.charAt(0) + "", userName);
                this.getUserDataBase().addUser(newUser);
                System.out.println("done!");
            //the user is repeated
            }else {
                System.out.println("failed!");
                System.out.println("Duplicated user record (" + userID + ")");
            }

        //if do add appointment command
        }else if(addingAppoint){
            String appDocID = this.getMiddleStr(inputStr, userTp);
            String appPatID = this.getMiddleStr(inputStr, appDocID);
            String timeStr = this.getRestStr(inputStr, appPatID);
            appDocID = appDocID.toLowerCase();
            appPatID = appPatID.toLowerCase();

            System.out.print("Adding new appointment, (" + appDocID + ","
                    + appPatID + "," + timeStr + ") : ");

            //have such doctor
            if(this.getUserDataBase().hasSuchDoc(appDocID)){
                //have such patient
                if(this.getUserDataBase().hasSuchPat(appPatID)){
                    //have correct timeslot format
                    if(this.isCorrectTimeFormat(timeStr)) {
                        TimeSlot newTime = new TimeSlot(timeStr);
                        Appointment newAppoint = new Appointment(appDocID, appPatID, newTime);

                        //have time clash with doctor
                        if (this.getAppointDataBase().hasDocTimeClash(newAppoint)) {
                            System.out.println("failed!");
                            System.out.println("Doctor appointment time clash ("
                                    + appDocID + ", " + timeStr + ")");

                        //have time clash with patient
                        } else if (this.getAppointDataBase().hasPatTimeClash(newAppoint)) {
                            System.out.println("failed!");
                            System.out.println("Patient appointment time clash ("
                                    + appPatID + ", " + timeStr + ")");
                        //success, do not have time clash
                        } else {
                            this.getAppointDataBase().addAppoint(newAppoint);
                            System.out.println("done!");
                        }
                    //failed, corrupted time slot
                    }else {
                        System.out.println("failed!");
                        System.out.println("Corrupted time slot (" + timeStr +")");
                    }
                //failed, patient do not exist
                }else {
                    System.out.println("failed!");
                    System.out.println("No such patient (" + appPatID +")");
                }
            //failed, doctor do not exist
            }else {
                System.out.println("failed!");
                System.out.println("No such doctor (" + appDocID +")");
            }
        //unknown command option for add
        } else{
            System.out.println("Unknown option, \"" + userTp +"\", for Add.");
        }
    }

    private int totalFileLineNumLoad(String fileName)throws Exception{
        File inputFile = new File(fileName);

        if(!inputFile.exists()){
            //System.out.println(" File not found (" + fileName + ")!");
            return 0;
        }

        Scanner input = new Scanner(inputFile);
        int count = 0;
        while (input.hasNextLine()) {
            count++;
            input.nextLine();
        }
        input.close();
        return count;
    }


    private String[] readFileLoad(String fileName)throws Exception{
        int totalLines = totalFileLineNumLoad(fileName);
        String[] linesArray = new String[totalLines];
        File inputFile = new File(fileName);
        if(!inputFile.exists()){
            System.out.println(" File not found (" + fileName + ")!");
            return null;
        }

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
    }

    //delete command
    private void deleteCmd(String inputStr){
        String userTp = this.getMiddleStr(inputStr, "delete");
        boolean deleteUser = strIsUser(userTp);
        boolean deleteAppoint = strIsAppoint(userTp);

        //if delete user command
        if(deleteUser) {
            //if right argument for delete which is 3
            if(this.totalArgumentNum(inputStr) == 3) {
                String userID = this.getMiddleStr(inputStr, userTp);
                userID = userID.toLowerCase();
                System.out.print("Deleting " + userTp + "(" + userID + "): ");

                //if user exist
                if (this.getUserDataBase().hasSuchUser(userID)) {
                    //if admin
                    if (userTp.charAt(0) == 'a') {
                        //if admin exist
                        if (this.getUserDataBase().hasSuchAdmin(userID)) {
                            deleteUserDBAndAppDB(userID);
                            System.out.println("done!");
                        //failed, is not a admin
                        } else {
                            System.out.println("failed!");
                            String truthTp = this.getUserDataBase().getUser(userID).getUserType();
                            String type = "";
                            if (truthTp.equals("d")) {
                                type = "doctor";
                            } else if (truthTp.equals("p")) {
                                type = "patient";
                            }
                            System.out.println("Delete user failed: User (" +
                                    userID + ") type mismatch.  The user is a " +
                                    type + ".");
                        }

                    //if doctor
                    } else if (userTp.charAt(0) == 'd') {
                        //if doctor exist
                        if (this.getUserDataBase().hasSuchDoc(userID)) {
                            deleteUserDBAndAppDB(userID);
                            System.out.println("done!");
                        //failed, is not a doctor
                        } else {
                            System.out.println("failed!");
                            String truthTp = this.getUserDataBase().getUser(userID).getUserType();
                            String type = "";
                            if (truthTp.equals("a")) {
                                type = "administrator";
                            } else if (truthTp.equals("p")) {
                                type = "patient";
                            }
                            System.out.println("Delete user failed: User (" +
                                    userID + ") type mismatch.  The user is a " +
                                    type + ".");
                        }

                    //if patient
                    } else if (userTp.charAt(0) == 'p') {
                        //if patient exist
                        if (this.getUserDataBase().hasSuchPat(userID)) {
                            deleteUserDBAndAppDB(userID);
                            System.out.println("done!");
                        //failed, is not a patient
                        } else {
                            System.out.println("failed!");
                            String truthTp = this.getUserDataBase().getUser(userID).getUserType();
                            String type = "";
                            if (truthTp.equals("a")) {
                                type = "administrator";
                            } else if (truthTp.equals("d")) {
                                type = "doctor";
                            }
                            System.out.println("Delete user failed: User (" +
                                    userID + ") type mismatch.  The user is a " +
                                    type + ".");
                        }
                    }
                //failed, user input no found
                } else {
                    System.out.println("Delete user failed: User (" + userID + ") not found in User DB");
                }
            //failed, wrong argument number for delete
            }else {
                System.out.println("Invalid number of arguments for Delete.");
            }


        //if delete appointment
        }else if(deleteAppoint){
            //if right argument number for delete appointment
            if(this.totalArgumentNum(inputStr) == 5) {
                String appDocID = this.getMiddleStr(inputStr, userTp);
                String appPatID = this.getMiddleStr(inputStr, appDocID);
                String timeStr = this.getRestStr(inputStr, appPatID);
                appDocID = appDocID.toLowerCase();
                appPatID = appPatID.toLowerCase();

                System.out.print("Deleting appointment, (" + appDocID + ","
                        + appPatID + "," + timeStr + ") : ");

                //if doctor exist
                if (this.getUserDataBase().hasSuchDoc(appDocID)) {
                    //if patient exist
                    if (this.getUserDataBase().hasSuchPat(appPatID)) {
                        // if time string correct
                        if (this.isCorrectTimeFormat(timeStr)) {
                            TimeSlot newTime = new TimeSlot(timeStr);
                            Appointment newAppoint = new Appointment(appDocID, appPatID, newTime);

                            //if appointment exist
                            if (this.getAppointDataBase().hasSuchAppoint(newAppoint)) {
                                this.getAppointDataBase().deleteAppoint(newAppoint);
                                System.out.println("done!");
                            //no such appointment
                            } else {
                                System.out.println("failed!");
                                System.out.println("Delete appointment failed: Appointment ("
                                        + appDocID + ", " + appPatID + ", " + timeStr + ") not found in Appointment DB");
                            }
                        //failed, corrupted time slot
                        } else {
                            System.out.println("failed!");
                            System.out.println("Corrupted time slot (" + timeStr + ")");
                        }
                    //failed, no such patient
                    } else {
                        System.out.println("failed!");
                        System.out.println("No such patient (" + appPatID + ")");
                    }
                //failed, no such doctor
                } else {
                    System.out.println("failed!");
                    System.out.println("No such doctor (" + appDocID + ")");
                }
            }
        //unknown option
        }else{
            System.out.println("Unknown option, \"" + userTp +"\", for Delete.");
        }
    }

    //help command
    private void helpCmd(String inputStr){
        String commandTp = this.getMiddleStr(inputStr, "help");
        System.out.println("User: " + user.getUserID() + " *** ADMIN ***");

        //if right argument number
        if(this.totalArgumentNum(inputStr) < 3) {
            //if help
            if (commandTp == null) {
                System.out.println("\nAvailable commands: ");
                System.out.println("  load   [ user | appointment ]\n" +
                        "  save   [ user | appointment ]\n" +
                        "  list   [ user | appointment ]\n" +
                        "  show   [ reminder | timetable ]\n" +
                        "  add    [ user | appointment ]\n" +
                        "  delete [ user | appointment ]\n" +
                        "  help   [ command ]\n" +
                        "  logout\n");
            }else {
                switch (commandTp) {
                    // if "help load" command
                    case "load":
                        System.out.println("\n\"load\" -- loads users or appointments from a file.\n" +
                                "Usage of \"load\":\n" +
                                "  load user fName\n" +
                                "  load appointment fName\n");
                        break;

                    // if "help save" command
                    case "save":
                        System.out.println("\n\"save\" -- saves users or appointments to a file.\n" +
                                "Usage of \"save\":\n" +
                                "  save user fName\n" +
                                "  save appointment fName\n");
                        break;

                    //if "help list" command
                    case "list":
                        System.out.println("\n\"list\" -- lists all users or appointments.\n" +
                                "Usage of \"list\":\n" +
                                "  list user\n" +
                                "  list appointment\n");
                        break;

                    //if "help show" command
                    case "show":
                        System.out.println("\n" +
                                "\"show\" -- shows reminders or timetable of a user.\n" +
                                "Usage of \"show\":\n" +
                                "  show reminder userID\n" +
                                "  show timetable userID\n");
                        break;

                    //if "help add" command
                    case "add":
                        System.out.println("\n" +
                                "\"add\" -- adds a new user (admin/doctor/patient) or appointment to the system.\n" +
                                "Usage of \"add\":\n" +
                                "  add admin userID passwd [ fullname ]\n" +
                                "  add doctor userID passwd [ fullname ]\n" +
                                "  add patient userID passwd [ fullname ]\n" +
                                "  add appointment doctorID patientID timeslot\n" +
                                "\n");
                        break;

                    //if "help delete" command
                    case "delete":
                        System.out.println("\n" +
                                "\"delete\" -- delete a user (admin/doctor/patient) or appointment from the system.\n" +
                                "Usage of \"delete\":\n" +
                                "  delete admin userID\n" +
                                "  delete doctor userID\n" +
                                "  delete patient userID\n" +
                                "  delete appointment doctorID patientID timeslot\n" +
                                "\n");
                        break;

                    //if "help help" command
                    case "help":
                        System.out.println("\n" +
                                "\"help\" -- shows this help message.\n" +
                                "Usage of \"help\":\n" +
                                "  help\n" +
                                "  help command\n" +
                                "\n");
                        break;

                    //if "help logout" command
                    case "logout":
                        System.out.println("\n" +
                                "\"logout\" -- logout from Room Booking System.\n" +
                                "Usage of \"logout\":\n" +
                                "  logout\n" +
                                "\n");
                        break;

                    default:
                        System.out.println("\n" +
                                "Unknown command (" + commandTp + ")\n" +
                                "\n");
                        break;
                }
            }
        //wrong number of help command
        }else {
            System.out.println("help: invalid number of arguments.");
        }
    }

    public void mainSys(boolean[] loginSuccess)throws Exception{
        //get input string and command
        String inputStr = this.getInputStr();
        String command = this.strBeforeSpace(inputStr);

        if(command == null){
            return;
        }

        switch(command){
            case "load" :   loadCmd(inputStr);  break;
            case "save" :   saveCmd(inputStr);  break;
            case "list" :   listCmd(inputStr);  break;
            case "show" :   showCmd(inputStr);  break;
            case "add"  :   addCmd(inputStr);   break;
            case "delete":  deleteCmd(inputStr);    break;
            case "help" :   helpCmd(inputStr);  break;
            case "logout":
                if(this.totalArgumentNum(inputStr) == 1) {
                    loginSuccess[0] = false;
                }else {
                    System.out.println("logout: too many arguments.");
                }
                break;
            default:
                System.out.println("Unknown command: " + command);
                break;

        }
    }

    //input userid and delete both userDB and appointmentDB
    private void deleteUserDBAndAppDB(String userID){
        this.getUserDataBase().deleteUser(userID);
        this.getAppointDataBase().deleteAllAppointOfUser(userID);
    }

    //if the input user type is correct
    private boolean strIsUser(String userTp){
        if(userTp.equals("admin") || userTp.equals("doctor") || userTp.equals("patient")){
            return true;
        }else {
            return false;
        }
    }

    //if input is appointment
    private boolean strIsAppoint(String userTp){
        if(userTp.equals("appointment")){
            return true;
        }else {
            return false;
        }
    }

}
