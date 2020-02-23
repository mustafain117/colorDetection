package colorDetection;
import static colorDetection.Resources.*;

public class ColorDetector implements Runnable{

  private static final long COLOUR_PERIOD = 1000;
  private long timeout = Long.MAX_VALUE;

  //local variables for 
  public  float colourRed;
  public  float colourGreen;
  public  float colourBlue;
  //for some reason this sampleProvider does not work.
  //private static SampleProvider colourValue = FRONT_COL_SENSOR.getRGBMode(); 
  /**
   * Buffer (array) to store light samples.
   */
  static float[] colourData = new float[3];

  /**
   * Constructor.
   */
  public ColorDetector(){
  }

  private static String color;
  public enum COLOUR {
    BLUE, GREEN, YELLOW, ORANGE, NONE;
  }

  public  COLOUR ringColour;

  /**
   * This method will localize the robot using light sensor, start the thread.
   */

  public void run() {
    long updateStart;
    long updateDuration;
    updateStart = System.currentTimeMillis();
    while (true) {
      lightSensor.getRGBMode().fetchSample(colourData, 0);
      colourRed = colourData[0];
      colourGreen = colourData[1];
      colourBlue = colourData[2];
      
      float normRed = (float) ((colourRed)/Math.sqrt(colourRed*colourRed+colourGreen*colourGreen+colourBlue*colourBlue));
      float normGreen = (float) ((colourGreen)/Math.sqrt(colourRed*colourRed+colourGreen*colourGreen+colourBlue*colourBlue));
      float normBlue = (float) ((colourBlue)/Math.sqrt(colourRed*colourRed+colourGreen*colourGreen+colourBlue*colourBlue));
      
      updateRingColour(normRed, normGreen, normBlue);
      // this ensures that the light sensor runs once every 3 ms.
      updateDuration = System.currentTimeMillis() - updateStart;
      if (updateDuration < COLOUR_PERIOD) {
        try {
          Thread.sleep(COLOUR_PERIOD - updateDuration);
        }catch(InterruptedException e) {
          
        }
      }
    }
  }


  public  COLOUR updateRingColour(double colourRed, double colourGreen, double colourBlue) {
    this.ringColour = COLOUR.NONE;
    
    int stdDevs = 40;
    //for orange and yellow;
    int stDevsOY = 30;
    
    if ((colourBlue >= GREEN_MEAN[2] - stdDevs* GREEN_SD[2]) & (colourBlue <= GREEN_MEAN[2] + stdDevs*GREEN_SD[2])) {
//      System.out.println("g1\n");
      if ((colourRed >= GREEN_MEAN[0] - stdDevs*GREEN_SD[0]) & (colourRed <= GREEN_MEAN[0] + stdDevs*GREEN_SD[0])) {
//        System.out.println("g2\n");
        if ((colourGreen >= GREEN_MEAN[1] - stdDevs*GREEN_SD[1]) & (colourGreen <= GREEN_MEAN[1] +stdDevs* GREEN_SD[1])) {
//          System.out.println("g3\n");
          this.ringColour = COLOUR.GREEN;
        }
      }
    } 

    if ((colourBlue >= BLUE_MEAN[2] - stdDevs*BLUE_SD[2]) & (colourBlue <= BLUE_MEAN[2] +stdDevs* BLUE_SD[2])) {
      //Printer.printMessage("b1\n", 2);
//      System.out.println("b1\n");
      if ((colourRed >= BLUE_MEAN[0] -stdDevs* BLUE_SD[0]) & (colourRed <= BLUE_MEAN[0] +stdDevs* BLUE_SD[0])) {
        //Printer.printMessage("b2\n", 3);
//        System.out.println("b2\n");
        if ((colourGreen >= BLUE_MEAN[1] - stdDevs*BLUE_SD[1]) & (colourGreen <= BLUE_MEAN[1] +stdDevs* BLUE_SD[1])) {
          //Printer.printMessage("b3\n", 4);
//          System.out.println("b3\n");
          this.ringColour = COLOUR.BLUE;
        }
      }
    } 
    

    if ((colourBlue >= YELLOW_MEAN[2] - stDevsOY*YELLOW_SD[2]) & (colourBlue <= YELLOW_MEAN[2] + stDevsOY*YELLOW_SD[2])) {
//      System.out.println("y1\n");
      if ((colourRed >= YELLOW_MEAN[0] - stDevsOY*YELLOW_SD[0]) & (colourRed <= YELLOW_MEAN[0] + stDevsOY*YELLOW_SD[0])) {
//        System.out.println("y2\n");
        if ((colourGreen >= YELLOW_MEAN[1] - stDevsOY*YELLOW_SD[1]) & (colourGreen <= YELLOW_MEAN[1] +stDevsOY* YELLOW_SD[1])) {
//          System.out.println("y3\n");
          this.ringColour = COLOUR.YELLOW;
        }
      }

    }  
    if ((colourBlue >= ORANGE_MEAN[2] - stdDevs*ORANGE_SD[2]) & (colourBlue <= ORANGE_MEAN[2] + stdDevs*ORANGE_SD[2])) {
//      System.out.println("o1\n");
      if ((colourRed >= ORANGE_MEAN[0] - stdDevs*ORANGE_SD[0]) & (colourRed <= ORANGE_MEAN[0] + stdDevs*ORANGE_SD[0])) {
//        System.out.println("o2\n");
        if ((colourGreen >= ORANGE_MEAN[1] - stdDevs*ORANGE_SD[1]) & (colourGreen <= ORANGE_MEAN[1] + stdDevs*ORANGE_SD[1])) {
//          System.out.println("o3\n");
          this.ringColour = COLOUR.ORANGE;
        }
      }
    }
    this.color = this.ringColour + "";
    return this.ringColour;

  }


  public static String getColor() {
    return color;
  }


}