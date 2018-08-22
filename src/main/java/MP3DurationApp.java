/*
 * @author Christos Giannoglou
 * 
 * 2018 (c) ATC
 * 
 */
package main.java;

public class MP3DurationApp {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    DurationCalculator dc1 = new DurationCalculator();
    dc1.setBitRates();
    dc1.setSampleRates();
    dc1.setSamples();
    double mp3Duration = dc1.mp3Duration("C:\\Users\\cgiannoglou\\Documents\\Source\\mp3-duration\\tests\\demo - vbr.mp3");
    System.out.println("The duration of the mp3 file is: "+mp3Duration);
  }
}