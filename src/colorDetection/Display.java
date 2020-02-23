package colorDetection;

import static colorDetection.Resources.*;
import java.text.DecimalFormat;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;

public class Display implements Runnable{
  /**
   * The LCD screen used for displaying text.
   */
  public static final TextLCD TEXT_LCD = LocalEV3.get().getTextLCD();

  volatile boolean exit = false; 

  public void run() {
    while (!exit) { // operates continuously
      TEXT_LCD.clear();

      // print last US reading
      //  TEXT_LCD.drawString("US Distance: " + localizer.readUsDistance(), 0, 1);
     
      //rotation angle used for navigation to a point
    //  double angle =Navigation.getRotationAngle();
      // Retrieve x, y and Theta information
      double[] position = odometer.getXyt();
      position[2] = (position[2] * 180.0 / 3.14159);

      // Print x,y,theta, and angle information
      DecimalFormat numberFormat = new DecimalFormat("######0.00");
      TEXT_LCD.drawString("X: " + numberFormat.format(position[0]), 0, 2);
      TEXT_LCD.drawString("Y: " + numberFormat.format(position[1]), 0, 3);
      TEXT_LCD.drawString("T: " + numberFormat.format(position[2]), 0, 4);
      //TEXT_LCD.drawString("Angle: " +angle, 0, 5);

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void test() {
   TEXT_LCD.clear();
   TEXT_LCD.drawString("<<<< Test", 0, 0);
    
  }
}
