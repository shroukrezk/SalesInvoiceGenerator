
package com.fwd.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class InvHeaderTableModel extends AbstractTableModel {

    private List<InvHeader> invoicesLists;
    private DateFormat dformat = new SimpleDateFormat("dd-MM-yyyy");
    
    public InvHeaderTableModel(List<InvHeader> invoicesList) {
        this.invoicesLists = invoicesList;
    }

    public List<InvHeader> getInvoicesLists() {
        return invoicesLists;
    }
    
    
    @Override
    public int getRowCount() {
        return invoicesLists.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "the Invoice Number";
            case 1:
                return "the Customer Name";
            case 2:
                return "the Invoice Date";
            case 3:
                return "the Invoice Total";
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnsIndex) {
        switch (columnsIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Double.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowsIndex, int columnsIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowsIndex, int columnsIndex) {
        InvHeader rows = invoicesLists.get(rowsIndex);
        
        switch (columnsIndex) {
            case 0:
                return rows.getInvNumb();
            case 1:
                return rows.toGetTheCustomerName();
            case 2:
                return dformat.format(rows.getInvDatee());
            case 3:
                return rows.getInvTotal();
            default:
                return "";
        }
        
    }
    
}
