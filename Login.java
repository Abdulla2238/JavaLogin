import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 180); // Adjusted height to accommodate styling

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // Added spacing between components

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Custom font and size
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14)); // Custom font and size
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Custom font and size
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14)); // Custom font and size

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14)); // Custom font, bold

        // Customizing button color and background
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(59, 89, 182));
        loginButton.setFocusPainted(false); // Remove button focus border

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Replace with your authentication logic
            if (authenticate(username, password)) {
                // If authentication successful, fetch data from API
                fetchDataFromApi();
            } else {
                JOptionPane.showMessageDialog(Login.this, "Login failed. Invalid username or password.");
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        // Adding padding around the panel
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Center the frame on the screen
        setLocationRelativeTo(null);

        add(panel);
        setVisible(true);
    }

    private boolean authenticate(String username, String password) {
        // Replace with your authentication logic (e.g., check against database or API)
        // For simplicity, hardcoding credentials here
        return username.equals("admin") && password.equals("password");
    }

    private void fetchDataFromApi() {
        try {
            @SuppressWarnings("deprecation")
            URL url = new URL("https://localhost:7202/api/Values");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuffer response;
                try ( // success
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }

                // Print result from API
                System.out.println("API Response:");
                System.out.println(response.toString());
            } else {
                System.out.println("GET request not worked");
            }
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}