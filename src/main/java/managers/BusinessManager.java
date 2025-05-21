package managers;

public class BusinessManager {

    public int totalSales;

    public void sellTicket() {}
    public void assignTicket() {}
    public void processSale() {
        sellTicket();
        assignTicket();
    }
    public int getTotalSales() {return 0;}
}
