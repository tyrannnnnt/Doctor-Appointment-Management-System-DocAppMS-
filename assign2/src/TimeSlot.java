/**
 * The Appointment class presents a TimeSlot object
 */

public class TimeSlot {
    private char day;
    private String hour;

    public TimeSlot(){
        this.day = ' ';
        this.hour = "";
    }

    /**
     * Constructor to create a TimeSlot with specified day and hour
     * @param day   the day of a timeslot
     * @param hour  the hour of a timeslot
     */
    public TimeSlot(char day, String hour){
        this.day = day;
        this.hour = hour;
    }

    public TimeSlot(String timeStr){
        this.day = timeStr.charAt(0);
        int num = 0;
        for(int i = 1; i < timeStr.length(); i++){
            num = num *10 + (timeStr.charAt(i)-'0');
        }
        this.hour = num+"";
    }

    //set day
    public void setDay(char day){
        this.day = day;
    }

    //set hour
    public void setHour(String hour){
        this.hour = hour;
    }

    //get hour
    public String getHour(){
        if(this.hour.length() == 3) {
            this.hour = ((this.hour.charAt(1) - '0')*10 + this.hour.charAt(2) - '0') + "";
        }
        return this.hour;
    }

    //get day character
    public char getDay(){
        return this.day;
    }

    //to string method
    public String toString(){
        int num = 0;
        for(int i = 0; i < this.hour.length(); i++){
            num = num *10 + (this.hour.charAt(i)-'0');
        }
        String outputStr = num+"";
        if(outputStr.length() == 1){
            outputStr = '0'+outputStr;
        }
        return this.day + outputStr;
    }

    //if two timeslot equal
    public boolean equalTimeslot(TimeSlot time){
        if(this.day == time.day && this.hour.equals(time.hour)){
            return true;
        }else {
            return false;
        }
    }

}
