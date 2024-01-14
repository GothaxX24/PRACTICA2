package PERSISTENCE;

import BUSINESS.Shoe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FilesDAO {

    private String filePath = "files/";

    public ArrayList<Shoe> readShoeFile(String dataset) {

        ArrayList<Shoe> shoes = new ArrayList<>();

        try {
            File file = new File(filePath + dataset + ".txt");
            Scanner scanner = new Scanner(file);

            int num_shoes = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < num_shoes; i++) {
                String[] dadesSabata = scanner.nextLine().split(";");
                String nom = dadesSabata[0];
                double price = Double.parseDouble(dadesSabata[1].replace(',', '.'));
                int minSize = Integer.parseInt(dadesSabata[2]);
                int maxSize = Integer.parseInt(dadesSabata[3]);
                int weight = Integer.parseInt(dadesSabata[4]);
                double puntuacio = Double.parseDouble(dadesSabata[5].replace(',', '.'));

                Shoe shoe = new Shoe(nom, price, minSize, maxSize, weight, puntuacio);

                shoes.add(shoe);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return shoes;
    }

    public int getMaxLines(String dataset) {
        int maxLines = 0;

        try {
            File file = new File(filePath + dataset + ".txt");
            Scanner scanner = new Scanner(file);
            maxLines = scanner.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return maxLines;
    }
}

