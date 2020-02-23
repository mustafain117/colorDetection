package colorDetection;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class Resources {
  /**
   * The ultrasonic sensor.
   */
  public static final EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S1);
  
  public static final EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S4);
  
  /**
   * The left motor.
   */
  public static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);

  /**
   * The right motor.
   */
  public static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
  /**
   * The odometer.
   */
  public static Odometer odometer = Odometer.getOdometer();
  /**
   * The wheel radius in centimeters.
   */
  public static final double WHEEL_RAD = 2.130;

  /**
   * The robot width in centimeters.
   */
  public static final double BASE_WIDTH = 15.29;
  /**
   * Speed of slower rotating wheel (deg/sec). Used during ultrasonic
   * localization.
   */
  public static final int MOTOR_LOW = 50;
  /**
   * Speed of the faster rotating wheel (deg/sec).
   */
  public static final int MOTOR_HIGH = 200;
  /**
   * The limit of invalid samples that we read from the US sensor before assuming no obstacle.
   */
  public static final int INVALID_SAMPLE_LIMIT = 25;
  

  /**
   * The tile size in centimeters. Note that 30.48 cm = 1 ft.
   */
  public static final double TILE_SIZE = 30.48;

//******** COLOR RESOURCES ***********
  
  /**
   * Blue mean RGB values
   */
  public static final double[] BLUE_MEAN = {0.248570263,0.75230169,0.610127029};
  
  /**
   * Blue standard deviation RGB values
   */
  public static final double[] BLUE_SD = {0.00278066, 0.007794649,0.005694158};
  
  /**
   * Green mean RGB values
   */
  public static final double[] GREEN_MEAN = {0.504894489,0.854782753,0.120116607};
  
  /**
   * Green standard deviation RGB values
   */
  public static final double[] GREEN_SD = {0.00288681,0.004807366,0.001653488};
  
  /**
   * Yellow mean RGB values
   */
  public static final double[] YELLOW_MEAN = {0.870748263,0.482106504,0.096802795};
  
  /**
   * Yellow standard deviation RGB values
   */
  public static final double[] YELLOW_SD = {0.009740534,0.003502987,0.002403683};
  
  /**
   * Orange mean RGB values
   */
  public static final double[] ORANGE_MEAN = {0.961721271, 0.264355713, 0.072168237};
  
  /**
   * Orange standard deviation RGB values
   */
  public static final double[] ORANGE_SD = {0.005720356, 0.001136768, 0.001012523};
}
