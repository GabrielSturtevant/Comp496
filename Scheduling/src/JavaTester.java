/**
 * Created by gabriel on 3/9/17.
 */
public class JavaTester
{
    public static void main(String[] args) {
        int[] lengths = new int[]{7,4,2,5};
        int[] deadline = new int[]{7,16,8,10};
        int[] profit = new int[]{10,9,14,13};
        JobScheduler jobbers = new JobScheduler(lengths, deadline, profit);

//        jobbers.printJobs();
        Permutations foobar = new Permutations(3);
    }
}
