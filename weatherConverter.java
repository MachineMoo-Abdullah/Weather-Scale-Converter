import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WeatherScaleConverter extends JFrame {

    JLabel l, l1, l2,label;
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
        JButton cToFButton = setBounds("Celsius to Fahrenheit", 250, 140);
        JButton cToKButton = setBounds("Celsius to Kelvin", 250, 210);
        JButton fToCButton = setBounds("Fahrenheit to Celsius", 250, 280);
        JButton fToKButton = setBounds("Fahrenheit to Kelvin", 250, 350);
        JButton kToCButton = setBounds("Kelvin to Celsius", 250, 420);
        JButton kToFButton = setBounds("Kelvin to Fahrenheit", 250, 490);

        cToFButton.setBackground(Color.red);
        cToFButton.setForeground(Color.white);
        cToKButton.setBackground(Color.yellow);
        fToCButton.setBackground(Color.red);
        fToCButton.setForeground(Color.white);
        fToKButton.setBackground(Color.yellow);
        kToCButton.setBackground(Color.red);
        kToCButton.setForeground(Color.white);
        kToFButton.setBackground(Color.yellow);

        // Add action listeners to buttons
        cToFButton.addActionListener(e -> openConversionWindow("Celsius to Fahrenheit"));
        cToKButton.addActionListener(e -> openConversionWindow("Celsius to Kelvin"));
        fToCButton.addActionListener(e -> openConversionWindow("Fahrenheit to Celsius"));
        fToKButton.addActionListener(e -> openConversionWindow("Fahrenheit to Kelvin"));
        kToCButton.addActionListener(e -> openConversionWindow("Kelvin to Celsius"));
        kToFButton.addActionListener(e -> openConversionWindow("Kelvin to Fahrenheit"));

        // Add buttons to the frame
        jf.add(cToFButton);
        jf.add(cToKButton);
        jf.add(fToCButton);
        jf.add(fToKButton);
        jf.add(kToCButton);
        jf.add(kToFButton);

        jf.setVisible(true);
    }

    private JButton setBounds(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 200, 40);
        return button;
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
                }
                else {
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
                    saveResultToFile(conversionType,inputTemp,result);
                    break;
                case "Celsius to Kelvin":
                    result = celsiusToKelvin(inputTemp);
                    saveResultToFile(conversionType,inputTemp,result);
                    break;
                case "Fahrenheit to Celsius":
                    result = fahrenheitToCelsius(inputTemp);
                    saveResultToFile(conversionType,inputTemp,result);
                    break;
                case "Fahrenheit to Kelvin":
                    result = fahrenheitToKelvin(inputTemp);
                    saveResultToFile(conversionType,inputTemp,result);
                    break;
                case "Kelvin to Celsius":
                    result = kelvinToCelsius(inputTemp);
                    saveResultToFile(conversionType,inputTemp,result);
                    break;
                case "Kelvin to Fahrenheit":
                    result = kelvinToFahrenheit(inputTemp);
                    saveResultToFile(conversionType,inputTemp,result);
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("conversion_results.txt", true))) {
            writer.write(conversionType + ": " + inputTemp + " -> " + result);
            writer.newLine();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving result to file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
