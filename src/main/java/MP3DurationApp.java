package main.java;

public class MP3DurationApp {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    DurationCalculator dc = new DurationCalculator();
    dc.setBitRates();
    dc.setSampleRates();
    dc.setSamples();
    long duration = dc.mp3Duration("C:\\Users\\cgiannoglou\\Documents\\Source\\mp3-duration\\tests\\demo - vbr.mp3", false);
    System.out.println("The duration of the mp3 file is: "+duration);
  }

}
