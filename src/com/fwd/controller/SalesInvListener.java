package com.fwd.controller;

import com.fwd.model.InvHeader;
import com.fwd.model.InvHeaderTableModel;
import com.fwd.model.InvLine;
import com.fwd.model.InvLinesTableModel;
import com.fwd.view.InvFrame;
import com.fwd.view.InvHeaderDialog;
import com.fwd.view.InvLineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SalesInvListener implements ActionListener, ListSelectionListener {

    private InvFrame myFrame;
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public SalesInvListener(InvFrame frame) {
        this.myFrame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "CreateNewInvoice":
                displayNewInvoiceDialog();
                break;
            case "DeleteInvoice":
                toDeleteInvoice();
                break;
            case "CreateNewLine":
                displayNewLineDialog();
                break;
            case "DeleteLine":
                toDeleteLine();
                break;
            case "LoadFile":
                loadFile();
                break;
            case "SaveFile":
                saveData();
                break;
            case "createInvCancel":
                createInvCancel();
                break;
            case "createInvOK":
                createInvOK();
                break;
            case "createLineCancel":
                toCreateLineCancel();
                break;
            case "createLineOK":
                toCreateLineOK();
                break;
        }
    }

    private void loadFile() {
        JOptionPane.showMessageDialog(myFrame, "Please, select header file!", "Attension", JOptionPane.WARNING_MESSAGE);
        JFileChooser openFile = new JFileChooser();
        int result = openFile.showOpenDialog(myFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File headerFile = openFile.getSelectedFile();
            try {
                FileReader headerFr = new FileReader(headerFile);
                BufferedReader headerBr = new BufferedReader(headerFr);
                String headerLine = null;

                while ((headerLine = headerBr.readLine()) != null) {
                    String[] headerParts = headerLine.split(",");
                    String invNumStr = headerParts[0];
                    String invDateStr = headerParts[1];
                    String custName = headerParts[2];

                    int invNum = Integer.parseInt(invNumStr);
                    Date invDate = dateFormat.parse(invDateStr);

                    InvHeader inv = new InvHeader(invNum, custName, invDate);
                    myFrame.getInvoicesList().add(inv);
                }

                JOptionPane.showMessageDialog(myFrame, "Please, select lines file!", "Attension", JOptionPane.WARNING_MESSAGE);
                result = openFile.showOpenDialog(myFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File linesFile = openFile.getSelectedFile();
                    BufferedReader lineBr = new BufferedReader(new FileReader(linesFile));
                    String linesLine = null;
                    while ((linesLine = lineBr.readLine()) != null) {
                        String[] linePartss = linesLine.split(",");
                        String invoiceNumStr = linePartss[0];
                        String itmName = linePartss[1];
                        String itmPriceStr = linePartss[2];
                        String itmCountStr = linePartss[3];

                        int invNumber = Integer.parseInt(invoiceNumStr);
                        double itmPrice = Double.parseDouble(itmPriceStr);
                        int itmCount = Integer.parseInt(itmCountStr);
                        InvHeader header = findInvoiceByNum(invNumber);
                        InvLine invLine = new InvLine(itmName, itmPrice, itmCount, header);
                        header.getLinez().add(invLine);
                    }
                    myFrame.setInvoiceHeaderTableModel(new InvHeaderTableModel(myFrame.getInvoicesList()));
                    myFrame.getInvoicesTable().setModel(myFrame.getInvoiceHeaderTableModel());
                    myFrame.getInvoicesTable().validate();
                }
                System.out.println("Check");
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(myFrame, "Date Format Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(myFrame, "Number Format Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(myFrame, "File Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(myFrame, "Read Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        displayInvoicez();
    }

    private void saveData() {
        String headers = "";
        String lines = "";
        for (InvHeader header : myFrame.getInvoicesList()) {
            headers += header.getDataAsCSV();
            headers += "\n";
            for (InvLine line : header.getLinez()) {
                lines += line.getDataAsCSV();
                lines += "\n";
            }
        }
        JOptionPane.showMessageDialog(myFrame, "Please, select file to save header data!", "Attension", JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(myFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File headerFile = fileChooser.getSelectedFile();
            try {
                FileWriter hFW = new FileWriter(headerFile);
                hFW.write(headers);
                hFW.flush();
                hFW.close();

                JOptionPane.showMessageDialog(myFrame, "Please, select file to save lines data!", "Attension", JOptionPane.WARNING_MESSAGE);
                result = fileChooser.showSaveDialog(myFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File linesFile = fileChooser.getSelectedFile();
                    FileWriter lFW = new FileWriter(linesFile);
                    lFW.write(lines);
                    lFW.flush();
                    lFW.close();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(myFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(myFrame, "Data saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

    }

    private InvHeader findInvoiceByNum(int invNum) {
        InvHeader header = null;
        for (InvHeader inv : myFrame.getInvoicesList()) {
            if (invNum == inv.getInvNumb()) {
                header = inv;
                break;
            }
        }
        return header;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("Invoice Selected!");
        invoicesTableRowSelected();
    }

    private void invoicesTableRowSelected() {
        int selectedRowIndex = myFrame.getInvoicesTable().getSelectedRow();
        if (selectedRowIndex >= 0) {
            InvHeader row = myFrame.getInvoiceHeaderTableModel().getInvoicesLists().get(selectedRowIndex);
            myFrame.getCustNameTF().setText(row.toGetTheCustomerName());
            myFrame.getInvDateTF().setText(dateFormat.format(row.getInvDatee()));
            myFrame.getInvNumLbl().setText("" + row.getInvNumb());
            myFrame.getInvTotalLbl().setText("" + row.getInvTotal());
            ArrayList<InvLine> lines = row.getLinez();
            myFrame.setInvoiceLinesTableModel(new InvLinesTableModel(lines));
            myFrame.getInvLinesTable().setModel(myFrame.getInvoiceLinesTableModel());
            myFrame.getInvoiceLinesTableModel().fireTableDataChanged();
        }
    }

    private void displayNewInvoiceDialog() {
        myFrame.setHeaderDialog(new InvHeaderDialog(myFrame));
        myFrame.getHeaderDialog().setVisible(true);
    }

    private void displayNewLineDialog() {
        myFrame.setLineDialog(new InvLineDialog(myFrame));
        myFrame.getLineDialog().setVisible(true);
    }

    private void createInvCancel() {
        myFrame.getHeaderDialog().setVisible(false);
        myFrame.getHeaderDialog().dispose();
        myFrame.setHeaderDialog(null);
    }

    private void createInvOK() {
        String custName = myFrame.getHeaderDialog().getCustomerNameField().getText();
        String invDateStr = myFrame.getHeaderDialog().getInvDateeField().getText();
        myFrame.getHeaderDialog().setVisible(false);
        myFrame.getHeaderDialog().dispose();
        myFrame.setHeaderDialog(null);
        try {
            Date invDate = dateFormat.parse(invDateStr);
            int invNum = toGetNextInvoiceNum();
            InvHeader invoiceHeader = new InvHeader(invNum, custName, invDate);
            myFrame.getInvoicesList().add(invoiceHeader);
            myFrame.getInvoiceHeaderTableModel().fireTableDataChanged();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(myFrame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        displayInvoicez();
    }

    private int toGetNextInvoiceNum() {
        int max = 0;
        for (InvHeader header : myFrame.getInvoicesList()) {
            if (header.getInvNumb() > max) {
                max = header.getInvNumb();
            }
        }
        return max + 1;
    }

    private void toCreateLineCancel() {
        myFrame.getLineDialog().setVisible(false);
        myFrame.getLineDialog().dispose();
        myFrame.setLineDialog(null);
    }

    private void toCreateLineOK() {
        String itemName = myFrame.getLineDialog().getItemNameField().getText();
        String itemCountStr = myFrame.getLineDialog().getItmCountField().getText();
        String itemPriceStr = myFrame.getLineDialog().getItemPriceField().getText();
        myFrame.getLineDialog().setVisible(false);
        myFrame.getLineDialog().dispose();
        myFrame.setLineDialog(null);
        int itemCount = Integer.parseInt(itemCountStr);
        double itemPrice = Double.parseDouble(itemPriceStr);
        int headerIndex = myFrame.getInvoicesTable().getSelectedRow();
        InvHeader invoice = myFrame.getInvoiceHeaderTableModel().getInvoicesLists().get(headerIndex);

        InvLine invoiceLine = new InvLine(itemName, itemPrice, itemCount, invoice);
        invoice.addInvLine(invoiceLine);
        myFrame.getInvoiceLinesTableModel().fireTableDataChanged();
        myFrame.getInvoiceHeaderTableModel().fireTableDataChanged();
        myFrame.getInvTotalLbl().setText("" + invoice.getInvTotal());
        displayInvoicez();
    }

    private void toDeleteInvoice() {
        int invIndex = myFrame.getInvoicesTable().getSelectedRow();
        InvHeader Headers = myFrame.getInvoiceHeaderTableModel().getInvoicesLists().get(invIndex);
        myFrame.getInvoiceHeaderTableModel().getInvoicesLists().remove(invIndex);
        myFrame.getInvoiceHeaderTableModel().fireTableDataChanged();
        myFrame.setInvoiceLinesTableModel(new InvLinesTableModel(new ArrayList<InvLine>()));
        myFrame.getInvLinesTable().setModel(myFrame.getInvoiceLinesTableModel());
        myFrame.getInvoiceLinesTableModel().fireTableDataChanged();
        myFrame.getCustNameTF().setText("");
        myFrame.getInvDateTF().setText("");
        myFrame.getInvNumLbl().setText("");
        myFrame.getInvTotalLbl().setText("");
        displayInvoicez();
    }

    private void toDeleteLine() {
        int lineIndex = myFrame.getInvLinesTable().getSelectedRow();
        InvLine line = myFrame.getInvoiceLinesTableModel().getInvoiceLinez().get(lineIndex);
        myFrame.getInvoiceLinesTableModel().getInvoiceLinez().remove(lineIndex);
        myFrame.getInvoiceLinesTableModel().fireTableDataChanged();
        myFrame.getInvoiceHeaderTableModel().fireTableDataChanged();
        myFrame.getInvTotalLbl().setText("" + line.getHeaders().getInvTotal());
        displayInvoicez();
    }

    private void displayInvoicez() {
        System.out.println("**********************");
        for (InvHeader header : myFrame.getInvoicesList()) {
            System.out.println(header);
        }
        System.out.println("*********************");
    }

}
