package RobotKS;

import javax.imageio.ImageIO;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;

public class ImageStuff {
    private File original;
    private File originalCrop;
    private File screenshot;
    private Robot robot;


    public ImageStuff(Robot robot, String original) {
        this.original = new File(original);
        this.originalCrop = cropImage(new File(original));
        this.robot = robot;
    }

    public ImageStuff(String original, String screenshot) {
        this.original = new File(original);
        this.screenshot = new File(screenshot);
        this.originalCrop = cropImage(new File(original));
    }

    public void calculate(boolean crop) {

        double total = 0;
        double largest = 0;
        double smallest = 0;
        double average = 0;
        int counter = 0;

        for (int i = 0; i < 10; i++) {

            double current = compareImage(crop);

            smallest = smallest == 0 ? current : Math.min(smallest, current);
            largest = largest == 0 ? current : Math.max(largest, current);
            average = average == 0 ? current : (average + current) / 2;

            total += current;
            counter += 1;

            System.out.printf("Smallest: %f, Largest: %f, Current: %f, Average: %f\n", smallest, largest, current, average);
        }
        System.out.println("Done!");

        System.out.println("Count: " + counter);
        System.out.println("Final Average: " + total / counter);

    }

    public void screenShot() {
        try {
            Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage bufferedImage1 = this.robot.createScreenCapture(rectangle);

            //Write to File
            File file = new File("screenShot"  + ".png");
            ImageIO.write(bufferedImage1, "png", file);
            this.screenshot = file;

        } catch ( IOException ex) {
            System.err.println(ex);
        }
    }

    public double compareImage(boolean crop) {

        double percentage = 0;
        screenShot();
        try {
            // take buffer data from both image files //

            BufferedImage biA = ImageIO.read(this.original);
            BufferedImage biB = ImageIO.read(this.screenshot);
            if(crop){
                biA = ImageIO.read(this.originalCrop);
                biB = ImageIO.read(cropImage(this.screenshot));
            }
            DataBuffer dbA = biA.getData().getDataBuffer();
            DataBuffer dbB = biB.getData().getDataBuffer();

            float sizeA = dbA.getSize();
            int count = 0;
            // compare data-buffer objects //
            if (sizeA == dbB.getSize()) {
                for (int i = 0; i < sizeA; i++) {
                    if (dbA.getElem(i) == dbB.getElem(i))
                        count += 1;
                }
                percentage = (count * 100) / sizeA;

                System.out.println("Percentage: " + percentage +"% similar.");
            } else {
                System.out.println("Both the images are not of same size");
            }
        } catch (Exception e) {
            System.out.println("Failed to compare image files ...");
        }
        return percentage;
    }



    public File cropImage(File file) {
            File copy = new File(file.getName().replace(".png","Crop.png"));

        try {
            BufferedImage image = ImageIO.read(file);
            BufferedImage imageCrop = image.getSubimage(0, 0, 200, 50);

            ImageIO.write(imageCrop, "png", copy);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return copy;
    }

    public void openImage(File file1) throws IOException {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file1);
    }

    public File getOriginal() {
        return this.original;
    }

    public File getScreenshot(){
        return this.screenshot;
    }
}
