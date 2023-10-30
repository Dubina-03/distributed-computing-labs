import classes.Function;
import classes.IntegralCalculation;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;


public class Main {
    private static final double a = 0, b = 1;

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("#.#############");
        JFrame frame = new JFrame("Integration Calculator");

        JTextField stepsField = new JTextField(10);
        JTextField threadsField = new JTextField(10);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int steps;
                int threads;

                try {
                    steps = Integer.parseInt(stepsField.getText());
                    threads = Integer.parseInt(threadsField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid integer values for steps and threads.");
                    return;
                }

                if (steps < 1 || threads < 1 || threads > 20) {
                    JOptionPane.showMessageDialog(null, "Please enter values within the specified range (steps: 1 and more, threads: 1-20).");
                    return;
                }

                IntegralCalculation integral = new IntegralCalculation();
                Function f = new Function();

                long startTime = System.currentTimeMillis();
                double result = Double.parseDouble(df.format(integral.calculation(a, b, steps, f, threads)));
                long endTime = System.currentTimeMillis();

                long executionTime = endTime - startTime;

                JOptionPane.showMessageDialog(null, "Result: " + result + "\nExecution Time: " + executionTime + " milliseconds");
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Steps:"));
        panel.add(stepsField);
        panel.add(new JLabel("Threads:"));
        panel.add(threadsField);
        panel.add(startButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}