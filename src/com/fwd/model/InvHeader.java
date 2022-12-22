
package com.fwd.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvHeader {
    private int invNumb;
    private String theCustomerName;
    private Date invDatee;
    private ArrayList<InvLine> linez;  

    public InvHeader(int invNum, String customerName, Date invDate) {
        this.invNumb = invNum;
        this.theCustomerName = customerName;
        this.invDatee = invDate;
        //this.linez = new ArrayList<>();   // eager creation
    }

    
    
    
    public Date getInvDatee() {
        return invDatee;
    }

    
    
    
    
    
    public void setInvDatee(Date invDatee) {
        this.invDatee = invDatee;
    }

    
    
    
    public int getInvNumb() {
        return invNumb;
    }

    
    
    
    public void toSetInvNumb(int invNumb) {
        this.invNumb = invNumb;
    }

    
    
    
    public String toGetTheCustomerName() {
        return theCustomerName;
    }

    
    
    
    public void setTheCustomerName(String theCustomerName) {
        this.theCustomerName = theCustomerName;
    }

    
    
    
    @Override
    public String toString() {
        String str = "InvoiceHeader{" + "invNum=" + invNumb + ", customerName=" + theCustomerName + ", invDate=" + invDatee + '}';
        for (InvLine line : getLinez()) {
            str += "\n\t" + line;
        }
        return str;
    }

    
    
    public ArrayList<InvLine> getLinez() {
        if (linez == null)
            linez = new ArrayList<>();  // lazy creation
        return linez;
    }

    public void setLinez(ArrayList<InvLine> linez) {
        this.linez = linez;
    }

    public double getInvTotal() {
        double total = 0.0;
        for (InvLine line : getLinez()) {
            total += line.getLineTotal();
        }
        return total;
    }
    
    public void addInvLine(InvLine line) {
        getLinez().add(line);
    }
    
    public String getDataAsCSV() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return "" + getInvNumb() + "," + df.format(getInvDatee()) + "," + toGetTheCustomerName();
    }
    
}
