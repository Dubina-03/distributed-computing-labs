import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class First_flow implements Runnable {
    private int priority;
    private String fileName;

    public First_flow(int priority, String fileName) {
        this.priority = priority;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(priority);
        System.out.println("\nSum start " + fileName);
        long startTime = System.currentTimeMillis();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            int sum = 0;

            while ((line = reader.readLine()) != null) {
                int number = Integer.parseInt(line);
                sum += number;
            }

            reader.close();

            System.out.println("Sum of numbers in " + fileName + ": " + sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("\nSum end " + fileName + " execution time " + executionTime);
    }
}