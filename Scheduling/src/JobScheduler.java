import java.util.ArrayList;

public class JobScheduler
{
    private int nJobs;
    private Job[]  jobs;

    public JobScheduler( int[] joblength, int[] deadline, int[] profit)
    {
        //Set nJobs
        //Fill jobs array. The kth job entered has JobNo = k;
        nJobs = joblength.length;
        jobs = new Job[nJobs];
        for (int i = 0; i < nJobs; i++) {
            jobs[i] = new Job(i, joblength[i], deadline[i], profit[i]);
        }
    }

    public void printJobs()
    //prints the array jobs
    {
        for (int i = this.nJobs-1; i >= 0; i--)
        {
            System.out.println(jobs[i].toString());
        }
    }

    //Brute force. Try all n! orderings. Return the schedule with the most profit
    public Schedule bruteForceSolution()
    {
        Schedule bruteForce = new Schedule();

        //TODO

        return bruteForce;
    }


    public Schedule makeScheduleEDF()
    //earliest deadline first schedule. Schedule items contributing 0 to total profit last
    {
        Schedule earliestDeadline = new Schedule();

        //TODO

        return earliestDeadline;
    }

    public Schedule makeScheduleSJF()
    //shortest job first schedule. Schedule items contributing 0 to total profit last
    {
        Schedule shortestJob = new Schedule();

        //TODO

        return shortestJob;
    }

    public Schedule makeScheduleHPF()
    //highest profit first schedule. Schedule items contributing 0 to total profit last
    {
        Schedule highestProfit = new Schedule();

        //TODO

        return highestProfit;
    }


    public Schedule newApproxSchedule()
    //Your own creation. Must be <= O(n3)
    {
        Schedule approxSchedule = new Schedule();

        //TODO

        return approxSchedule;
    }

}//end of JobScheduler class

//---------------------------Include Job and Schedule classes in JobScheduler. java-----------------------------
class Job
{
    int jobNumber;
    int length;
    int deadline;
    int profit;
    int start;
    int finish;


    public Job( int jn , int len, int d, int p)
    {
        jobNumber = jn;
        length = len;
        deadline = d;
        profit = p;
        start = -1;
        finish = -1;
    }


    public String toString()
    {
        return "#" + jobNumber + ":(" + length + ","
                + deadline + "," + profit +
                "," + start + "," + finish + ")";
    }

}//end of Job class



// ----------------------------------------------------
class Schedule
{
    ArrayList<Job> schedule;
    int profit;

    public Schedule()
    {
        profit = 0;
        schedule = new ArrayList<Job>();
    }

    public void add(Job job)
    {
        this.schedule.add(job);
    }


    public int getProfit()
    {
        return 1;
    }

    public String toString()
    {
        String s = "Schedule Profit = " + profit ;
        for(int k = 0 ; k < schedule.size(); k++)
        {
            s = s + "\n"  + schedule.get(k);
        }

        return s;
    }
}// end of Schedule class

class Permutations
{
    int size;
    int[] arr;
    public Permutations(int size)
    {
        this.size = size;
        arr = new int[size];
        for (int i = 0; i < this.size; i++) {
            arr[i] = i;
        }
        permute(arr, 0, size-1);
    }
    private void permute(int[] a, int l, int r)
    {
        if (l == r){
            for (int i = 0; i < a.length; i++) {
                System.out.print(a[i] + ",");
            }
            System.out.println("");
        }
        else
        {
            for (int i = l; i <= r; i++)
            {
                a = swap(a,l,i);
                permute(a, l+1, r);
                a = swap(a,l,i);
            }
        }
    }

    public int[] swap(int[] a, int i, int j)
    {
        int temp;
        temp = a[i] ;
        a[i] = a[j];
        a[j] = temp;
        return a;
    }
}