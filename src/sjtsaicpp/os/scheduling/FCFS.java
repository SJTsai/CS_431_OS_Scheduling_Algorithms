// Written by Simon Tsai
//
// CS 431 - Operating Systems
//
// Project - Scheduling Algorithms

package sjtsaicpp.os.scheduling;

import sjtsaicpp.os.job.Job;

public class FCFS extends Scheduler {
    
    public FCFS(Job[] jobs) {
        super(jobs);
        schedulingType = "FIRST-COME-FIRST-SERVED";
    }

    public void execute() {
        int total = 0; // Holds total for total completion time
        int elapsedTime = 0; // Holds elapsed time for each job
        
        // For each job, record the elapsed time and associated job name.
        // Then add the elapsed time to the running total completion time
        for (Job job : jobs) {
            int burstTime = job.getBurstTime();
            
            elapsedTime += burstTime;
            
            burstTimes.put(elapsedTime, job.getJobName());
            elapsedTimes.add(elapsedTime);
            
            total += elapsedTime;
        }
        
        totalCompletionTime = total;
        meanTime = total / (double)jobs.length;
    }
}
