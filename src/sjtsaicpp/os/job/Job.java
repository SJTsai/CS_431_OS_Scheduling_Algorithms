// Written by Simon Tsai
//
// CS 431 - Operating Systems
//
// Project - Scheduling Algorithms

package sjtsaicpp.os.job;

public class Job implements Comparable<Job> {
    
    private String jobNumber;
    private int burstTime;
    
    public Job(String jobNumber, int burstTime) throws IllegalArgumentException {
        if (burstTime <= 0) {
            throw new IllegalArgumentException("Burst time must be greater than 0.");
        }
        
        this.jobNumber = jobNumber;
        this.burstTime = burstTime;
    }
    
    public String getJobName() {
        return jobNumber;
    }
    
    public int getBurstTime() {
        return burstTime;
    }
    
    public int compareTo(Job target) {
        return burstTime - target.getBurstTime();
    }
}
