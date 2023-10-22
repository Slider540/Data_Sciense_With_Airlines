package datasciencewithairlines;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class that is used to display and build the logic of the main program frame
 */
public class MainFrame extends JFrame {

    private JFileChooser fileChooser;
    private File fileName;
    private String dirName;
    private JButton btnChooseFile;
    private JButton btnAnalyzeFile;
    private JButton btnExitProgram;

    /**
     * Constructor of the main program frame
     */
    public MainFrame() {
        super("Data science with airlines");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, 515, 287);

        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 515, 287);
        contentPane.add(panel);
        panel.setLayout(null);

        final JLabel label1 = new JLabel("This program analyzes flight data and "
                + "outputs statistics to a file.");
        final JLabel label2 = new JLabel("Please, choose a file in CSV format to analyze.");

        label1.setBounds(0, 5, 500, 20);
        label1.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        panel.add(label1);
        label2.setBounds(0, 25, 500, 20);
        label2.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        panel.add(label2);

        btnChooseFile = new JButton("Choose file");
        btnChooseFile.addActionListener((ActionEvent ae) -> {
            fileChooser = new JFileChooser();
            FileNameExtensionFilter filter
                    = new FileNameExtensionFilter("Comma separated values (*.csv)", "csv");
            fileChooser.setFileFilter(filter);
            int chooseResult = fileChooser.showDialog(null, "Choose file");
            if (chooseResult == JFileChooser.APPROVE_OPTION) {
                fileName = fileChooser.getSelectedFile();
                dirName = fileChooser.getCurrentDirectory().getAbsolutePath();
                label2.setText("You chose '" + fileName.getName() + "'.");
                btnAnalyzeFile.setEnabled(true);
            }
        }
        );
        btnChooseFile.setBounds(50, 210, 110, 25);
        panel.add(btnChooseFile);

        btnAnalyzeFile = new JButton("Analyze file");
        btnAnalyzeFile.addActionListener((ActionEvent ae) -> {
            try {
                AnalysisCenter ac = new AnalysisCenter();
                ac.analyzeFile(dirName, fileName);
                JOptionPane.showMessageDialog(MainFrame.this, "Data analysis "
                        + "completed. The results of the analysis are recorded "
                        + "in the file 'answers.txt' in the folder where the "
                        + "source data is located.");
                btnAnalyzeFile.setEnabled(false);
                label2.setText("You can choose another file in CSV format to analyze.");
            } catch (Exception ex) {
                btnAnalyzeFile.setEnabled(false);
                JOptionPane.showMessageDialog(MainFrame.this, "Sorry, "
                        + "file upload and analysis failed. Please choose "
                        + "another file with the correct data.");
//                ex.printStackTrace();
            }
        }
        );
        btnAnalyzeFile.setBounds(195, 210, 110, 25);
        btnAnalyzeFile.setEnabled(false);
        panel.add(btnAnalyzeFile);

        btnExitProgram = new JButton("Exit program");
        btnExitProgram.addActionListener((ActionEvent ae) -> {
            System.exit(0);
        }
        );
        btnExitProgram.setBounds(340, 210, 110, 25);
        panel.add(btnExitProgram);

        JLabel backgroundLabel;
        backgroundLabel = new JLabel(new ImageIcon(getClass().getResource("background.jpg")));
        backgroundLabel.setBounds(0, 0, 500, 250);
        panel.add(backgroundLabel);

        setPreferredSize(new Dimension(515, 287));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {

        new MainFrame();
    }
}
