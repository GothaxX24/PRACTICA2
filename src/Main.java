import BUSINESS.*;
import BUSINESS.Ex1.BackTrackingOne;
import BUSINESS.Ex1.BranchAndBound;
import BUSINESS.Ex2.BackTrackingTwo;
import BUSINESS.Ex2.QuickSort;
import PERSISTENCE.FilesDAO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final FilesDAO filesDAO = new FilesDAO();
    private static List<Shoe> shoes = new ArrayList<>();
    private static final QuickSort quickSort= new QuickSort();
    private static BackTrackingOne backTrackingFirst;
    private static BackTrackingTwo backTrackingTwo;
    private static BranchAndBound branchAndBound;
    public static void main(String[] args) {
        shoes = filesDAO.readShoeFile("datasetL");
        backTrackingTwo = new BackTrackingTwo(shoes);
        backTrackingFirst = new BackTrackingOne(shoes);
        branchAndBound = new BranchAndBound(shoes);
        displayMenu();
    }

    //Menu de seleccio de exercici
    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Menu:");
        System.out.println("1. Enviament de caixes");
        System.out.println("2. Divisio d'inventari");
        System.out.println("0. Sortir");
        System.out.print("Escull una opcio: ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 ->  {
                handleEnviamentDeCaixes();
            }

            case 2 -> {
                handleMagatzems();
            }
            case 0 -> {
                System.out.println("Adeu!");
                System.exit(0);
            }
            default -> System.out.println("Opció no valida. Torna a intentar.");
        }

    }

    //Menu pel exercici 1
    private static void handleEnviamentDeCaixes() {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Seleccione una opcion:");
        System.out.println("1. Modo BnB");
        System.out.println("2. Modo Backtracking");
        System.out.println("3. Salir");
        System.out.print("Escull una opcio: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> branchAndBound.bnb();
            case 2 -> backTrackingFirst.backTrackingMode();
            case 3 -> {
                System.out.println("Saliendo del programa. ¡Hasta luego!");
                System.exit(0);
            }
            default -> System.out.println("Opcion no valida. Intentelo de nuevo.");
        }

    }



    //Menu pel exercici 2
    public static void handleMagatzems() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opcion:");
            System.out.println("1. Modo Greedy");
            System.out.println("2. Modo Backtracking");
            System.out.println("3. Salir");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> greedyMode();
                case 2 -> backTrackingTwo.backTrackingMode();
                case 3 -> {
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    System.exit(0);
                }
                default -> System.out.println("Opcion no valida. Intentelo de nuevo.");
            }
        }
    }


    //Opcio GreedyMode per la opcio2
    private static void greedyMode() {
        double totalA = 0, totalB = 0;
        List<Shoe> shopA = new ArrayList<>();
        List<Shoe> shopB = new ArrayList<>();
        long startTime = System.currentTimeMillis(); // Tiempo de inicio

        quickSort.quickSort(shoes,0,shoes.size()-1);

        for (Shoe s :shoes) {
            if (s.getPrice() + totalA > totalB) {
                shopB.add(s);
                totalB+= s.getPrice();
            } else {
                shopA.add(s);
                totalA += s.getPrice();
            }
        }
        long endTime = System.currentTimeMillis();

        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Sabates totals en A: " + shopA.size() + " Preu de A: " + df.format(totalA));
        System.out.println("Sabates totals en B: " + shopB.size() + " Preu de B: " + df.format(totalB));
        System.out.println("Diferencia entre A i B de " + df.format(Math.abs(totalA - totalB)));

        long elapsedTime = endTime - startTime;
        double seconds = (double) elapsedTime / 1000.0;
        System.out.println("Tiempo de ejecucion: " + elapsedTime+ " ms");


    }
}
