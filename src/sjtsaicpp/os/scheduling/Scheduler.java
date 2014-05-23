// Written by Simon Tsai
//
// CS 431 - Operating Systems
//
// Project - Scheduling Algorithms

package sjtsaicpp.os.scheduling;

import java.util.HashMap;
import java.util.LinkedList;

import sjtsaicpp.os.job.Job;

public abstract class Scheduler implements Scheduling {
    
    protected Job[] jobs; // Jobs given to the scheduler
    protected HashMap<Integer, String> burstTimes; // Maps burst time to job name
    protected LinkedList<Integer> elapsedTimes; // Holds burst times in order of execution
    protected int totalCompletionTime;
    protected double meanTime;
    protected String schedulingType; // Name of the scheduling type.  i.e. Round-Robin-5
    
    public Scheduler(Job[] jobs) {
        this.jobs = jobs;
        burstTimes = new HashMap<Integer, String>();
        elapsedTimes = new LinkedList<Integer>();
    }
    
    public int getSize() {
        if (jobs != null) {
            return jobs.length;
        }
        
        return -1;
    }
    
    public String getSchedulingType() {
        return schedulingType;
    }
    
    public HashMap<Integer, String> getBurstTimes() {
        return burstTimes;
    }
    
    public LinkedList<Integer> getElapsedTimes() {
        return elapsedTimes;
    }
    
    public int getTotalCompletionTime() {
        return totalCompletionTime;
    }
    
    public double getMeanTime() {
        return meanTime;
    }
}
