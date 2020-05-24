package RobotKS;


import java.awt.Robot;
import java.awt.AWTException;


public class Runner {

    public static void main(String[] args) {
        try {
            LoginProgram loginProgram = new LoginProgram(new Robot());

            for(int i = 0; i < 100; i++){
                loginProgram.run();
            }


        } catch (AWTException e) {
            System.out.println(e.getMessage());
        }
    }
}

