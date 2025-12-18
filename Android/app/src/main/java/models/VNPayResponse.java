package models;

public class VNPayResponse {
    private String paymentUrl;
    private String txnRef;
    public String getPaymentUrl(){
        return paymentUrl;
    }
    public void setPaymenturl(String paymentUrl){
        this.paymentUrl = paymentUrl;
    }
}
