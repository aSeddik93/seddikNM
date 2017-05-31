package model;

/**
 * Created by ADMIN on 27/05/2017.
 */
public class Payment {

    private int id;
    private String cardType;
    private int cardNumber;
    private String cardHolder;
    private int cardCVC;
    private String cardExpiry;
    private int bookingId;
    private double amount;

    public Payment(int id,String cardType, String cardNumber, String cardHolder, String cardCVC, String cardExpiry, double amount,int bookingid) {
        this.id = id;
        this.cardType = cardType;
        this.cardNumber = Integer.parseInt(cardNumber);
        this.cardHolder = cardHolder;
        this.cardCVC = Integer.parseInt(cardCVC);
        this.cardExpiry = cardExpiry;
        this.amount = amount;
        this.bookingId = bookingid;

    }

    public Payment(int id, String cardType, int cardNumber, String cardHolder, int cardCVC, String cardExpiry, double amount,int bookingid) {
        this.id = id;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cardCVC = cardCVC;
        this.cardExpiry = cardExpiry;
        this.amount = amount;
        this.bookingId = bookingid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getCardType() {
        return cardType;
    }

    int getCardNumber() {
        return cardNumber;
    }

    String getCardHolder() {
        return cardHolder;
    }

    int getCardCVC() {
        return cardCVC;
    }

    String getCardExpiry() {
        return cardExpiry;
    }


    public int getBookingId() {
        return bookingId;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setCardCVC(int cardCVC) {
        this.cardCVC = cardCVC;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

