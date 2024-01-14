package BUSINESS.Ex1;

import BUSINESS.Shoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoxConfig implements Comparable<BoxConfig> {
    private ArrayList<ArrayList<Shoe>> config;
    private List<Shoe> shoes;
    private int lastShoe;
    private int minBoxes;

    //Constructor inicial per la inicialitzacio del primer element en la PriorityQueue, on el primer element sera la primera sabata que trobem.
    public BoxConfig(List<Shoe> shoes) {
        this.config = new ArrayList<>();
        this.shoes = shoes;
        Shoe firstShoe = shoes.get(0);
        ArrayList<Shoe> first_list = new ArrayList<>();
        first_list.add(firstShoe);
        config.add(first_list);
        lastShoe = 1;
        minBoxes = (int) Math.ceil(shoes.size() / 6.0);
    }

    public ArrayList<ArrayList<Shoe>> getConfig() {
        return config;
    }
    //Constructor privat per fer copies
    private BoxConfig(BoxConfig that) {

        this.config = deepCopy(that.config);
        this.lastShoe = that.lastShoe;
        this.shoes = that.shoes;
    }

    @Override
    public int compareTo(BoxConfig that) {
        return this.estimation() - that.estimation();
    }

    private int estimation() {
        return this.config.size() + minBoxes;
    }

    public boolean isCompleted() {
        return this.lastShoe == shoes.size();
    }
    //Metode que serveix per copiar les arraylist i que no comparteixin referencia entre solucions
    public ArrayList<ArrayList<Shoe>> deepCopy(ArrayList<ArrayList<Shoe>> original) {
        ArrayList<ArrayList<Shoe>> copy = new ArrayList<>();
        for (ArrayList<Shoe> innerList : original) {
            copy.add(new ArrayList<>(innerList));
        }
        return copy;
    }
    //Funcio que expandeix / crea els fills possibles a la hora de buscar la millor combinacio
    public List<BoxConfig> expandir() {
        List<BoxConfig> successors = new ArrayList<>();
        Shoe current_shoe = shoes.get(lastShoe);
        for (int j = 0; j <= config.size(); j++) {
            BoxConfig fill = new BoxConfig(this); // Fem copia de la conf. actual

            if (j < fill.config.size()) {
                ArrayList<Shoe> currentBox = fill.config.get(j);
                if (currentBox.size() < 6) {
                    currentBox.add(current_shoe);
                    if (getTotalValue(currentBox) < 1000) {
                        fill.lastShoe++;
                        successors.add(fill);
                    }
                }
            } else {
                // Tmb mirem el cas de que haguem de afegir la sabata a una nova caixa
                ArrayList<Shoe> newBox = new ArrayList<>();
                newBox.add(current_shoe);
                fill.config.add(newBox);
                fill.lastShoe++;
                successors.add(fill);
            }
        }

        return successors;
    }

    public int cost() {
        return this.config.size();
    }
    //Funcio que ens servira per mirar el preu de una caixa aplicant els descomptes.
    private int getTotalValue(List<Shoe> shoeBox) {
        Map<String, Integer> brandCount = new HashMap<>();

        int childShoes = 0;
        int underRate  = 0;
        int overRate = 0;
        int total_value = 0;

        for (Shoe s: shoeBox) {
            String brand = s.getName().split(" ")[0];
            brandCount.put(brand, brandCount.getOrDefault(brand, 0) + 1);
            childShoes += (s.getMaxSize() < 35) ? 1 : 0;
            underRate += (s.getRating() < 5) ? 1 : 0;
            overRate += (s.getRating() > 8 ) ? 1 : 0;
        }

        for (Shoe s: shoeBox) {
            String brand = s.getName().split(" ")[0];
            double price = s.getPrice();
            if (childShoes >= 2 && s.getMaxSize() < 35) {
                price *= 0.65;
            }
            if (underRate >= 3 && s.getRating() < 5) {
                price *= 0.60;
            }
            if (overRate >= 3 && s.getRating() > 8) {
                price *= 1.20;
            }
            if (brandCount.containsKey(brand) && brandCount.get(brand) >= 2) {
                price *= 0.80;
            }
            total_value += price;
        }

        return total_value;
    }

}
