
package com.fwd.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvLineDialog extends JDialog{
    private JTextField itmNamField;
    private JTextField itmCountField;
    private JTextField itemPriceField;
    private JLabel itemNameLabel;
    private JLabel itmCountLabel;
    private JLabel itemPriceLbl;
    private JButton okButton;
    private JButton cancelButton;
    
    public InvLineDialog(InvFrame frame) {
        itmNamField = new JTextField(20);
        itemNameLabel = new JLabel("Item Name");
        
        itmCountField = new JTextField(20);
        itmCountLabel = new JLabel("Item Count");
        
        itemPriceField = new JTextField(20);
        itemPriceLbl = new JLabel("Item Price");
        
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        
        okButton.setActionCommand("createLineOK");
        cancelButton.setActionCommand("createLineCancel");
        
        okButton.addActionListener(frame.getListener());
        cancelButton.addActionListener(frame.getListener());
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLabel);
        add(itmNamField);
        add(itmCountLabel);
        add(itmCountField);
        add(itemPriceLbl);
        add(itemPriceField);
        add(okButton);
        add(cancelButton);
        
        pack();
    }

    public JTextField getItemNameField() {
        return itmNamField;
    }

    public JTextField getItmCountField() {
        return itmCountField;
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }
}
