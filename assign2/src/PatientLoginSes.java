/**
 * The PatientLoginSes class is the subclass of LoginSes which is to do the operation
 * for the user type is patient.
 */

public class PatientLoginSes extends LoginSes{
    private User user;

    public PatientLoginSes(){
        super();
        this.user = new User();
    }

    /**
     *  Constructor to create a PatientLoginSes with specified User and LoginSes.
     * @param user  the User which is login
     * @param login the LoginSes to get the database
     */

    public PatientLoginSes(User user, LoginSes login){
        super(login.getUserDataBase(), login.getAppointDataBase());
        this.user = user;
    }

    //show command
    private void showCmd(String inputStr){
        //if 2 arguments
        if(this.totalArgumentNum(inputStr) == 2) {
            String commandType = this.getMiddleStr(inputStr, "show");

            switch (commandType){
                //if "show timetable" command
                case "timetable" :
                    this.drawTimetable(this.user);
                    break;

                //if "show reminder" command
                case "reminder" :
                    System.out.print(user.getUserName() + "(" + user.getUserID() + ")");
                    System.out.println(" is a patient, and has the following appointments:");
                    Appointment[] appointArray = this.getAppointDataBase().getAppoint(user.getUserID());

                    //show appointment
                    for (int i = 0; i < appointArray.length; i++) {
                        System.out.print(appointArray[i].getTimeslot());
                        System.out.print(" -- Doctor: ");
                        System.out.println(appointArray[i].getDoctorID() + "(" +
                                this.getUserDataBase().getUser(appointArray[i].getDoctorID()).getUserName() + ")");
                    }
                    System.out.println(appointArray.length + " appointments found.");
                    break;

                default:
                    System.out.println("Unknown option, \"" + commandType + "\", for Show.");
                    break;
            }
        }else {
            System.out.println("Invalid number of arguments for Show.");
        }
    }

    //add command
    private void addCmd(String inputStr){
        //if 3 arguments
        if(this.totalArgumentNum(inputStr) == 3) {
            String userID = this.getMiddleStr(inputStr, "add");
            String timeStr = this.getRestStr(inputStr, userID);
            userID = userID.toLowerCase();
            System.out.print("Adding new appointment, (" + userID + "," + this.user.getUserID() + "," + timeStr + "):");

            //if doctor exist
            if (this.getUserDataBase().hasSuchDoc(userID)) {
                //if timeslot format correct
                if (this.isCorrectTimeFormat(timeStr)) {
                    TimeSlot newTime = new TimeSlot(timeStr);
                    Appointment newAppoint = new Appointment(userID, this.user.getUserID(), newTime);
                    this.getAppointDataBase().addAppoint(newAppoint);
                //failed, corrupted time slot
                } else {
                    System.out.println("failed!");
                    System.out.println("Corrupted time slot (" + timeStr + ")");
                }
            //failed, no such doctor
            } else {
                System.out.println("failed!");
                System.out.println("No such doctor (" + userID + ")");
            }
        //wrong number of arguments
        }else {
            System.out.println("Invalid number of arguments for Add.");
        }
    }

    //delete command
    private void deleteCmd(String inputStr){
        //if 3 arguments
        if(this.totalArgumentNum(inputStr) == 3) {
            String userID = this.getMiddleStr(inputStr, "delete");
            String timeStr = this.getRestStr(inputStr, userID);
            userID = userID.toLowerCase();
            System.out.print("Deleting appointment, (" + userID + "," + this.user.getUserID() + "," + timeStr + "):");

            //if patient exist
            if (this.getUserDataBase().hasSuchDoc(userID)) {
                //if timeslot format correct
                if (this.isCorrectTimeFormat(timeStr)) {
                    TimeSlot newTime = new TimeSlot(timeStr);
                    Appointment newAppoint = new Appointment(userID, this.user.getUserID(), newTime);
                    //if have such appointment
                    if (this.getAppointDataBase().hasSuchAppoint(newAppoint)) {
                        this.getAppointDataBase().deleteAppoint(newAppoint);
                        System.out.println("done!");
                    //no such appointment
                    } else {
                        System.out.println("failed!");
                        System.out.println("Appointment (" + userID + ","
                                + this.user.getUserID() + "," + timeStr
                                + ") not found in Appointment DB");
                    }
                //failed, corrupted time slot
                } else {
                    System.out.println("failed!");
                    System.out.println("Corrupted time slot (" + timeStr + ")");
                }
            //no such doctor
            } else {
                System.out.println("failed!");
                System.out.println("No such doctor (" + userID + ")");
            }
        //wrong number of arguments
        }else {
            System.out.println("Invalid number of arguments for Delete.");
        }
    }

    //help command
    private void helpCmd(String inputStr){
        if(this.totalArgumentNum(inputStr) < 3) {
            String helpTp = this.getMiddleStr(inputStr, "help");
            System.out.println("User: " + this.user.getUserID() + "  *** Patient ***");

            //if "help" command
            if (helpTp == null) {
                this.printDPHelp();
            } else {
                switch (helpTp){
                    case "show" :   this.printDPHelpShow(); break;
                    case "add"  :   this.printDPHelpAdd();  break;
                    case "delete" : this.printDPHelpDelete(); break;
                    case "help" :   this.printDPHelpHelp(); break;
                    case "logout" : this.printDPHelpLogout(); break;
                    default:
                        System.out.println("Unknown command (" + helpTp + ")");
                        break;
                }
            }
        }else {
            System.out.println("help: invalid number of arguments.");
        }
    }

    //main system of patient login
    public void mainSys(boolean[] loginSuccess)throws Exception{
        //get input string and command
        String inputStr = this.getInputStr();
        String command = this.strBeforeSpace(inputStr);

        if(command == null){
            return;
        }

        switch (command){
            case "show" :   showCmd(inputStr);  break;
            case "add" :    addCmd(inputStr);   break;
            case "delete" : deleteCmd(inputStr);  break;
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
}
