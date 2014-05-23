// Written by Simon Tsai
//
// CS 431 - Operating Systems
//
// Project - Scheduling Algorithms

package sjtsaicpp.os.job;

import java.util.Comparator;

public class JobComparator implements Comparator<Job> {
    
    public int compare(Job target1, Job target2) {
        return target1.getBurstTime() - target2.getBurstTime();
    }
}
