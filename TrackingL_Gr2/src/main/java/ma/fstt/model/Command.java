package ma.fstt.model;

public class Command {
    private long id_cmd;
    private String brand;
    private String productName;

    public Command(long id_cmd, String brand, String productName, String quantityProd, String price, String date_cmd) {
        this.id_cmd = id_cmd;
        this.brand = brand;
        this.productName = productName;
        this.quantityProd = quantityProd;
        this.price = price;
        this.date_cmd = date_cmd;
    }

    private String quantityProd;
    private String price;
    private String date_cmd;

    public long getId_cmd() {
        return id_cmd;
    }

    public void setId_cmd(long id_cmd) {
        this.id_cmd = id_cmd;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantityProd() {
        return quantityProd;
    }

    public void setQuantityProd(String quantityProd) {
        this.quantityProd = quantityProd;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate_cmd() {
        return date_cmd;
    }

    public void setDate_cmd(String date_cmd) {
        this.date_cmd = date_cmd;
    }


}
