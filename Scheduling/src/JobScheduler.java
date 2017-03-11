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
        Schedule bruteForce;
        Schedule keeper = new Schedule();
        int highestProfit = 0;

        Permutations permutations = new Permutations(this.nJobs);
        int[][] possibilities = permutations.toReturn;

        for (int i = 0; i < possibilities.length; i++) {
            bruteForce = new Schedule();
            for (int j = 0; j < possibilities[i].length; j++) {
                int index = possibilities[i][j];

                Job tempJob = new Job(
                        jobs[index].jobNumber,
                        jobs[index].length,
                        jobs[index].deadline,
                        jobs[index].profit
                );

                bruteForce.add(tempJob);
            }
            if(bruteForce.getProfit() > highestProfit) {
                highestProfit = bruteForce.getProfit();
                System.out.println("i: " + i);
                for (int j = 0; j < possibilities[i].length; j++) {
                    System.out.print(possibilities[i][j] + ",");
                }
                System.out.println("");
                keeper = bruteForce;
            }
        }
        return keeper;
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
        if(this.schedule.size() == 0) {
//            job.jobNumber = schedule.size();
            job.start = 0;
            job.finish = job.length + job.start;
            if(job.start < job.deadline){
                this.profit += job.profit;
            }
        } else {
            Job lastJob = this.schedule.get(this.schedule.size() - 1);
//            job.jobNumber = schedule.size();
            job.start = lastJob.finish;
            job.finish = job.start + job.length;
            if(job.start < job.deadline){
                this.profit += job.profit;
            }
        }
        this.schedule.add(job);
    }


    public int getProfit()
    {
        return this.profit;
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
    int length;
    int[] arr;
    int[][] toReturn;
    int index = 0;
    public Permutations(int size)
    {
        this.size = size;
        arr = new int[size];
        for (int i = 0; i < this.size; i++) {
            arr[i] = i;
        }
        this.length = factorial(size);
        toReturn = new int[this.length][this.size];
        permute(arr, 0, size-1);
    }
    private int factorial(int i){
        if (i == 1)
            return 1;
        else {
            return i * factorial(i-1);
        }
    }
    private void permute(int[] a, int l, int r)
    {
        if (l == r){
            for (int i = 0; i < a.length; i++) {
                this.toReturn[index][i] = a[i];
            }
            index++;
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