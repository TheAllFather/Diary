import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class DiaryApplication extends JFrame {
    private JTextArea textArea;

    public DiaryApplication() {
        setTitle("My Personal Diary");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDiary();
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearDiary();
            }
        });

        JButton loadButton = new JButton("Load Note");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNote();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(loadButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to save the contents of the diary to a file
    private void saveDiary() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            // Get the selected file name and path
            String fileName = fileChooser.getSelectedFile().getAbsolutePath();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                String content = textArea.getText();
                // Write the contents of the text area to the file
                writer.write(content);
                JOptionPane.showMessageDialog(this, "Diary saved successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error occurred while saving the diary: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to clear the contents of the diary
    private void clearDiary() {
        textArea.setText("");
    }

    // Method to load a note from a file and display it in the diary
    private void loadNote() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            // Get the selected file name and path
            String fileName = fileChooser.getSelectedFile().getAbsolutePath();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                StringBuilder sb = new StringBuilder();
                String line;
                // Read each line from the file and append it to the StringBuilder
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                // Set the contents of the text area to the loaded note
                textArea.setText(sb.toString());
                JOptionPane.showMessageDialog(this, "Note loaded successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error occurred while loading the note: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DiaryApplication app = new DiaryApplication();
                app.setVisible(true);
            }
        });
    }
}
