package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Pin extends JFrame implements ActionListener {
    JButton b1, b2;
    JPasswordField p1, p2;
    String pin;

    Pin(String pin) {
        this.pin = pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label1 = new JLabel("CHANGE YOUR PIN");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(430, 180, 400, 35);
        l3.add(label1);

        JLabel label2 = new JLabel("New PIN: ");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System", Font.BOLD, 16));
        label2.setBounds(430, 220, 150, 35);
        l3.add(label2);

        p1 = new JPasswordField();
        p1.setBackground(new Color(65, 125, 128));
        p1.setForeground(Color.WHITE);
        p1.setBounds(600, 220, 180, 25);
        p1.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(p1);

        JLabel label3 = new JLabel("Re-Enter New PIN: ");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("System", Font.BOLD, 16));
        label3.setBounds(430, 250, 400, 35);
        l3.add(label3);

        p2 = new JPasswordField();
        p2.setBackground(new Color(65, 125, 128));
        p2.setForeground(Color.WHITE);
        p2.setBounds(600, 255, 180, 25);
        p2.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(p2);

        b1 = new JButton("CHANGE");
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

        setSize(1550, 1080);
        setLayout(null);
        setLocation(0, 0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String pin1 = new String(p1.getPassword());
            String pin2 = new String(p2.getPassword());

            if (!pin1.equals(pin2)) {
                JOptionPane.showMessageDialog(null, "Entered PINs do not match");
                return;
            }

            if (e.getSource() == b1) {
                if (pin1.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter New PIN");
                    return;
                }

                Connection con = new Connn().getConnection();
                String query1 = "UPDATE bank SET pin = ? WHERE pin = ?";
                String query2 = "UPDATE login SET pin = ? WHERE pin = ?";
                String query3 = "UPDATE signupthree SET pin = ? WHERE pin = ?";

                try (PreparedStatement pstmt1 = con.prepareStatement(query1);
                     PreparedStatement pstmt2 = con.prepareStatement(query2);
                     PreparedStatement pstmt3 = con.prepareStatement(query3)) {

                    pstmt1.setString(1, pin1);
                    pstmt1.setString(2, pin);
                    pstmt1.executeUpdate();

                    pstmt2.setString(1, pin1);
                    pstmt2.setString(2, pin);
                    pstmt2.executeUpdate();

                    pstmt3.setString(1, pin1);
                    pstmt3.setString(2, pin);
                    pstmt3.executeUpdate();

                    JOptionPane.showMessageDialog(null, "PIN changed successfully");
                    setVisible(false);
                    new main_Class(pin1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error updating PIN: " + ex.getMessage());
                } finally {
                    con.close();
                }
            } else if (e.getSource() == b2) {
                setVisible(false);
                new main_Class(pin);
            }
        } catch (Exception E) {
            E.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred: " + E.getMessage());
        }
    }

    public static void main(String[] args) {
        new Pin("");
    }
}
