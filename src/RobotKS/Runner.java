package RobotKS;

import java.awt.Robot;
import java.awt.AWTException;

public class Runner {

    public static void main(String[] args) {

        System.out.println("Please Open Battle.net");
        try{

            Robot robot =  new Robot();
            robot.delay(10000);
            new TransparentJFrame(robot);

        }catch (AWTException e){
            e.getLocalizedMessage();
        }
    }
}

