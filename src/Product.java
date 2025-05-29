public class Product {
    public String name;
    public String brand;
    public double price;
    public String description;
    public String imagePath;

    public Product(String name, String brand, double price, String description, String imagePath) {
        this.name = name;
        this.brand =brand;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
    }

      public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}