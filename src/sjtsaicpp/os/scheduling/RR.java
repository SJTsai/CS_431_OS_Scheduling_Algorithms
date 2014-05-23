// Written by Simon Tsai
//
// CS 431 - Operating Systems
//
// Project - Scheduling Algorithms

package sjtsaicpp.os.scheduling;

import sjtsaicpp.os.job.Job;

public class RR extends Scheduler {
    
    private int timeSlice;

    public RR(Job[] jobs, int timeSlice) {
        super(jobs);
        this.timeSlice = timeSlice;
        schedulingType = String.format("ROUND-ROBIN-%d", timeSlice);
    }
    
    public int getTimeSlice() {
        return timeSlice;
    }

    public void execute() {
        int[] burstTimes = jobBurstTimes(); // Hold copy of all jobs' burst times
        boolean[] completed = new boolean[jobs.length]; // Jobs that are completed
        int jobsCompleted = 0; // Number of jobs that have been completed
        
        int total = 0;
        int elapsedTime = 0;
        int indexPointer = 0;
        
        // Loop through all the jobs until they have all been completed,
        // all the while keeping track of the total completion time and
        // the elapsed time for each job
        while (jobsCompleted < jobs.length) {
            // Process the job if it hasn't been completed yet
            if (!completed[indexPointer]) {
                // 1. Get the remaining burst time for the job
                int remainingBurstTime = burstTimes[indexPointer];
                // 2. Determine whether the job will be completed in this run.
                //    If it does, set the remaining burst time to 0, indicate
                //    that is has been completed, add the remaining burst time
                //    to the elapsed time, increment the number of jobs completed,
                //    and add the elapsed time to the total completion time of the jobs
                if (jobWillCompleteForRemainingBurstTime(remainingBurstTime)) {
                    burstTimes[indexPointer] = 0;
                    completed[indexPointer] = true;
                    elapsedTime += remainingBurstTime;
                    jobsCompleted++;
                    total += elapsedTime;
                } else { // Otherwise, simply add to the elapsed time the default time slice,
                         // and calculate the remaining burst time for the job
                    elapsedTime += timeSlice;
                    burstTimes[indexPointer] = remainingBurstTime - timeSlice;
                }
                
                super.burstTimes.put(elapsedTime, jobs[indexPointer].getJobName());
                elapsedTimes.add(elapsedTime);
            }
            // If index pointer is incremented and is past the end of the array,
            // go back to the beginning of the array
            if (++indexPointer == jobs.length) {
                indexPointer = 0;
            }
        }
        
        totalCompletionTime = total;
        meanTime = total / (double)jobs.length;
    }
    
    private boolean jobWillCompleteForRemainingBurstTime(int remainingBurstTime) {
        return remainingBurstTime <= timeSlice;
    }
    
    // Gets the burst times for all the jobs
    private int[] jobBurstTimes() {
        int[] burstTimes = new int[jobs.length];
        
        for (int i = 0; i < jobs.length; i++) {
            burstTimes[i] = jobs[i].getBurstTime();
        }
        
        return burstTimes;
    }
}
