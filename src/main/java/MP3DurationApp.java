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
    double mp3Duration = dc1.mp3Duration("C:\\Users\\cgiannoglou\\Downloads\\Iron Maiden - Fear of the Dark, live Tele2 Arena, Stockholm Sweden 2018-06-01.mp3");
    System.out.println("The duration of the mp3 file is: "+mp3Duration);
  }
}