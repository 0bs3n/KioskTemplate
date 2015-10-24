package net.androidengineer.kiosktemplate.objects;

/**
 * Created by James Campbell. All rights reserved.
 */
public class ProductItem {

    public String ImageFilePath;
    public String Name;
    public String Price;
    public String SKU;
    public String Description;
    public String Category;

    public ProductItem(String image, String name, String price, String sku,
                       String description, String category) {
        this.ImageFilePath = image;
        this.Name = name;
        this.Price = price;
        this.SKU = sku;
        this.Description = description;
        this.Category = category;
    }

    public String getImageFilePath() {
        return ImageFilePath;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }

    public String getSKU() {
        return SKU;
    }

    public String getDescription() {
        return Description;
    }

    public String getCategory() {
        return Category;
    }

}
