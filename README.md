# mp3CalculateDuration
public static void main(String[] args) {
    DurationCalculator1 dc1 = new DurationCalculator1();
    dc1.setBitRates();
    dc1.setSampleRates();
    dc1.setSamples();
    double mp3Duration = dc1.mp3Duration(pathToFile);
    System.out.println("The duration of the mp3 file is: "+mp3Duration);
  }