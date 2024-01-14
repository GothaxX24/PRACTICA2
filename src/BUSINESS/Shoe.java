package BUSINESS;

public class Shoe {

    private String name;
    private double price;
    private int minSize;
    private int maxSize;
    private int weight;
    private double rating;

    public Shoe(String name, double price, int minSize, int maxSize, int weight, double rating) {
        this.name = name;
        this.price = price;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.weight = weight;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public double getCombination() {
        return(((rating+1) / price) * (maxSize - minSize));
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}

