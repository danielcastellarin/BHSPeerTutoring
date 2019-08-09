package home;

public class TimeSlot {

    private String day;
    private int startTIme;
    private int endTime;

    public TimeSlot(String day, int start, int end){
        this.day = day;
        startTIme = start;
        endTime = end;
    }

    public String getDay(){
        return day;
    }

    public int getStartTime(){
        return startTIme;
    }

    public int getEndTime(){
        return endTime;
    }

    public void printTimeSlot(){
        System.out.println("Day: " + day + ", Start: " + startTIme + ", End: " + endTime);
    }


}
