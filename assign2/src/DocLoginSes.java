/**
 * The DocLoginSes class is the subclass of LoginSes which is to do the operation
 * for the user type is doctor.
 */

public class DocLoginSes extends LoginSes {
    private User user;

    public DocLoginSes(){
        super();
        this.user = new User();
    }

    /**
     *  Constructor to create a DocLoginSes with specified User and LoginSes.
     * @param user  the User which is login
     * @param login the LoginSes to get the database
     */

    public DocLoginSes(User user, LoginSes login){
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
                    System.out.println(" is a doctor, and has the following appointments:");
                    Appointment[] appointArray = this.getAppointDataBase().getAppoint(user.getUserID());
                    //show appointment
                    for (int i = 0; i < appointArray.length; i++) {
                        System.out.print(appointArray[i].getTimeslot());
                        System.out.print(" -- Patient: ");
                        System.out.println(appointArray[i].getPatientID() + "(" +
                                this.getUserDataBase().getUser(appointArray[i].getPatientID()).getUserName() + ")");
                    }
                    //total appointments
                    System.out.println(appointArray.length + " appointments found.");
                    break;

                //if default
                default:
                    System.out.println("Unknown option, \"" + commandType + "\", for Show.");
                    break;
            }
        //wrong argument number for show
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
            System.out.print("Adding new appointment, (" + this.user.getUserID() + "," + userID + "," + timeStr + "):");

            //if patient exist
            if (this.getUserDataBase().hasSuchPat(userID)) {
                //if timeslot format correct
                if (this.isCorrectTimeFormat(timeStr)) {
                    TimeSlot newTime = new TimeSlot(timeStr);
                    Appointment newAppoint = new Appointment(this.user.getUserID(), userID, newTime);
                    this.getAppointDataBase().addAppoint(newAppoint);
                //failed, corrupted time slot
                } else {
                    System.out.println("failed!");
                    System.out.println("Corrupted time slot (" + timeStr + ")");
                }
            //failed, patient do not exist
            } else {
                System.out.println("failed!");
                System.out.println("No such patient (" + userID + ")");
            }
        //wrong number of argument for add
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
            System.out.print("Deleting appointment, (" + this.user.getUserID() + "," + userID + "," + timeStr + "):");

            //if patient exist
            if (this.getUserDataBase().hasSuchPat(userID)) {
                //if timeslot format correct
                if (this.isCorrectTimeFormat(timeStr)) {
                    TimeSlot newTime = new TimeSlot(timeStr);
                    Appointment newAppoint = new Appointment(this.user.getUserID(), userID, newTime);
                    //if appointment exist
                    if (this.getAppointDataBase().hasSuchAppoint(newAppoint)) {
                        this.getAppointDataBase().deleteAppoint(newAppoint);
                        System.out.println("done!");
                    //fail, appointment no found
                    } else {
                        System.out.println("failed!");
                        System.out.println("Appointment (" + this.user.getUserID() + "," + userID + "," + timeStr + ") not found in Appointment DB");
                    }
                //failed, corrupted timeslot
                } else {
                    System.out.println("failed!");
                    System.out.println("Corrupted time slot (" + timeStr + ")");
                }
            //failed, such patient do not exist
            } else {
                System.out.println("failed!");
                System.out.println("No such patient (" + userID + ")");
            }
        //wrong number of argument
        }else {
            System.out.println("Invalid number of arguments for Delete.");
        }
    }

    //help command
    private void helpCmd(String inputStr){
        if(this.totalArgumentNum(inputStr) < 3) {
            String helpTp = this.getMiddleStr(inputStr, "help");
            System.out.println("User: " + this.user.getUserID() + "  *** Doctor ***");

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

    //main system of doctor login
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
