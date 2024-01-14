package BUSINESS.Ex1;

import BUSINESS.Shoe;

import java.text.DecimalFormat;
import java.util.*;

public class BranchAndBound {
    private List<Shoe> shoes;
    private ArrayList<ArrayList<Shoe>> bestOption;
    private int minBoxes;
    private int minCounter;

    public BranchAndBound(List<Shoe> shoes) {
        this.shoes = shoes;
        minBoxes = (int) Math.ceil(shoes.size() / 6.0);
        minCounter = Integer.MAX_VALUE;
    }
    //Funcio principal que aplica el metode iteratiu de Branch and Bound
    public void bnb(){
        long startTime = System.currentTimeMillis(); // Tiempo de inicio

        BoxConfig initialConfig = new BoxConfig(shoes);
        PriorityQueue<BoxConfig> pq = new PriorityQueue<>();

        pq.offer(initialConfig);


        while (!pq.isEmpty()) {
            BoxConfig current = pq.poll();

            List<BoxConfig> successors = current.expandir();

            for(BoxConfig fill : successors) {
                if (fill.isCompleted()) {
                    if (fill.cost() < minCounter) {
                        bestOption = fill.deepCopy(fill.getConfig());
                        minCounter = fill.cost();
                        if (minCounter == minBoxes) {
                            break;
                        }
                    }
                } else {
                    if (fill.cost() < minCounter) {
                        pq.offer(fill);
                    }
                }
            }
            //Si s'ha trobat una solucio amb el minim nombre de caixes possibles no fa falta buscar mes.
            if (minCounter == minBoxes) {
                break;
            }
        }
        long endTime = System.currentTimeMillis();

        //Mostrem el resultat per comprovar que tots els preus es troben a dintre de alguna caixa.
        for (List<Shoe> b : bestOption) {
            for (Shoe s : b) {
                System.out.println(s.getPrice());
            }
            System.out.println("Total Box cost: " + getTotalValue(b));
        }
        DecimalFormat df = new DecimalFormat("#.##");
        long elapsedTime = endTime - startTime;
        double seconds = (double) elapsedTime / 1000.0;
        System.out.println("Tiempo de ejecucion: " + elapsedTime+ " ms");

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
