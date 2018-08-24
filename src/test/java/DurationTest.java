package test.java;

import static org.junit.Assert.*;
import org.junit.Test;
import main.java.DurationCalculator;

public class DurationTest {

  @Test
  public void demoVBRTest() {
    DurationCalculator dc1 = new DurationCalculator();
    dc1.setBitRates();
    dc1.setSampleRates();
    dc1.setSamples();
    double mp3Duration = dc1.mp3Duration("C:\\Users\\cgiannoglou\\Documents\\Source\\mp3-duration\\tests\\demo - vbr.mp3");
    assertTrue(mp3Duration == 285.701 || mp3Duration == 285.702 || mp3Duration == 285.703);
  }

  @Test
  public void mathRiddlesTest() {
    DurationCalculator dc1 = new DurationCalculator();
    dc1.setBitRates();
    dc1.setSampleRates();
    dc1.setSamples();
    double mp3Duration = dc1.mp3Duration("C:\\Users\\cgiannoglou\\Downloads\\9 Math Riddles That'll Stump Even Your Smartest Friends.mp3");
    assertTrue(mp3Duration == 400.090 || mp3Duration == 400.091 || mp3Duration == 400.092);
  }
  
  @Test
  public void mathGamesTest() {
    DurationCalculator dc1 = new DurationCalculator();
    dc1.setBitRates();
    dc1.setSampleRates();
    dc1.setSamples();
    double mp3Duration = dc1.mp3Duration("C:\\Users\\cgiannoglou\\Downloads\\10 Math Games That'll Boost Your Brain Power By 80%.mp3");
    assertTrue(mp3Duration == 681.194 || mp3Duration == 681.195 || mp3Duration == 681.196);
  }
}
