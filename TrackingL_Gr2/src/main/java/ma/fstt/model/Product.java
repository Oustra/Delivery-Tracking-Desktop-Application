package ma.fstt.model;

public class Product {
    private long id_products;
    private String product_name;
    private String brand;
    private String status;
    private String price;

    public Product(long id_products, String product_name, String brand, String status, String price) {
        this.id_products = id_products;
        this.product_name = product_name;
        this.brand = brand;
        this.status = status;
        this.price = price;
    }

    public long getId_products() {
        return id_products;
    }

    public void setId_products(long id_products) {
        this.id_products = id_products;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
