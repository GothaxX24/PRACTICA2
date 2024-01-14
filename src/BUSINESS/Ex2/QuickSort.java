package BUSINESS.Ex2;

import BUSINESS.Shoe;

import java.util.List;
import java.util.Random;

public class QuickSort {

    //Principal funcio per ordenar la llista abans de aplicar Greedy
    public void quickSort(List<Shoe> shoes, int min, int max) {
        if (max <= min) {
            return;
        }
        Random rand = new Random();
        int pivotIndex = min + rand.nextInt(max - min + 1);
        Shoe pivot = shoes.get(pivotIndex);

        swap(shoes, pivotIndex, max);

        int pointer = partition(shoes, min, max, pivot);

        quickSort(shoes, min, pointer - 1);
        quickSort(shoes, pointer + 1, max);
    }

    private int partition(List<Shoe> shoes, int min, int max, Shoe pivot) {
        int i = min - 1;
        for (int j = min; j <= max - 1; j++) {
            if (shoes.get(j).getPrice() > pivot.getPrice()) {
                i++;
                swap(shoes, i, j);
            }
        }
        i++;

        swap(shoes, i, max);

        return i;
    }

    private void swap(List<Shoe> shoes, int i, int j) {
        Shoe temp = shoes.get(i);
        shoes.set(i, shoes.get(j));
        shoes.set(j, temp);
    }

}
