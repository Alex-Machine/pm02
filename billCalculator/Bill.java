package billCalculator;

public class Bill {
    private int number;
    private String name;
    private String category;
    private Double sum;
    private String currency;
    private Double percent;
    private String date;

    public Bill() {
    }

    public Bill(int number, String name, String category, Double sum, String currency, Double percent) {
        this.number = number;
        this.name = name;
        this.category = category;
        this.sum = sum;
        this.currency = currency;
        this.percent = percent;
    }

    public Bill(int number, String name, String category, Double sum, String currency, Double percent, String date) {
        this.number = number;
        this.name = name;
        this.category = category;
        this.sum = sum;
        this.currency = currency;
        this.percent = percent;
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
