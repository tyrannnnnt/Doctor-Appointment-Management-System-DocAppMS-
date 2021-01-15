/**
 * The AppointmentDB class presents a appointment array.
 */

public class AppointmentDB {
    private Appointment[] allAppointments;

    public AppointmentDB(){
        this.allAppointments = new Appointment[0];
    }

    public AppointmentDB(Appointment newAppointment){
        this.allAppointments = new Appointment[1];
        this.allAppointments[0] = newAppointment;
    }

    /**
     * Constructor to create a appointmentDB with specified appointment array
     * @param newAppointmentArray   the appointment array of a AppointmentDB
     */

    public AppointmentDB(Appointment[] newAppointmentArray){
        this.allAppointments = newAppointmentArray;
    }

    //set appointment database
    public void setAllAppointments(Appointment[] appointArray){
        this.allAppointments = appointArray;
    }

    //add appointment array with appointment array input
    public void addAppointArray(Appointment[] appointArray, String loadFN){
        Appointment[] newAppointArray = new Appointment[this.allAppointments.length+appointArray.length];
        int pos = this.allAppointments.length;
        int repeatCount = 0;
        int addNum = this.allAppointments.length;

        //get old appointment
        for(int i = 0; i < this.allAppointments.length; i++){
            newAppointArray[i] = this.allAppointments[i];
        }

        //get new appointment
        for(int i = 0; i < appointArray.length; i++){
            boolean repeated = false;
            int repeatPos = 0;
            for(int j = 0; j < newAppointArray.length; j++) {
                if(newAppointArray[j] != null) {
                    if (appointArray[i].docTimeClash(newAppointArray[j]) || appointArray[i].patTimeClash(newAppointArray[j])) {
                        repeated = true;
                        repeatPos = i+1;
                        break;
                    }
                }
            }
            //if repeated
            if(repeated){
                System.out.println("AppointmentDB.loadDB: error adding record on line" + repeatPos
                        + " of " + loadFN + " -- Patient appointment time clash(" +
                        appointArray[repeatPos-1].getPatientID() + "," + appointArray[repeatPos-1].getTimeslot() + ")");
                repeatCount++;
                continue;
            }else {
                newAppointArray[pos] = appointArray[i];
                pos++;
            }
        }

        //if have repeated
        if(repeatCount != 0){
            Appointment[] returnAppointArray = new Appointment[this.allAppointments.length + appointArray.length-repeatCount];
            for(int i = 0; i < returnAppointArray.length; i++){
                returnAppointArray[i] = newAppointArray[i];
            }
            this.allAppointments = returnAppointArray;
        //no repeated
        }else {
            this.allAppointments = newAppointArray;
        }
        addNum = appointArray.length - repeatCount;
        System.out.println(addNum + " appointment records loaded.");
    }

    //to string function
    public String toString(){
        String str = "";
        for(int i = 0; i < this.allAppointments.length; i++){
            str += allAppointments[i] + "\n";
        }
        return str;
    }

    //print appointment database in format
    public void printFormat(){
        for(int i = 0; i < this.allAppointments.length; i++){
            System.out.printf("%-10s%-10s%-3s\n", this.allAppointments[i].getDoctorID(),this.allAppointments[i].getPatientID(),
                    this.allAppointments[i].getTimeslot());
        }
    }

    //get appointment array
    public Appointment[] getAppointArray(){
        return this.allAppointments;
    }

    //if has such appointment in database
    public boolean hasAppointInDB(User user){
        for(int i = 0; i < this.getAppointArray().length; i++){
            if(this.getAppointArray()[i].hasAppoint(user)){
                return true;
            }
        }
        return false;
    }

    //add appointment
    public void addAppoint(Appointment appoint){
        for(int i = 0; i < this.allAppointments.length; i++) {
            if (appoint.docTimeClash(this.allAppointments[i])){
                System.out.println("Doc timeClash, add failed.");
                return;
            }else if(appoint.patTimeClash(this.allAppointments[i])){
                System.out.println("Doc timeClash, add failed.");
                return;
            }
        }
        Appointment[] newArray = new Appointment[this.allAppointments.length+1];
        for(int i = 0; i < this.allAppointments.length; i++) {
            newArray[i] = this.allAppointments[i];
        }
        newArray[this.allAppointments.length] = appoint;
        this.setAllAppointments(newArray);
        System.out.println("done!");
    }

    //delete appointment
    public void deleteAppoint(Appointment appoint){
        Appointment[] newAppoint = new Appointment[this.allAppointments.length-1];
        int index = 0;
        for(int i = 0; i < this.allAppointments.length; i++){
            if(this.allAppointments[i].appointEqual(appoint)){
                continue;
            }else {
                newAppoint[index] = this.allAppointments[i];
                index++;
            }
        }
        this.allAppointments = newAppoint;
    }

    //if such appointment exist
    public boolean hasSuchAppoint(Appointment appoint){
        for(int i = 0; i < this.allAppointments.length; i++){
            if(this.allAppointments[i].appointEqual(appoint)){
                return true;
            }
        }
        return false;
    }

    //get all appointment with input userid string
    public Appointment[] getAppoint(String user){
        Appointment[] newArray = new Appointment[allAppointments.length];
        int index = 0;
        for(int i = 0; i < this.allAppointments.length; i++){
            if(this.allAppointments[i].getPatientID().equals(user)){
                newArray[index] = this.allAppointments[i];
                index++;
            }else if(this.allAppointments[i].getDoctorID().equals(user)){
                newArray[index] = this.allAppointments[i];
                index++;
            }
        }
        if(index == 0){ //no appoint
            Appointment[] returnArray = new Appointment[index];
            System.out.println("Have no appointment.");
            return returnArray;
        }else {
            Appointment[] returnArray = new Appointment[index];
            for(int i = 0; i < returnArray.length; i++){
                returnArray[i] = newArray[i];
            }
            return returnArray;
        }
    }

    //if has time clash with doctor in appointment database
    public boolean hasDocTimeClash(Appointment appoint){
        for(int i = 0; i < this.allAppointments.length; i++){
            if(this.allAppointments[i].docTimeClash(appoint)){
                return true;
            }
        }
        return false;
    }

    //if has time clash with patient in appointment database
    public boolean hasPatTimeClash(Appointment appoint){
        for(int i = 0; i < this.allAppointments.length; i++){
            if(this.allAppointments[i].patTimeClash(appoint)){
                return true;
            }
        }
        return false;
    }

    //delete all appointments of input user
    public void deleteAllAppointOfUser(String UserID){
        Appointment[] currentAppoint = getAppoint(UserID);//all appoint of a user
        Appointment[] finalAppoint = new Appointment[this.allAppointments.length-currentAppoint.length];
        int index1 = 0; //index of all appoint of input user
        int index2 = 0; //index of final appoint

        for(int i = 0; i < this.allAppointments.length; i++){
            if(index2 < currentAppoint.length) {
                if (this.allAppointments[i].appointEqual(currentAppoint[index2])) {
                    index2++;
                    continue;
                } else {
                    finalAppoint[index1] = this.allAppointments[i];
                    index1++;
                }
            }else {
                finalAppoint[index1] = this.allAppointments[i];
                index1++;
            }
        }
        this.allAppointments = finalAppoint;
    }
}
