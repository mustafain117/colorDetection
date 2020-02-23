package colorDetection;


import static colorDetection.Resources.*;
import lejos.hardware.Button;

public class Main {
  private static ColorDetector carectorizer;

  public static void main(String[] args) {
    Display.test();
    int buttonChoice;
    buttonChoice = Button.waitForAnyPress();
    if(buttonChoice == Button.ID_LEFT) {
//      carectorizer = new ColorDetector();
//      carectorizer.run();
      
      new Thread(odometer).start(); // start the odometer
     // new Thread(new Display()).start();
      odometer.setXyt(TILE_SIZE, TILE_SIZE,0);
      new Thread(new ColorDetector()).start();
      new Thread(new Navigation()).start();
      
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
      
//      Button.waitForAnyPress();
//      System.exit(0);
      }
    
  }
}
