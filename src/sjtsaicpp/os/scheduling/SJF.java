// Written by Simon Tsai
//
// CS 431 - Operating Systems
//
// Project - Scheduling Algorithms

package sjtsaicpp.os.scheduling;

import java.util.Arrays;
import sjtsaicpp.os.job.*;

public class SJF extends FCFS {
    
    public SJF(Job[] jobs) {
        super(jobs);
        // Sort jobs array by shortest burst time first
        Arrays.sort(jobs, new JobComparator());
        schedulingType = "SHORTEST-JOB-FIRST";
    }
}
