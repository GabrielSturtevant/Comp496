import java.util.ArrayList;
import java.util.PriorityQueue;

public class JobScheduler
{
    private int nJobs;
    private Job[]  jobs;

    public JobScheduler( int[] joblength, int[] deadline, int[] profit)
    {
        //Set nJobs
        //Fill jobs array. The kth job entered has JobNo = k;
    }

    public void printJobs()  //prints the array jobs
    {  }

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
        jobNumber = jn; length = len; deadline = d;
        profit = p;  start = -1;  finish = -1;
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
