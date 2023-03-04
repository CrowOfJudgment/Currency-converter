import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class UserInterface extends JFrame {
    UserInterface(double rate){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320,210);
        setTitle("Currency converter");
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        ImageIcon GBFlag = new ImageIcon("GBFlag.png");
        ImageIcon PLFlag = new ImageIcon("PLFlag.png");

        Border border = BorderFactory.createLineBorder(Color.GRAY,1);

        //--------- Send ---------

        JLabel youSendText = new JLabel("You send");
        add(youSendText);

        JPanel sendPanel = new JPanel();
        sendPanel.setBackground(Color.WHITE);
        sendPanel.setPreferredSize(new Dimension(300,40));
        sendPanel.setBorder(border);
        add(sendPanel);

        JLabel youSendLabel = new JLabel();
        youSendLabel.setIcon(GBFlag);
        youSendLabel.setBorder(border);
        sendPanel.add(youSendLabel);

        JTextField youSendTextField = new JTextField();
        youSendTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        youSendTextField.setPreferredSize(new Dimension(200,30));
        sendPanel.add(youSendTextField);

        JLabel GBPLabel = new JLabel("GBP");
        sendPanel.add(GBPLabel);

        //--------- Receive ---------

        JLabel theyReceiveText = new JLabel("They receive");
        add(theyReceiveText);

        JPanel receivePanel = new JPanel();
        receivePanel.setBackground(Color.WHITE);
        receivePanel.setPreferredSize(new Dimension(300,40));
        receivePanel.setBorder(border);
        add(receivePanel);

        JLabel theyReceiveLabel = new JLabel();
        theyReceiveLabel.setIcon(PLFlag);
        theyReceiveLabel.setBorder(border);
        receivePanel.add(theyReceiveLabel);

        JTextField theyReceiveTextField = new JTextField();
        theyReceiveTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        theyReceiveTextField.setPreferredSize(new Dimension(200,30));
        receivePanel.add(theyReceiveTextField);

        JLabel PLNLabel = new JLabel("PLN");
        receivePanel.add(PLNLabel);

        //---------------------------

        youSendTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Runnable sendRunnable = new Runnable() {
                    @Override
                    public void run() {
                        String text = youSendTextField.getText();
                        if(!text.matches("\\d*(\\.\\d{0,2})?")){
                            youSendTextField.setText(text.substring(0,text.length()-1));
                        }
                    }
                };
                SwingUtilities.invokeLater(sendRunnable);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {}
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        youSendTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                try{
                    BigDecimal amount = new BigDecimal((youSendTextField.getText())).multiply(new BigDecimal(rate));
                    theyReceiveTextField.setText(String.valueOf(amount));
                }catch(Exception ex){
                    theyReceiveTextField.setText("");
                    youSendTextField.setText("");
                }
            }
        });

        theyReceiveTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Runnable receiveRunnable = new Runnable() {
                    @Override
                    public void run() {
                        String text = theyReceiveTextField.getText();
                        if(!text.matches("\\d*(\\.\\d{0,2})?")){
                            theyReceiveTextField.setText(text.substring(0,text.length()-1));
                        }
                    }
                };
                SwingUtilities.invokeLater(receiveRunnable);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {}
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        theyReceiveTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    BigDecimal amount = new BigDecimal((theyReceiveTextField.getText())).divide(new BigDecimal(rate), 2, RoundingMode.HALF_UP);
                    youSendTextField.setText(String.valueOf(amount));
                } catch (Exception ex) {
                    theyReceiveTextField.setText("");
                    youSendTextField.setText("");
                }
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setLayout(new BorderLayout());
        add(bottomPanel);

        bottomPanel.add(new JLabel("1 GBP = " + String.format("%.2f", rate) + " PLN"),BorderLayout.NORTH);
        bottomPanel.add(new JLabel("No transfer fee"),BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
