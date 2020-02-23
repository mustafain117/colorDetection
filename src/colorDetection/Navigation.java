package colorDetection;

import static colorDetection.Resources.*;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class Navigation implements Runnable{
  /**
   * The distance remembered by the {@code filter()} method.
   */
  private int prevDistance;
  
  /**
   * The number of invalid samples seen by {@code filter()} so far.
   */
  private int invalidSampleCount;
  
  //private static int[][] map1 = new int[][] {{1,4}, {4,7} , {4,1}, {7,4},{7,7}};
  private static int[][] map1 = new int[][] {{1,3},{2,2},{3,3},{3,2},{2,1}};
  /**
   * Buffer (array) to store US samples. Declared as an instance variable to avoid creating a new
   * array each time {@code readUsSample()} is called.
   */
  private float[] usData = new float[usSensor.sampleSize()];
  
  private double odoValues[] = new double[3];   // array to hold odometer values

  private double rotationAngle;

 public Navigation() {
 }
 @Override
 public void run() {
   usData[0]=(float) 100.0;
   // TODO Auto-generated method stub
   startNavigation();
   
 }
 public void startNavigation() {
  for(int i = 0 ; i < map1.length ; i++) {
     travelTo(map1[i][0], map1[i][1]);
     Sound.beepSequenceUp();
   }
 }
 
 /**
  * Calculates the minimum angle needed to correct the robot's heading.
  * @param theta Target angle to turn to
  */
 private void turnTo(double theta) {
   rotationAngle = theta - odoValues[2];
   rotationAngle = Math.toDegrees(rotationAngle);
   
   ///ensures shortest angle is used
   if(rotationAngle > 180) {
     rotationAngle = 360 - rotationAngle;
   }else if(rotationAngle < -180) {
     rotationAngle = 360 + rotationAngle;
   }
   Sound.beep();
   int nav_turn_error = -1;
   // correct the heading
   leftMotor.rotate(convertAngle(rotationAngle + nav_turn_error),true);
   rightMotor.rotate(-convertAngle(rotationAngle + nav_turn_error),false);
 }
 
 private void travelTo(double x, double y) {
   //convert map coordinates to centimetres
    x = x * TILE_SIZE;
    y = y * TILE_SIZE;
    
    odoValues = odometer.getXyt();
    //subtracting odometer  x and y values to estimate x and y distance needed
    double distX = x - odoValues[0]; 
    double distY = y - odoValues[1];
    
    double theta = Math.atan2(distX, distY);
    
   // Sound.beep();
    leftMotor.setSpeed(MOTOR_LOW);
    rightMotor.setSpeed(MOTOR_LOW);
    turnTo(theta);
    
    double hypotenuse = Math.sqrt(distY*distY  + distX*distX);
    double currX = odoValues[0];
    double currY = odoValues[1];
    double distanceTravelled = 0;
    while((Math.abs(odometer.getXyt()[0]-x)>=2 || Math.abs(odometer.getXyt()[1]-y)>=2) && distanceTravelled <= hypotenuse ) {
      leftMotor.setSpeed(MOTOR_LOW);
      rightMotor.setSpeed(MOTOR_LOW);
      rightMotor.forward();
      leftMotor.forward();
      
      if(readUsDistance() <= 15 && readUsDistance()>0 ) {
        leftMotor.setSpeed(30);
        rightMotor.setSpeed(30);
        rightMotor.forward();
        leftMotor.forward();
      }
      
      if(readUsDistance()<=3 && readUsDistance()>0) {
        rightMotor.stop();
        leftMotor.stop();
        System.out.println("\n"+ ColorDetector.getColor());
        Button.waitForAnyPress();
      } 
      
      double xNew =  odometer.getXyt()[0];
      double yNew = odometer.getXyt()[1];
      distanceTravelled = Math.sqrt((xNew-currX)*(xNew-currX) + (yNew-currY)*(yNew-currY));
//      int leftMotorTachoCount = leftMotor.getTachoCount();
//      int rightMotorTachoCount = rightMotor.getTachoCount();
//      double distL = Math.PI * WHEEL_RAD * (leftMotorTachoCount - lastTachoL) / 180;
//      double distR = Math.PI * WHEEL_RAD * (rightMotorTachoCount - lastTachoR) / 180;
    }
  rightMotor.stop();
  leftMotor.stop();
//  double hypotenuse = Math.sqrt(distY*distY  + distX*distX);
//    
//    leftMotor.setSpeed(MOTOR_HIGH);
//    rightMotor.setSpeed(MOTOR_HIGH);
    
//    leftMotor.rotate(convertDistance(hypotenuse), true);
//    rightMotor.rotate(convertDistance(hypotenuse), false);
 }
 /**
  * Returns the filtered distance between the US sensor and an obstacle in cm.
  * 
  * @return the filtered distance between the US sensor and an obstacle in cm
  */
 public int readUsDistance() {
   usSensor.fetchSample(usData, 0);
   // extract from buffer, convert to cm, cast to int, and filter
   return filter((int) (usData[0] * 100.0));
 }
 
 /**
  * Rudimentary filter - toss out invalid samples corresponding to null signal.
  * 
  * @param distance raw distance measured by the sensor in cm
  * @return the filtered distance in cm
  */
 int filter(int distance) {
   if (distance >= 255 && invalidSampleCount < INVALID_SAMPLE_LIMIT) {
     // bad value, increment the filter value and return the distance remembered from before
     invalidSampleCount++;
     return prevDistance;
   } else {
     if (distance < 255) {
       // distance went below 255: reset filter and remember the input distance.
       invalidSampleCount = 0;
     }
     prevDistance = distance;
     return distance;
   }
 }

 /**
  * Converts input distance to the total rotation of each wheel needed to cover that distance.
  * 
  * @param distance the input distance
  * @return the wheel rotations necessary to cover the distance
  */
 public static int convertDistance(double distance) {
   return (int) ((180.0 * distance) / (Math.PI * WHEEL_RAD));
 }

 /**
  * Converts input angle to the total rotation of each wheel needed to rotate 
  * the robot by that angle.
  * 
  * @param angle the input angle
  * @return the wheel rotations necessary to rotate the robot by the angle
  */
 public static int convertAngle(double angle) {
   return convertDistance(Math.PI * BASE_WIDTH * angle / 360.0);
 }
}
