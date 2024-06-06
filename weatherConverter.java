import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class WeatherScaleConverter extends JFrame {

    JLabel l, l1, l2, label;
    JRadioButton cToFButton, cToKButton, fToCButton, fToKButton, kToCButton, kToFButton;
    ButtonGroup group;

    public WeatherScaleConverter() {
        JFrame jf = new JFrame();
        jf.setTitle("Weather Scale Converter");
        jf.setSize(700, 700);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setLayout(null); // Use absolute positioning
        jf.getContentPane().setBackground(Color.BLACK);

        JLabel l = new JLabel("Weather Scale Converter");
        l.setForeground(Color.YELLOW);
        l.setBounds(120, 30, 500, 40);
        l.setFont(new Font("Arial", Font.BOLD, 40));
        jf.add(l);

        // Create UI components
        cToFButton = createRadioButton("Celsius to Fahrenheit", 250, 120);
        cToKButton = createRadioButton("Celsius to Kelvin", 250, 180);
        fToCButton = createRadioButton("Fahrenheit to Celsius", 250, 240);
        fToKButton = createRadioButton("Fahrenheit to Kelvin", 250, 300);
        kToCButton = createRadioButton("Kelvin to Celsius", 250, 360);
        kToFButton = createRadioButton("Kelvin to Fahrenheit", 250, 420);

        // Group the radio buttons
        group = new ButtonGroup();
        group.add(cToFButton);
        group.add(cToKButton);
        group.add(fToCButton);
        group.add(fToKButton);
        group.add(kToCButton);
        group.add(kToFButton);

        // Add a submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(250, 560, 200, 40);
        submitButton.setBackground(Color.yellow);
        submitButton.addActionListener(e -> handleSelection());

        // Add components to the frame
        jf.add(cToFButton);
        jf.add(cToKButton);
        jf.add(fToCButton);
        jf.add(fToKButton);
        jf.add(kToCButton);
        jf.add(kToFButton);
        jf.add(submitButton);

        jf.setVisible(true);
    }

    private JRadioButton createRadioButton(String text, int x, int y) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setBounds(x, y, 200, 40);
        radioButton.setBackground(Color.BLACK);
        radioButton.setForeground(Color.WHITE);
        radioButton.setFont(new Font("Arial", Font.BOLD, 14));
        return radioButton;
    }

    private void handleSelection() {
        if (cToFButton.isSelected()) {
            openConversionWindow("Celsius to Fahrenheit");
        } else if (cToKButton.isSelected()) {
            openConversionWindow("Celsius to Kelvin");
        } else if (fToCButton.isSelected()) {
            openConversionWindow("Fahrenheit to Celsius");
        } else if (fToKButton.isSelected()) {
            openConversionWindow("Fahrenheit to Kelvin");
        } else if (kToCButton.isSelected()) {
            openConversionWindow("Kelvin to Celsius");
        } else if (kToFButton.isSelected()) {
            openConversionWindow("Kelvin to Fahrenheit");
        } else {
            JOptionPane.showMessageDialog(null, "Please select a conversion type", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openConversionWindow(String conversionType) {
        JFrame conversionFrame = new JFrame(conversionType);
        conversionFrame.setSize(700, 500);
        conversionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        conversionFrame.setLocationRelativeTo(null);
        conversionFrame.setLayout(null); // Use absolute positioning
        conversionFrame.getContentPane().setBackground(Color.black);

        l1 = new JLabel(conversionType);
        l1.setForeground(Color.YELLOW);
        l1.setBounds(120, 30, 500, 40);
        l1.setFont(new Font("Arial", Font.BOLD, 40));
        conversionFrame.add(l1);

        l2 = new JLabel("Enter Temperature:");
        l2.setBounds(80, 150, 200, 40);
        l2.setFont(new Font("Arial", Font.BOLD, 20));
        l2.setForeground(Color.white);
        conversionFrame.add(l2);

        JTextField inp = new JTextField();
        inp.setBounds(300, 150, 200, 30);
        conversionFrame.add(inp);


        JLabel resultLabel = new JLabel("Result: ");
        resultLabel.setBounds(80, 250, 500, 40);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultLabel.setForeground(Color.white);
        conversionFrame.add(resultLabel);

        JButton submitButton = new JButton("Convert");
        submitButton.setBounds(300, 200, 200, 40);
        submitButton.setBackground(Color.red);
        submitButton.setForeground(Color.white);
        conversionFrame.add(submitButton);
        inp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inp.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Input field cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    submitButton.doClick();
                }
            }
        });

        // Add action listener to the submit button
        submitButton.addActionListener(e -> performConversion(conversionType, inp, resultLabel));

        conversionFrame.setVisible(true);
    }

    private void performConversion(String conversionType, JTextField inputField, JLabel resultLabel) {
        try {
            double inputTemp = Double.parseDouble(inputField.getText());
            double result = 0;

            switch (conversionType) {
                case "Celsius to Fahrenheit":
                    result = celsiusToFahrenheit(inputTemp);
                    saveResultToFile(conversionType, inputTemp, result);
                    break;
                case "Celsius to Kelvin":
                    result = celsiusToKelvin(inputTemp);
                    saveResultToFile(conversionType, inputTemp, result);
                    break;
                case "Fahrenheit to Celsius":
                    result = fahrenheitToCelsius(inputTemp);
                    saveResultToFile(conversionType, inputTemp, result);
                    break;
                case "Fahrenheit to Kelvin":
                    result = fahrenheitToKelvin(inputTemp);
                    saveResultToFile(conversionType, inputTemp, result);
                    break;
                case "Kelvin to Celsius":
                    result = kelvinToCelsius(inputTemp);
                    saveResultToFile(conversionType, inputTemp, result);
                    break;
                case "Kelvin to Fahrenheit":
                    result = kelvinToFahrenheit(inputTemp);
                    saveResultToFile(conversionType, inputTemp, result);
                    break;
            }

            resultLabel.setText("Result: " + result);
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Please enter a number.");
        }
    }

    // Conversion methods
    private double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    private double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    private double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    private double fahrenheitToKelvin(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9 + 273.15;
    }

    private double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    private double kelvinToFahrenheit(double kelvin) {
        return (kelvin - 273.15) * 9 / 5 + 32;
    }

    public static void main(String[] args) {
        new WeatherScaleConverter();
    }


    private void saveResultToFile(String conversionType, double inputTemp, double result) {
        // Save to text file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("conversion_results.txt", true))) {
            writer.write(conversionType + ": " + inputTemp + " -> " + result);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving result to text file", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Save to PDF file
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("conversion_results.pdf", true));
            document.open();
            BufferedReader reader = new BufferedReader(new FileReader("conversion_results.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                document.add(new Paragraph(line));
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading from text file or writing to PDF file", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            document.close();
        }
    }

}
