/**
 * Created by gabriel on 3/9/17.
 * Updated 3/10/17
 */
public class JavaTester
{
    public static void main(String[] args) {
        int[] lengths = new int[]{7,4,2,5};
        int[] deadline = new int[]{7,16,8,10};
        int[] profit = new int[]{10,9,14,13};
        JobScheduler scheduler = new JobScheduler(lengths, deadline, profit);

        System.out.println("Brute Force: ");
        Schedule schedule = scheduler.bruteForceSolution();
        System.out.println(schedule.toString());

        System.out.println("\nEarliest Deadline First: ");
        schedule = scheduler.makeScheduleEDF();
        System.out.println(schedule.toString());

        System.out.println("\nShortest Job First: ");
        schedule = scheduler.makeScheduleSJF();
        System.out.println(schedule.toString());

        System.out.println("\nHighest Profit First: ");
        schedule = scheduler.makeScheduleHPF();
        System.out.println(schedule.toString());

    }
}
