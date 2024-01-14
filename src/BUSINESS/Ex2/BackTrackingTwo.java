package BUSINESS.Ex2;

import BUSINESS.Shoe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BackTrackingTwo {
    private List<Shoe> bestShopA;
    private List<Shoe> bestShopB;
    private double minDifference = Integer.MAX_VALUE;
    private double totalA = 0, totalB = 0;
    private double priceLeft = 0;
    private List<Shoe> shoes;



    //Segona classe de Backtracking pel exercici 2
    public BackTrackingTwo(List<Shoe> shoes) {
        this.shoes = new ArrayList<>(shoes);
    }
    //Funcio per rebre el preu total que queda
    private void getTotalPrice() {
        priceLeft = 0;
        for (Shoe s: shoes) {
            priceLeft+= s.getPrice();
        }
    }
    private void getTotals() {
        totalA = 0;
        totalB = 0;
        for (Shoe s: bestShopA) {
        totalA += s.getPrice();
        }
        for (Shoe s: bestShopB) {
            totalB += s.getPrice();
        }
    }
    //Metode que inicialitza la recursivitat de backTracking, es pot triar entre backtracking amb o sense marcatge.
    public void backTrackingMode() {
        long startTime = System.currentTimeMillis(); // Tiempo de inicio

        bestShopA = null;
        bestShopB = null;
        minDifference = Double.MAX_VALUE;
        totalB = 0;
        totalA = 0;
        getTotalPrice();

        backtrackingNoMarcatge(new ArrayList<>(), new ArrayList<>(), 0, 0, 0);
        //backtrackingSiMarcatge(new ArrayList<>(), new ArrayList<>(), 0, 0, 0, priceLeft);
        long endTime = System.currentTimeMillis();

        for(Shoe s: bestShopA) {
            System.out.println(s.getPrice());
        }
        System.out.println();
        for(Shoe s: bestShopB) {
            System.out.println(s.getPrice());
        }

        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Sabates totals en A: " + bestShopA.size() + " Preu de A: " + df.format(totalA));
        System.out.println("Sabates totals en B: " + bestShopB.size() + " Preu de B: " + df.format(totalB));
        System.out.println("Diferencia entre A i B de " + df.format(minDifference));

        long elapsedTime = endTime - startTime;
        double seconds = (double) elapsedTime / 1000.0;
        System.out.println("Tiempo de ejecucion: " + elapsedTime+ " ms");


    }

    //Metode Backtracking amb algun error i sense marcatge
    private void backtrackingNoMarcatge(List<Shoe> currentShopA, List<Shoe> currentShopB, int index, double totalA, double totalB) {
        if (index == shoes.size()) {
            double currentDifference = Math.abs(totalA - totalB);
            if (bestShopA == null || currentDifference < minDifference) {
                bestShopA = new ArrayList<>(currentShopA);
                bestShopB = new ArrayList<>(currentShopB);
                minDifference = currentDifference;
                getTotals();
            }
            return;
        }

        Shoe actual_shoe = shoes.get(index);
        currentShopB.add(actual_shoe);
        backtrackingNoMarcatge(new ArrayList<>(currentShopA), new ArrayList<>(currentShopB), index + 1, totalA, totalB + shoes.get(index).getPrice());
        currentShopB.remove(currentShopB.size() - 1);

        currentShopA.add(actual_shoe);
        backtrackingNoMarcatge(new ArrayList<>(currentShopA), new ArrayList<>(currentShopB), index + 1, totalA + shoes.get(index).getPrice(), totalB);
        currentShopA.remove(currentShopA.size() - 1);

    }

    //Mateix metode backtracking pero amb marcatge i optimitzat
    private void backtrackingSiMarcatge(List<Shoe> currentShopA, List<Shoe> currentShopB, int index, double totalA, double totalB, double marcatge) {
        if (index == shoes.size()) {
            double currentDifference = Math.abs(totalA - totalB);
            if (bestShopA == null || currentDifference < minDifference) {
                bestShopA = new ArrayList<>(currentShopA);
                bestShopB = new ArrayList<>(currentShopB);
                minDifference = currentDifference;
                getTotals();
            }
            return;
        }

        if (Math.abs(totalA - totalB) - marcatge >= minDifference) {
            return;
        }
        Shoe actual_shoe = shoes.get(index);

        currentShopB.add(actual_shoe);
        backtrackingSiMarcatge(new ArrayList<>(currentShopA), new ArrayList<>(currentShopB), index + 1, totalA, totalB + shoes.get(index).getPrice(), (marcatge - actual_shoe.getPrice()));
        currentShopB.remove(currentShopB.size() - 1);


        currentShopA.add(actual_shoe);
        backtrackingSiMarcatge(new ArrayList<>(currentShopA), new ArrayList<>(currentShopB), index + 1, totalA + shoes.get(index).getPrice(), totalB,(marcatge - actual_shoe.getPrice()));
        currentShopA.remove(currentShopA.size() - 1);


    }




}
