import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Third_flow implements Runnable {
    private int priority;
    private String fileName;

    public Third_flow(int priority, String fileName) {
        this.priority = priority;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(priority);
        System.out.println("\nRandom Line start " + fileName);
        long startTime = System.currentTimeMillis();
        try {
            int totalLines = countLines(fileName);
            if (totalLines > 0) {
                String randomLine = getRandomLine(fileName, totalLines);
                System.out.println("Randomly selected line:\n" + randomLine);
            } else {
                System.out.println("The file is empty.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("\nRandom Line end " + fileName + " execution time " + executionTime);
    }

    private static int countLines(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int lineCount = 0;

        while (reader.readLine() != null) {
            lineCount++;
        }

        reader.close();
        return lineCount;
    }

    private static String getRandomLine(String filePath, int totalLines) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        Random random = new Random();
        int randomLineIndex = random.nextInt(totalLines);

        String randomLine = null;
        for (int i = 0; i <= randomLineIndex; i++) {
            randomLine = reader.readLine();
        }

        reader.close();
        return randomLine;
    }

}
