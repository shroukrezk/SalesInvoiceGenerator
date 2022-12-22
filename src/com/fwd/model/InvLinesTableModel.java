
package com.fwd.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class InvLinesTableModel extends AbstractTableModel {

    private List<InvLine> invoiceLinez;
    private DateFormat dformat = new SimpleDateFormat("dd-MM-yyyy");
    
    public InvLinesTableModel(List<InvLine> invoiceLines) {
        this.invoiceLinez = invoiceLines;
    }

    public List<InvLine> getInvoiceLinez() {
        return invoiceLinez;
    }
    
    
    @Override
    public int getRowCount() {
        return invoiceLinez.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Itm Name";
            case 1:
                return "Itm Price";
            case 2:
                return "Itm Count";
            case 3:
                return "Line Totaal";
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnsIndex) {
        switch (columnsIndex) {
            case 0:
                return String.class;
            case 1:
                return Double.class;
            case 2:
                return Integer.class;
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
        InvLine row = invoiceLinez.get(rowsIndex);
        
        switch (columnsIndex) {
            case 0:
                return row.getItmName();
            case 1:
                return row.getItmPrice();
            case 2:
                return row.getItmCount();
            case 3:
                return row.getLineTotal();
            default:
                return "";
        }
        
    }
    
}
