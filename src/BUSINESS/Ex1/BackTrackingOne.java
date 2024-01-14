package BUSINESS.Ex1;

import BUSINESS.Shoe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackTrackingOne {

    private List<Shoe> shoes;
    private ArrayList<ArrayList<Shoe>> bestShoeBoxes;

    private int minCounter = Integer.MAX_VALUE;
    private int minBoxes;

    //Constructor de la primera classe de Backtracking pel exercici 1
    public BackTrackingOne(List<Shoe> shoes) {
        this.shoes = shoes;
        minBoxes = (int) Math.ceil(shoes.size() / 6.0);
    }

    //Metode que inicialitza el BackTracking
    public void backTrackingMode() {
        long startTime = System.currentTimeMillis(); // Tiempo de inicio

        bestShoeBoxes = new ArrayList<>();
        ArrayList<ArrayList<Shoe>> config = new ArrayList<>();
        ArrayList<Shoe> firstBox = new ArrayList<>();

        firstBox.add(shoes.get(0));
        config.add(firstBox);
        backTracking4(config, 1);
        long endTime = System.currentTimeMillis();

        for (List<Shoe> b : bestShoeBoxes) {
            for (Shoe s : b) {
                System.out.println(s.getPrice());
            }
            System.out.println("Total Box cost: " + getTotalValue(b));
        }
        DecimalFormat df = new DecimalFormat("#.##");
        long elapsedTime = endTime - startTime;
        //double seconds = (double) elapsedTime / 1000.0;
        System.out.println("Tiempo de ejecucion: " + elapsedTime+ " ms");
    }
    //BackTracking final amb marcatge i poda aplicada
    private void backTracking4(ArrayList<ArrayList<Shoe>> config, int i) {
        if (minCounter == minBoxes) {
            return;
        }
        if (i == shoes.size()) {
            int currentBoxes = config.size();

            if (bestShoeBoxes == null || currentBoxes < minCounter) {
                bestShoeBoxes = deepCopy(config);
                minCounter = currentBoxes;
            }
            return;
        }

        if (config.size() > minCounter) {
            return;
        }

        Shoe current_shoe = shoes.get(i);

        for (int j = 0; j < config.size(); j++) {
            ArrayList<Shoe> c = config.get(j);
            if (config.get(j).size() < 6) {
                config.get(j).add(current_shoe);
                if (getTotalValue(config.get(j)) < 1000) {
                    backTracking4(deepCopy(config), i + 1);
                }
                config.get(j).remove(config.get(j).size() - 1);
            }
        }

        ArrayList<Shoe> new_box = new ArrayList<>();
        new_box.add(current_shoe);
        config.add(new_box);
        backTracking4(deepCopy(config), i + 1);
        config.remove(config.size() - 1);
    }
    //Mateixa funcio vist en altres classes, permet copiar les arrayList i fer que no comparteixin referencia.
    private ArrayList<ArrayList<Shoe>> deepCopy(ArrayList<ArrayList<Shoe>> original) {
        ArrayList<ArrayList<Shoe>> copy = new ArrayList<>();
        for (ArrayList<Shoe> innerList : original) {
            copy.add(new ArrayList<>(innerList));
        }
        return copy;
    }

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
