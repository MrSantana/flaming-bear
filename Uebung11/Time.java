public class Time {
  private int hour;
  private int min;
  
  public Time(){
    this.hour=0;
    this.min=0;
  }
  
  public Time(int hour, int min){
    this.hour=hour;
    this.min=min;
  }
  
  public int getHour() {
    return hour;
  }
  public void setHour(int hour) {
    this.hour = hour;
  }
  public int getMin() {
    return min;
  }
  public void setMin(int min) {
    this.min = min;
  }
}