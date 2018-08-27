/*
 * @author Christos Giannoglou
 * 
 * 2018
 * 
 */
package main.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MP3DurationApp {

  public static void main(String[] args) {
    DurationCalculator dc1 = new DurationCalculator();
    dc1.setBitRates();
    dc1.setSampleRates();
    dc1.setSamples();
    double mp3Duration = dc1.mp3Duration("C:\\Users\\cgiannoglou\\Downloads\\10 Math Games That'll Boost Your Brain Power By 80%.mp3");
    Logger logger = LoggerFactory.getLogger(MP3DurationApp.class);
    logger.info("The duration of the mp3 file is: "+mp3Duration);
  }
}