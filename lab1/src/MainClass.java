import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainClass {

    private static List<ThreadRow> threadRows;
    private static JRadioButton syncRadioButton;
    private JRadioButton asyncRadioButton;

    public MainClass() {
        threadRows = new ArrayList<>();
    }

    public void createRow(JPanel panel) {
        ThreadRow threadRow = new ThreadRow(panel);
        threadRows.add(threadRow);
        panel.add(threadRow);
        panel.revalidate();
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();

        JFrame frame = new JFrame("Thread Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 370);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        mainClass.syncRadioButton = new JRadioButton("Synchronous");
        mainClass.asyncRadioButton = new JRadioButton("Asynchronous");
        ButtonGroup group = new ButtonGroup();
        group.add(mainClass.syncRadioButton);
        group.add(mainClass.asyncRadioButton);

        JButton plusButton = new JButton("+");

        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainClass.createRow(panel);
            }
        });

        JButton runButton = new JButton("Run");

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isSynchronous = syncRadioButton.isSelected();

                for (ThreadRow threadRow : threadRows) {
                    String threadType = threadRow.getThreadType();
                    String fileName = threadRow.getFileName();
                    int priority = threadRow.getPriority();

                    if (threadType != null && fileName != null) {
                        Thread thread;

                        if (threadType.equals("Sum")) {
                            thread = new Thread(new First_flow(priority, fileName));
                        } else if (threadType.equals("WordCount")) {
                            thread = new Thread(new Second_flow(priority, fileName));
                        } else if (threadType.equals("RandomLine")) {
                            thread = new Thread(new Third_flow(priority, fileName));
                        } else {
                            continue;
                        }

                        thread.start();

                        if (isSynchronous) {
                            try {
                                thread.join();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        panel.add(mainClass.syncRadioButton);
        panel.add(mainClass.asyncRadioButton);
        panel.add(plusButton);
        panel.add(runButton);

        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    static class ThreadRow extends JPanel {
        private JComboBox<String> threadTypeComboBox;
        private JTextField fileNameField;
        private JSpinner prioritySpinner;

        public ThreadRow(JPanel parentPanel) {
            threadTypeComboBox = new JComboBox<>(new String[]{"Sum", "WordCount", "RandomLine"});
            threadTypeComboBox.setPreferredSize(new Dimension(100, 30));
            fileNameField = new JTextField();
            fileNameField.setPreferredSize(new Dimension(150, 30));
            prioritySpinner = new JSpinner(new SpinnerNumberModel(8, 1, 10, 1));

            this.add(threadTypeComboBox);
            this.add(fileNameField);
            this.add(new JLabel("Priority:"));
            this.add(prioritySpinner);

            JButton removeButton = new JButton("-");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parentPanel.remove(ThreadRow.this);
                    parentPanel.revalidate();
                    parentPanel.repaint();
                    threadRows.remove(ThreadRow.this);
                }
            });

            this.add(removeButton);
        }

        public String getThreadType() {
            return (String) threadTypeComboBox.getSelectedItem();
        }

        public String getFileName() {
            return fileNameField.getText();
        }

        public int getPriority() {
            return (int) prioritySpinner.getValue();
        }
    }
}
