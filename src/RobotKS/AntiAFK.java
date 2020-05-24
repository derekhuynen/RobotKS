package RobotKS;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AntiAFK {

    private Robot robot;
    private int min = 40000;
    private int max = 60000; //60000 is max (1min)
    private int timeLoop = 8;

    private int random;
    private int randomKS;
    private String movementType;

    //Key press Timing
    private static final int MIN_KS = 100;
    private static final int MAX_KS = 200; //60000 is max (1min)

    //Date formatter
    private SimpleDateFormat formatter= new SimpleDateFormat("MM-dd-yyyy 'at' hh:mm:ss aa z");


    public AntiAFK(Robot robot) {
            this.robot = robot;
    }

    public AntiAFK(Robot robot, int min, int max, int timeLoop) {
            this.robot = robot;
            this.min = min;
            this.max = max;
            this.timeLoop = timeLoop;
    }

    public void runRobot(int counter){
        this.random = (int)(Math.random() * ((this.max - this.min) + 1)) + this.min;
        this.randomKS = (int)(Math.random() * ((MAX_KS - MIN_KS) + 1)) + MIN_KS;

        setMovement();
        printBefore(counter);
        for (int i = 0; i < timeLoop; i++) {
            this.robot.delay(this.random);
        }
        movement();
        printAfter();
    }

    public void setMovement(){
        if(random%5 == 0) {
            this.movementType = "WS";
        }
        else if(random%4 == 0) {
            this.movementType = "QE";
        }
        else if(random%3 == 0) {
            this.movementType = "Turn Right";
        }
        else if(random%2 == 0) {
            movementType = "Turn Left";
        }else {
            this.movementType = "Jump";
        }
    }

    public void movement(){
        switch (this.movementType) {
            case "WS":
                robot.keyPress(KeyEvent.VK_W);
                robot.delay(randomKS);
                robot.keyRelease(KeyEvent.VK_W);
                robot.delay(random); //longer
                robot.keyPress(KeyEvent.VK_S);
                robot.delay(randomKS);
                robot.keyRelease(KeyEvent.VK_S);
                break;
            case "QE":
                robot.keyPress(KeyEvent.VK_Q);
                robot.delay(randomKS);
                robot.keyRelease(KeyEvent.VK_Q);
                robot.delay(random); //longer
                robot.keyPress(KeyEvent.VK_E);
                robot.delay(randomKS);
                robot.keyRelease(KeyEvent.VK_E);
                break;
            case "Turn Right":
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.delay(randomKS);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                break;
            case "Turn Left":
                robot.keyPress(KeyEvent.VK_LEFT);
                robot.delay(randomKS);
                robot.keyRelease(KeyEvent.VK_LEFT);
                break;
            default:
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.delay(randomKS);
                robot.keyRelease(KeyEvent.VK_SPACE);
                break;
        }
    }

    public void printBefore(int counter){
        int printTime = this.random * this.timeLoop;
        System.out.printf("%d. Delay: %.2f minutes - KeyPress: %d ms\n",counter,(double)printTime/60000,randomKS);
        Date date = new Date(System.currentTimeMillis() + printTime);
        System.out.printf("Next %s movement at: %s\n",this.movementType,this.formatter.format(date));
    }

    public void printAfter(){
        Date date = new Date(System.currentTimeMillis());
        System.out.printf("%s movement at %s\n\n",this.movementType, this.formatter.format(date));
    }
}
