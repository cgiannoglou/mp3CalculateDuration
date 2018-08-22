package test.java;

import static org.junit.Assert.*;
import org.junit.Test;
import main.java.DurationCalculator;

public class DurationTest {

  @Test
  public void demoVBRtest() {
    DurationCalculator dc1 = new DurationCalculator();
    dc1.setBitRates();
    dc1.setSampleRates();
    dc1.setSamples();
    double mp3Duration = dc1.mp3Duration("C:\\Users\\cgiannoglou\\Documents\\Source\\mp3-duration\\tests\\demo - vbr.mp3");
    assertTrue(mp3Duration == 285.701 || mp3Duration == 285.702 || mp3Duration == 285.703);
  }

}
