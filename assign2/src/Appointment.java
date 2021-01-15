/**
 * The Appointment class presents a appointment object
 */

public class Appointment {
    private String doctorID;    //doctor id of a appointment
    private String patientID;   //patient id of a appointment
    private TimeSlot timeSlot;  //timeslot of a appointment

    public Appointment(){
        this.doctorID = "";
        this.patientID = "";
        this.timeSlot = new TimeSlot();
    }

    /**
     * Constructor to create a appointment with specified doctorID, patientID and timeslot
     * @param doctorID  the doctor id of the appointment
     * @param patientID the patient id of the appointment
     * @param timeSlot  the timeslot of a appointment
     */

    public Appointment(String doctorID, String patientID, TimeSlot timeSlot){
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.timeSlot = timeSlot;
    }

    //to string function
    public String toString(){
        return this.doctorID + " " + this.patientID + " " + this.timeSlot;
    }

    //get doctor ID
    public String getDoctorID(){
        return this.doctorID;
    }

    //get patientID
    public String getPatientID(){
        return this.patientID;
    }

    //get timeslot string
    public String getTimeslot(){
        return this.timeSlot+"";
    }

    //get timeslot Timeslot
    public TimeSlot getSpeTimeSlot(){
        return this.timeSlot;
    }

    //if have time clash with patient
    public boolean patTimeClash(Appointment appoint){
        if(this.patientID.equals(appoint.patientID) && this.timeSlot.equalTimeslot(appoint.timeSlot)){
            return true;
        }else {
            return false;
        }
    }

    //if have time clash with doctor
    public boolean docTimeClash(Appointment appoint){
        if(this.doctorID.equals(appoint.doctorID) && this.timeSlot.equalTimeslot(appoint.timeSlot)){
            return true;
        }else {
            return false;
        }
    }

    //if has such appointment
    public boolean hasAppoint(User user){
        if(user.getUserID().equals(this.patientID) || user.getUserID().equals(this.doctorID)){
            return true;
        }else {
            return false;
        }
    }

    //if two appointments equal
    public boolean appointEqual(Appointment appoint){
        if(this.doctorID.equals(appoint.getDoctorID()) && this.patientID.equals(appoint.getPatientID())
        && this.timeSlot.equalTimeslot(appoint.getSpeTimeSlot())){
            return true;
        }
        return false;
    }




}
