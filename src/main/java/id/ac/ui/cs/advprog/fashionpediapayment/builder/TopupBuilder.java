package id.ac.ui.cs.advprog.fashionpediapayment.builder;

import id.ac.ui.cs.advprog.fashionpediapayment.model.Topup;

public class TopupBuilder {
    private final Topup topup;
    //String buyerId, String method, String bankName, String accountNumber, long nominal, String photoProof
    public TopupBuilder() {
        topup = new Topup();
    }

    public Topup build() {
        if (topup.getBuyerId() == null ||
            topup.getMethod() == null ||
            topup.getAccountNumber() == null ||
            topup.getBankName() == null) {
            return null;
        }
        return topup;
    }

    public TopupBuilder setBuyerId(String buyerId) {
        topup.setBuyerId(buyerId);
        return this;
    }

    public TopupBuilder setBankName(String bankName) {
        topup.setBankName(bankName);
        return this;
    }

    public TopupBuilder setAccountNumber(String accountNumber) {
        topup.setAccountNumber(accountNumber);
        return this;
    }

    public TopupBuilder setMethod(String method) {
        topup.setMethod(method);
        return this;
    }

    public TopupBuilder setNominal(long nominal) {
        topup.setNominal(nominal);
        return this;
    }

    public TopupBuilder setPhotoProof(String photoProof) {
        topup.setPhotoProof(photoProof);
        return this;
    }
}
