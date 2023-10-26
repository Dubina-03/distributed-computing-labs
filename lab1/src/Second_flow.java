import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Second_flow implements Runnable{
    private int priority;
    private String fileName;

    public Second_flow(int priority, String fileName) {
        this.priority = priority;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(priority);
        System.out.println("\nWords count start " + fileName);
        long startTime = System.currentTimeMillis();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                System.out.println("Row " + line + " contains " + words.length + " words.");
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("\nWords count end " + fileName + " execution time " + executionTime);
    }
}