/**
 * Gabriel Sturtevant
 * Project 1
 * Comp 496ALG
 * Feb 28th 2017
 */

import java.lang.Math;

public class Sorts
{
    public static void main(String[] args) {
        int number_size = 1000000;
        int cardinality = 1;
        int repetitions = 5;
        int sets = 7;
        System.out.println("Merge Sort, average of " + repetitions + " runs: ");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.format("%-1s %7s ", "|", "n");
        System.out.format("%-1s %15s ", "|", "C(n)");
        System.out.format("%-1s %15s ", "|", "C(n)/O(n)");
        System.out.format("%-1s %15s ", "|", "T(n)");
        System.out.format("%-1s %15s %1s %n", "|", "T(n)/O(n)", "|");
        System.out.println("-----------------------------------------------------------------------------------");
        for (int i = 0; i < sets; i++) {

            long count = 0;

            long time = 0;

            if(i == (sets - 1))
            {
                cardinality *= 5;
            }else
            {
                cardinality *= 10;
            }

            for (int j = 0; j < repetitions; j++) {

                long start;
                long end;
                int[] nums = new int[cardinality];

                for (int k = 0; k < cardinality; k++) {
                    nums[k] = (int)(Math.random() * number_size);
                }
                start = System.nanoTime();
                count += mergesort(nums);
                end = System.nanoTime();
                time += end - start;

                if(!isSorted(nums))
                {
                    System.err.println("Array not sorted!");
                    System.err.println("N: " +cardinality);
                    System.exit(1);
                }
            }

            double avgCount     = count/repetitions;
            double avgTime      = time/repetitions;
            double countCalc    = avgCount / (cardinality * (Math.log(cardinality)/Math.log(2)));
            double timeCalc     = avgTime /  (cardinality * (Math.log(cardinality)/Math.log(2)));

            System.out.format("%-1s %7d ", "|", cardinality);
            System.out.format("%-1s %15.2f ", "|", avgCount);
            System.out.format("%-1s %15.7f ", "|", countCalc);
            System.out.format("%-1s %15.2f ", "|", avgTime);
            System.out.format("%-1s %15.7f %1s %n", "|", timeCalc, "|");
        }
        System.out.println("-----------------------------------------------------------------------------------");
        cardinality = 1;
        System.out.println("\n\nInsertion Sort, average of " + repetitions + " runs:");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.format("%-1s %7s ", "|", "n");
        System.out.format("%-1s %15s ", "|", "C(n)");
        System.out.format("%-1s %15s ", "|", "C(n)/O(n)");
        System.out.format("%-1s %15s ", "|", "T(n)");
        System.out.format("%-1s %15s %1s %n", "|", "T(n)/O(n)", "|");
        System.out.println("-----------------------------------------------------------------------------------");
        for (int i = 0; i < sets; i++) {

            long count = 0;
            long time = 0;

            if(i == (sets - 2))
            {
                cardinality *= 2;
            }else if(i == (sets - 1))
            {
                cardinality *= 1.5;
            }else
            {
                cardinality *= 10;
            }

            for (int j = 0; j < repetitions; j++) {

                long start;
                long end;
                int[] nums = new int[cardinality];

                for (int k = 0; k < cardinality; k++) {
                    nums[k] = (int)(Math.random() * number_size);
                }
                start = System.nanoTime();
                count += insertionsort(nums);
                end = System.nanoTime();
                time += end - start;

                if(!isSorted(nums))
                {
                    System.err.println("Array not sorted!");
                    System.err.println("N: " +cardinality);
                    System.exit(2);
                }
            }

            double avgCount     = count/repetitions;
            double avgTime      = time/repetitions;
            double countCalc    = avgCount / ((Math.pow(cardinality, 2)));
            double timeCalc     = avgTime /  ((Math.pow(cardinality, 2)));

            System.out.format("%-1s %7d ", "|", cardinality);
            System.out.format("%-1s %15.2f ", "|", avgCount);
            System.out.format("%-1s %15.7f ", "|", countCalc);
            System.out.format("%-1s %15.2f ", "|", avgTime);
            System.out.format("%-1s %15.7f %1s %n", "|", timeCalc, "|");
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }
    /*--------------Insertion Sort -----------------------*/
    public static long insertionsort( int[] a)
    {
        long count = 0;
        int temp;
        for (int i = 1; i < a.length; i++) {
            for(int j = i ; j > 0 ; j--){
                count++;
                if(a[j] < a[j-1]){
                    temp = a[j];
                    a[j] = a[j-1];
                    a[j-1] = temp;
                }
            }
        }
        return count;
    }
/* --------------------Merge Sort --------------------*/
    //merges sorted slices a[i.. j] and a[j + 1 …  k] for 0<=  i <=j < k < a.length

    public static long merge ( int[] a,  int i, int j , int k)
    {
        int size = k-i+1;
        int[] temp = new int[size];
        int temp_i = i;
        int temp_j = j+1;
        long comps = 0;

        for (int l = 0; l < size ; l++) {
            if(temp_i == j+1)
            {
                temp[l] = a[temp_j++];
            }else if(temp_j == k+1)
            {
                temp[l] = a[temp_i++];
            }else if(a[temp_i] <= a[temp_j])
            {
                temp[l] = a[temp_i++];
                comps++;
            }else
            {
                temp[l] = a[temp_j++];
                comps++;
            }
        }
        for (int l = 0; l < size; l++) {
            a[i++] = temp[l];
        }
        return comps;
    }
    //sorts  a[ i .. k]  for 0<=i <= k < a.length
    private  static  long mergesort(int[] a,  int i ,  int k)
    {
        long comps = 0;
        if((k-i)<=0)
        {
            return comps;
        }else
        {
            int middle = (i + k) / 2;
            comps += mergesort(a, i, middle);
            comps += mergesort(a, middle + 1, k);
            comps += merge(a, i, middle, k);
        }
        return comps;
    }
    //Sorts the array using mergesort
    public static long mergesort(int[] a )
    {
        long comps = mergesort(a,0, a.length-1);
        return comps;
    }
    public static boolean isSorted( int[] a)
    {
        for (int i = 1; i < a.length; i++) {
            if(a[i]<a[i-1])
                return false;
        }
        return true;
    }
}
