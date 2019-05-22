package home;

public class TimeSlot {

    private String day;
    private String startTIme;
    private String endTime;

    public TimeSlot(String day, String start, String end){
        this.day = day;
        startTIme = start;
        endTime = end;
    }

    public String getDay(){
        return day;
    }

    public String getStartTIme(){
        return startTIme;
    }

    public String getEndTime(){
        return endTime;
    }

    public void printTimeSlot(){
        System.out.println("Day: " + day + ", Start: " + startTIme + ", End: " + endTime);
    }
}
