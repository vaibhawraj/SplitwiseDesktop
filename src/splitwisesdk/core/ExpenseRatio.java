package splitwisesdk.core;

public class ExpenseRatio {

    public People creditor = new People();
    public People debitor = new People();
    public  float amount;
    public float portion;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getPortion() {
        return portion;
    }

    public void setPortion(float portion) {
        this.portion = portion;
    }
}
