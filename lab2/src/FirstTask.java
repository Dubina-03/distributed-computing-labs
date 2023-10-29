import java.util.stream.IntStream;


public class FirstTask{
    public static void main(String[]args) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        IntStream.range(0, numThreads).parallel().
                forEach(threadNum->{
                    System.out.println("Hello World thread"+threadNum);
                });
    }
}
