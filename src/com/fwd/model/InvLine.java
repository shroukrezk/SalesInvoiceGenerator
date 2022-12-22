
package com.fwd.model;
//here we will make something different
public class InvLine {
    private String itmName;
    private double itmPrice;
    private int itmCount;
    private InvHeader Headers;

    public InvLine(String itemName, double itemPrices, int itemCounts, InvHeader header) {
        this.itmName = itemName;
        this.itmPrice = itemPrices;
        this.itmCount = itemCounts;
        this.Headers = header;
    }
    
    
    
    public String getItmName() {
        return itmName;
    }

    
    public void setItmName(String itmName) {
        this.itmName = itmName;
    }

    
    
    public double getItmPrice() {
        return itmPrice;
    }

    
    public void setItmPrice(double itmPrice) {
        this.itmPrice = itmPrice;
    }

    
    public int getItmCount() {
        return itmCount;
    }

    
    public void setItmCount(int itmCount) {
        this.itmCount = itmCount;
    }

    
    public InvHeader getHeaders() {
        return Headers;
    }

    
    public void setHeaders(InvHeader Headers) {
        this.Headers = Headers;
    }

    
    @Override
    public String toString() {
        return "InvoiceLine{" + "itemName=" + itmName + ", itemprice=" + itmPrice + ", itemCount=" + itmCount + '}';
    }
    
    
    public double getLineTotal() {
        return itmCount * itmPrice;
    }
    
    
    public String getDataAsCSV() {
        return "" + getHeaders().getInvNumb() + "," + getItmName() + "," + getItmPrice() + "," + getItmCount();
    }
}
