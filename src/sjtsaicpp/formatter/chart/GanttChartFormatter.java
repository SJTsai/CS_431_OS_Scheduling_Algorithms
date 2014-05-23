// Written by Simon Tsai
//
// CS 431 - Operating Systems
//
// Project - Scheduling Algorithms

package sjtsaicpp.formatter.chart;

import java.util.HashMap;
import java.util.LinkedList;

import sjtsaicpp.os.scheduling.Scheduler;

public class GanttChartFormatter {
    
    private static final int startingColumn = 6;
    
    public static void displayGanttChart(Scheduler scheduler, int newLineColumnLimit) {
        System.out.println(scheduler.getSchedulingType());
        
        scheduler.execute();
        
        HashMap<Integer, String> burstTimes = scheduler.getBurstTimes();
        LinkedList<Integer> elapsedTimes = scheduler.getElapsedTimes();

        int nextStartingIndex = 0;
        int startingIndex = 0;
        int previousElapsedTime = 0;
        int listSize = elapsedTimes.size();
                
        while (nextStartingIndex < listSize) {
            int columnsUsed = startingColumn;
            
            for (int i = startingIndex; i < listSize; i++) {
                int currentElapsedTime = elapsedTimes.get(i);
                int columnsToAdd = numCol(currentElapsedTime, previousElapsedTime);
                // If number of columns to be added is less than the length of the
                // job name, expand the column so formatting appears correctly
                int jobNameLength = jobNameLength(burstTimes, currentElapsedTime);
                if (columnsToAdd <= jobNameLength) {
                    columnsToAdd += jobNameLength - columnsToAdd + 1;
                }
                // Display the portion of the Gantt Chart that fits within the specified
                // column width limit
                if (columnsUsed + columnsToAdd > newLineColumnLimit) {
                    nextStartingIndex = i;
                    break;
                } else { // Current job info can be displayed on this row.
                         // Proceed to test if next job can be displayed on this same row.
                    previousElapsedTime = currentElapsedTime;
                    columnsUsed += columnsToAdd;
                    // If we have reached the end of the list, then set the next starting index
                    // to be the size of the list, so we can exit out of the while loop
                    if (i == listSize - 1) {
                        nextStartingIndex = listSize;
                    }
                }
            }
            // Reset previous elapsed time to reuse for calculations.
            // If the starting index is 0, then it should be 0.
            // Otherwise, it should be the value held at the starting index.
            previousElapsedTime = resetPreviousElapsedTime(elapsedTimes, startingIndex);
            // Print out job numbers
            // 1. Print out title of row, Job#
            System.out.printf("%-" + startingColumn + "s", "Job#");
            // 2. Print out the job numbers with respect to the elapsed
            //    times that fit on that row
            for (int i = startingIndex; i < nextStartingIndex; i++) {
                // The elapsed time we are currently testing
                int currentElapsedTime = elapsedTimes.get(i);
                // Determine the number of columns to add based on current
                // and previous elapsed time ("previous" meaning the elapsed
                // time that was evaluated to still fit within the specified
                // column width limit)
                int columnsToAdd = numCol(currentElapsedTime, previousElapsedTime);
                // Expand column if it doesn't fit the length of the job name
                int jobNameLength = jobNameLength(burstTimes, currentElapsedTime);
                if (columnsToAdd <= jobNameLength) {
                    columnsToAdd += jobNameLength - columnsToAdd + 1;
                }
                // columnsToAdd includes the extra column for the vertical bar
                // in the Gantt Chart.  We subtract 1 to determine the actual
                // elapsed time.
                int columnsWithoutVerticalBar = columnsToAdd - 1;
                // Determine the placement of the job number
                int columnPosition;
                if (columnsWithoutVerticalBar % 2 == 0) {
                    columnPosition = columnsWithoutVerticalBar / 2;
                } else {
                    columnPosition = columnsWithoutVerticalBar / 2 + 1;
                }
                // Place job number in middle as best as possible,
                // using its length as part of the equation
                String jobNumber = burstTimes.get(currentElapsedTime);
                // This is the equation mentioned in the comment above
                columnPosition += jobNumber.length() / 2;
                // Print out the job number
                System.out.printf("%" + columnPosition + "s", burstTimes.get(currentElapsedTime));
                // Use columnPosition variable to add the rest of the space for the current
                // elapsed time.  If remaining space is 0, just make it 1.
                columnPosition = columnsToAdd - columnPosition;
                columnPosition = columnPosition > 0 ? columnPosition : 1;
                // Print remaining space
                System.out.printf("%" + columnPosition + "s", "");
                // Update previous elapsed time to the current elapsed time
                previousElapsedTime = currentElapsedTime;
            }
            // 3. Print dashed representation of elapsed time
            System.out.printf("%n%" + startingColumn + "s", "|");
            // Reset previous elapsed time to reuse for calculations.
            // If the starting index is 0, then it should be 0.
            // Otherwise, it should be the value held at the starting index.
            previousElapsedTime = resetPreviousElapsedTime(elapsedTimes, startingIndex);
            // Print dashes
            for (int i = startingIndex; i < nextStartingIndex; i++) {
                // The elapsed time we are currently testing
                int currentElapsedTime = elapsedTimes.get(i);
                // Determine the number of columns to add based on current
                // and previous elapsed time ("previous" meaning the elapsed
                // time that was evaluated to still fit within the specified
                // column width limit)
                int columnsToAdd = numCol(currentElapsedTime, previousElapsedTime);
                // Hold the elapsed time (currently stored in the var, columnsToAdd,
                // since columnsToAdd may increase if the job name length is greater
                // than its value
                int actualElapsedTime = columnsToAdd - 1;
                // Expand column if it doesn't fit the length of the job name
                boolean wasExpanded = false;
                int jobNameLength = jobNameLength(burstTimes, currentElapsedTime);
                if (columnsToAdd <= jobNameLength) {
                    columnsToAdd += jobNameLength - columnsToAdd + 1;
                    wasExpanded = true;
                }
                // columnsToAdd includes the extra column for the vertical bar
                // in the Gantt Chart.  We subtract 1 to determine the actual
                // elapsed time.
                int columnsWithoutVerticalBar = columnsToAdd - 1;
                // If columnsToAdd was adjusted to fit the job name, then determine where
                // to place the correct number of dashes
                int extraSpace = 0;
                boolean requiresRightPadding = false;
                if (actualElapsedTime != columnsWithoutVerticalBar) {
                    // Calculate the extra space needed to display before the dashes
                    // and possibly after the dashes
                    extraSpace = (columnsWithoutVerticalBar - actualElapsedTime) / 2;
                    // Determine if right padding (extra space after the dashes) is necessary
                    if (extraSpace != 0) {
                        requiresRightPadding = true;
                    }
                    // If extraSpace is 0, make it 1 for column formatting
                    extraSpace = extraSpace == 0 ? 1 : extraSpace;
                    System.out.printf("%" + extraSpace + "s", "");
                }
                // Print the dashes
                for (int j = 0; j < actualElapsedTime; j++) {
                    System.out.print("-");
                }
                // Print the vertical bar of if right padding required
                if (wasExpanded && requiresRightPadding) {
                    System.out.printf("%" + (extraSpace + 1) + "s", "|");
                } else {
                    System.out.print("|");
                }
                // Update previous elapsed time to the current elapsed time
                previousElapsedTime = currentElapsedTime;
            }
            // 4. Print the elapsed times
            // Reset previous elapsed time
            previousElapsedTime = resetPreviousElapsedTime(elapsedTimes, startingIndex);
            // Print the 0 at the beginning
            System.out.printf("%n%" + startingColumn + "d", previousElapsedTime);
            for (int i = startingIndex; i < nextStartingIndex; i++) {
                // The elapsed time we are currently testing
                int currentElapsedTime = elapsedTimes.get(i);
                // Determine the number of columns to add based on current
                // and previous elapsed time ("previous" meaning the elapsed
                // time that was evaluated to still fit within the specified
                // column width limit)
                int columnsToAdd = numCol(currentElapsedTime, previousElapsedTime);
                // Expand column if it doesn't fit the length of the job name
                int jobNameLength = jobNameLength(burstTimes, currentElapsedTime);
                if (columnsToAdd <= jobNameLength) {
                    columnsToAdd += jobNameLength - columnsToAdd + 1;
                }
                // Print the elapsed time
                System.out.printf("%" + columnsToAdd + "d", currentElapsedTime);
                // Set previous elapsed time to current elapsed time
                previousElapsedTime = currentElapsedTime;
            }
            // Since we have printed out the portion of the Gantt Chart that fits in the
            // specified column width, we can move the starting index to the beginning
            // of the portion that has not yet been processed.  We can also start a new
            // line.
            System.out.println("\n");
            startingIndex = nextStartingIndex;
        }
        // Print out total completion time, number of jobs, and mean time
        System.out.printf("%-23s%d ms%n",
                          "Total completion time: ", scheduler.getTotalCompletionTime());
        System.out.printf("%-23s%d%n", "Number of jobs: ", scheduler.getSize());
        System.out.printf("%-23s%f ms%n", "Mean completion time: ", scheduler.getMeanTime());
    }
    
    private static int numCol(int currentElapsedTime, int previousElapsedTime) {
        return currentElapsedTime - previousElapsedTime + 1;
    }
    
    private static int resetPreviousElapsedTime(LinkedList<Integer> elapsedTimes, 
                                                int startingIndex) {
        return startingIndex == 0 ? 0 : elapsedTimes.get(startingIndex - 1);
    }
    
    private static int jobNameLength(HashMap<Integer, String> burstTimes, int elapsedTime) {
        return burstTimes.get(elapsedTime).length();
    }
}
