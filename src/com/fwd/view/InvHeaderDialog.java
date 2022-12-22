
package com.fwd.view;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
public class InvHeaderDialog extends JDialog {
    private JTextField customerNameField;
    private JTextField invDateeField;
    private JLabel customerNameLabel;
    private JLabel invDateLabel;
    private JButton okButton;
    private JButton cancelButton;

    public InvHeaderDialog(InvFrame frame) {
        customerNameLabel = new JLabel("Customer Name:");
        customerNameField = new JTextField(20);
        invDateLabel = new JLabel("Invoice Date:");
        invDateeField = new JTextField(20);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        
        okButton.setActionCommand("createInvOK");
        cancelButton.setActionCommand("createInvCancel");
        
        okButton.addActionListener(frame.getListener());
        cancelButton.addActionListener(frame.getListener());
        setLayout(new GridLayout(3, 2));
        
        add(invDateLabel);
        add(invDateeField);
        add(customerNameLabel);
        add(customerNameField);
        add(okButton);
        add(cancelButton);
        
        pack();
        
    }

    public JTextField getCustomerNameField() {
        return customerNameField;
    }

    public JTextField getInvDateeField() {
        return invDateeField;
    }
    
}
