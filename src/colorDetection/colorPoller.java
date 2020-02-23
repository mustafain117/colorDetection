package colorDetection;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class colorPoller {


  /**
   * The ultrasonic sensor.
   */
//  public static final EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S1);
  
  /*
   * The light sensor
   */
    public static final EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S4);
    
    
    static SampleProvider RGBSensor = lightSensor.getRGBMode();  
  

  /**
   * Float array to hold the data returned by the light sensor.
  */

  private static float[] sample = new float[RGBSensor.sampleSize()];
  
  private static double rgb[] = new double[3];

  private static float[] lightValue;
  
  
//    public static void main(String args[]) {
//      
//      new Thread(new Display()).start();
//
//      int buttonChoice = Button.waitForAnyPress();
//            
//      while(buttonChoice != Button.ID_ESCAPE) {
//        getRGBvalues();
//      }
//    }
    
    public static void  getRGBvalues() {
      RGBSensor.fetchSample(sample, 0);
      rgb[0] = sample[0];
      rgb[1] = sample[1];
      rgb[2] = sample[2];
    }

    public static double[] getRgb() {
      return rgb;
    }
    
    public static float getLightVal() {
      lightSensor.getRedMode().fetchSample(lightValue , 0);
      return lightValue[0] * 100;
    }
}
