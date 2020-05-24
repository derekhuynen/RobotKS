package RobotKS;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginProgram {

    private Robot robot;
    private SimpleDateFormat formatter= new SimpleDateFormat("hh:mm:ss aa");

    public LoginProgram(Robot robot) {
        this.robot = robot;
    }

    public void run() {
        System.out.println("Place Mouse on Play Button. Waiting 20 seconds");
        this.robot.delay(20000);

        //Press enter to login
        System.out.println("Clicking the Play Button.");
        this.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        this.robot.delay(50);
        this.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        shortDelay(20000,"Game Screen to Load");

        //Check if you are in Queue
        ImageStuff imageStuff = new ImageStuff(robot ,"WowQueue.png");
        System.out.println("Comparing Images to see which Screen you are on.");

        while (imageStuff.compareImage(true) > 90) {
            System.out.println("You are currently in Queue");
            longerDelay(2);
        }

        System.out.println("You are in Character Screen");
        longerDelay(2);


        //Press enter to login
        System.out.println("Pressing Enter to Enter Game");
        this.robot.keyPress(KeyEvent.VK_ENTER);
        this.robot.delay(50);
        this.robot.keyRelease(KeyEvent.VK_ENTER);

        //Make a beep noise
        Toolkit.getDefaultToolkit().beep(); //Dont really like

        //OPTION run anti afk
        System.out.println("\nStarting AntiAFK");
        looper(new AntiAFK(robot, 20000, 40000, 1),5);

        //Press Alt+F4
        System.out.println("Pressing Alt+F4 to exit Game");
        this.robot.keyPress(KeyEvent.VK_ALT);
        this.robot.delay(50);
        this.robot.keyPress(KeyEvent.VK_F4);
        this.robot.delay(50);
        this.robot.keyRelease(KeyEvent.VK_F4);
        this.robot.delay(50);
        this.robot.keyRelease(KeyEvent.VK_ALT);

        System.out.println("\nRestarting Log-in Application");
    }


    public static void looper(AntiAFK myRobot,int durationLoop) {
        for (int i = 1; i < durationLoop; i++) {
            myRobot.runRobot(i);
        }
    }

    public void shortDelay(int delay, String reason){
        System.out.printf("%s: Waiting %.2f seconds for %s. %s\n",
                this.formatter.format(new Date(System.currentTimeMillis())),
                (double) delay/1000,
                reason,
                this.formatter.format(new Date(System.currentTimeMillis() + delay)));
        this.robot.delay(delay);
    }

    public void longerDelay(int duration){
        System.out.printf("%s Delay: %d minutes %s\n",
                this.formatter.format(new Date(System.currentTimeMillis())),
                duration,
                this.formatter.format(new Date(System.currentTimeMillis() + (duration*60000))));
        for (int i = 0; i < duration; i++) {
            this.robot.delay(60000); //1min
        }
    }
}

