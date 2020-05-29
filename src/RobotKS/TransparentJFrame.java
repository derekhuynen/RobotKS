package RobotKS;


import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;


public class TransparentJFrame {

    private Point point;
    private Robot robot;
    private String configFile = "configuration.txt";

    public TransparentJFrame(Robot robot){
        this.robot = robot;
        readFile();

    }

    public void transparentWindow(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.add(panel);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                point = new Point(e.getX(),e.getY());
                System.out.println("Point Saved:     X: " + point.getX() + " Y: " + point.getY());
                frame.dispose();
                WriteToFile();
                runLoginLogic();
            }
        });

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setOpacity(0.3f);
    }

    public Point getPoint() {
        return this.point;
    }

    public void WriteToFile(){
        try(FileWriter fileWriter = new FileWriter(this.configFile)) {
            String fileContent = "Play Button: X: " + this.point.x + " Y: " + this.point.getY();
            fileWriter.write(fileContent);
        } catch (IOException e) {
            // Cxception handling
        }
    }

    public void readFile(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile))) {
            String line = bufferedReader.readLine();
            while(line != null){

                if(line.contains("Play Button:")){
                    double x = Double.parseDouble(line.substring(line.indexOf("X") + 3, line.indexOf("Y") -1));
                    double y = Double.parseDouble(line.substring(line.indexOf("Y") + 3));
                    System.out.println("X: " + x + "  Y: " + y);
                    this.point = new Point((int)x,(int)y);
                    runLoginLogic();
                }
                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            transparentWindow();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void runLoginLogic(){
            LoginProgram loginProgram = new LoginProgram(robot, this.point);

            for(int i = 0; i < 100; i++){
                loginProgram.run();
            }
    }
}

