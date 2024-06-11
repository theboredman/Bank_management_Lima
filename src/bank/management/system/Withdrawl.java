package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Withdrawl extends JFrame implements ActionListener {

    String pin;
    TextField textField;
    JLabel balanceLabel;

    JButton b1, b2;

    Withdrawl(String pin) {
        this.pin = pin;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label1 = new JLabel("MAXIMUM WITHDRAWAL IS RS.10,000");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(460, 180, 700, 35);
        l3.add(label1);

        JLabel label2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System", Font.BOLD, 16));
        label2.setBounds(460, 220, 400, 35);
        l3.add(label2);

        textField = new TextField();
        textField.setBackground(new Color(65, 125, 128));
        textField.setForeground(Color.WHITE);
        textField.setBounds(460, 260, 320, 25);
        textField.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(textField);

        balanceLabel = new JLabel("Current Balance: ");
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("System", Font.BOLD, 16));
        balanceLabel.setBounds(460, 300, 400, 35);
        l3.add(balanceLabel);

        b1 = new JButton("WITHDRAW");
        b1.setBounds(700, 362, 150, 35);
        b1.setBackground(new Color(65, 125, 128));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(700, 406, 150, 35);
        b2.setBackground(new Color(65, 125, 128));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        l3.add(b2);

        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setVisible(true);

        // Update balance on the screen
        updateBalance();
    }

    private void updateBalance() {
        try (Connection con = new Connn().getConnection()) {
            String query = "SELECT SUM(CASE WHEN type = 'Deposit' THEN amount ELSE -amount END) AS balance FROM bank WHERE pin = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, pin);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int balance = rs.getInt("balance");
                    balanceLabel.setText("Current Balance: Rs. " + balance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving balance: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            try {
                String amountStr = textField.getText();
                if (amountStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter the Amount you want to withdraw");
                    return;
                }

                int amount = Integer.parseInt(amountStr);
                if (amount > 10000) {
                    JOptionPane.showMessageDialog(null, "Maximum withdrawal amount is Rs. 10,000");
                    return;
                }

                Connn c = new Connn();
                String query = "SELECT SUM(CASE WHEN type = 'Deposit' THEN amount ELSE -amount END) AS balance FROM bank WHERE pin = ?";
                try (Connection con = c.getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setString(1, pin);
                    ResultSet rs = pstmt.executeQuery();
                    int balance = 0;
                    if (rs.next()) {
                        balance = rs.getInt("balance");
                    }

                    if (balance < amount) {
                        JOptionPane.showMessageDialog(null, "Insufficient Balance");
                        return;
                    }

                    String insertQuery = "INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, 'Withdrawal', ?)";
                    try (PreparedStatement insertPstmt = con.prepareStatement(insertQuery)) {
                        insertPstmt.setString(1, pin);
                        insertPstmt.setDate(2, new java.sql.Date(new Date().getTime()));
                        insertPstmt.setInt(3, amount);
                        insertPstmt.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "Rs. " + amount + " Debited Successfully");
                    setVisible(false);
                    new main_Class(pin);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid amount entered. Please enter a numeric value.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error processing withdrawal: " + ex.getMessage());
            }
        } else if (e.getSource() == b2) {
            setVisible(false);
            new main_Class(pin);
        }
    }

    public static void main(String[] args) {
        new Withdrawl("");
    }
}
