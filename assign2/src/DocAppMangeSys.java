/*
program description: Doctor Appointment Management System
                     1.The system allow users to make/manage doctor appointments and to check their status.
                     2.There are 3 types of users in the system.
                     3.Doctors and Patients are allowed to make/delete appointments, and showing the current status of the appointments.
                     4.Another type of user, Administrators are allowed to carry out various maintenance functions of the system.
                     5.All users are required to login to the system before using the system.
Author name : ZHANG Jiayi
Student Number: 19250568
*/

public class DocAppMangeSys {
    public static void main(String[] args)throws Exception {
        new DocAppMangeSys().runApp(args);
    }

    void runApp(String[] args)throws Exception{
        LoginSes login = new LoginSes();
        System.out.println("*** System Startup: begin ***");
        System.out.print("Loading user db from " + args[0] + "...\n");

        //read user file as string
        String[] usersStr = login.readFile(args[0]);

        //create User data base
        UserDB allUserDataBase = login.createUserDB(usersStr, args[0]);
        login.setUserDataBase(allUserDataBase);
        System.out.println(login.getUserDataBase().getUserArray().length+ " user records loaded.");

        //read appointment file as string
        String[] appointmentStr = login.readFile(args[1]);

        //if such file do not exit, return
        if(appointmentStr.length == 0){
            System.out.println("Load AppointmentDB failed. Quit!");
            return;
        }

        //create appointment data base
        AppointmentDB allAppointDataBase = login.createAppointmentDB(appointmentStr, args[1]);
        login.setAppointDataBase(allAppointDataBase);
        System.out.println(login.getAppointDataBase().getAppointArray().length + " appointment records loaded.");

        //print program beginning text
        login.programBegin();

        //whether login successfully
        boolean[] loginSuccess =new boolean[1];
        loginSuccess[0] = false;

        //whether quit
        boolean[] hasQuit = new boolean[1];
        hasQuit[0] = false;

        //create current user class
        User[] currentUser = new User[1];
        currentUser[0] = new User();

        //while do not quit the program
        while(!hasQuit[0]) {
            do{
                //do the login system and return if login successfully or not
                loginSuccess[0] = login.loginSys(login,currentUser,hasQuit);
                //if quit in the login system, break
                if(hasQuit[0] == true){
                    break;
                }
            }while (!loginSuccess[0]);

            //while login successfully
            while (loginSuccess[0]) {
                // create user of current login user
                User loginUser = allUserDataBase.getUser(currentUser[0].getUserID());

                //if user is an admin
                if (loginUser.getUserType().equals('a' + "")) {
                    AdminLoginSes adminLogin = new AdminLoginSes(loginUser, login);
                    while (loginSuccess[0]) {
                        adminLogin.mainSys(loginSuccess);// do admin login session
                    }

                //if user is a doctor
                }else if(loginUser.getUserType().equals('d' + "")){
                    DocLoginSes docLogin = new DocLoginSes(loginUser, login);
                    while (loginSuccess[0]) {
                        docLogin.mainSys(loginSuccess);// do doctor login session
                    }

                //if user is a patient
                }else if(loginUser.getUserType().equals('p' + "")){
                    PatientLoginSes patLogin = new PatientLoginSes(loginUser, login);
                    while (loginSuccess[0]) {
                        patLogin.mainSys(loginSuccess);// do patient login session
                    }
                }
            }
        }
    }
}
