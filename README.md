# mp3CalculateDuration

This is a Java program  which is about calculating mp3 files duration in milliseconds

## Usage

public static void main(String[] args) {  
	DurationCalculator dc = new DurationCalculator();  
	dc.setBitRates();  
	dc.setSampleRates();  
	dc.setSamples();  
	double mp3Duration = dc.mp3Duration(pathToFile);  
	System.out.println("The duration of the mp3 file is: "+mp3Duration);  
}
