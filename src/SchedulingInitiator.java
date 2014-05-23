// Written by Simon Tsai
//
// CS 431 - Operating Systems
//
// Project - Scheduling Algorithms

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sjtsaicpp.formatter.chart.GanttChartFormatter;
import sjtsaicpp.manager.file.FileManager;
import sjtsaicpp.manager.file.FileManagerCouldNotOpenFileException;
import sjtsaicpp.manager.file.FileManagerDoesNotContainFileException;
import sjtsaicpp.os.job.Job;
import sjtsaicpp.os.scheduling.FCFS;
import sjtsaicpp.os.scheduling.RR;
import sjtsaicpp.os.scheduling.SJF;
import sjtsaicpp.os.scheduling.Scheduler;

public class SchedulingInitiator {
    
    public static void main(String[] args) throws FileNotFoundException {
        // JOBS FROM testdata1.txt
        runAllSchedulersWithFile("testdata1.txt");
        
        // JOBS FROM testdata2.txt
        runAllSchedulersWithFile("testdata2.txt");
        
        // JOBS FROM testdata3.txt
        runAllSchedulersWithFile("testdata3.txt");
    }
    
    private static Job[] jobsWithFile(String fileName) {
        // Contains jobs with job name and burst time from provided file
        LinkedList<Job> jobs = new LinkedList<Job>();
        // Open the file
        FileManager fm = openFile(fileName);
        // Regex to parse numbers
        Pattern p = Pattern.compile("[0-9]+");
        // Get the number portion of the job names, and store it in the
        // job lists as a new Job along with its burst time
        if (fm != null) {
            try {
                while (fm.fileHasNextLine(false)) {
                    Matcher m = p.matcher(fm.getNextLine());
                    m.find();
                    String jobNumber = m.group();
                    int burstTime = fm.getNextInt();
                    fm.getNextLine();
                    
                    jobs.add(new Job(jobNumber, burstTime));
                }
            } 
            catch (FileManagerCouldNotOpenFileException e) {} 
            catch (FileManagerDoesNotContainFileException e) {}
            // Return it as an array
            return jobs.toArray(new Job[jobs.size()]);
        }
        // Return null if file was unable to be opened
        return null;
    }
    
    // Opens the given file with custom class, FileManager
    private static FileManager openFile(String fileName) {
        FileManager fm = null;
        
        try {
            fm = new FileManager(fileName);
        } catch (FileManagerCouldNotOpenFileException e) {
            return null;
        }
        
        return fm;
    }
    
    // Creates and runs all the schedulers (First Come First Served, Shortest Job First,
    // Round Robin 3, Round Robin 5) while displaying the Gantt Chart for each schedule type
    private static void runAllSchedulersWithFile(String fileName) throws FileNotFoundException {
        System.out.println("JOB LIST: " + fileName);
        printJobInformationFromFile(fileName);
        System.out.println();
        // Scheduler for first come first serve
        Job[] jobsFCFS = jobsWithFile(fileName);
        Scheduler fcfs = new FCFS(jobsFCFS);
        // Scheduler for shortest job first
        Job[] jobsSJF = jobsWithFile(fileName);
        Scheduler sjf = new SJF(jobsSJF);
        // Scheduler for round robin w/ time slice of 3
        Job[] jobsRR3 = jobsWithFile(fileName);
        Scheduler rr3 = new RR(jobsRR3, 3);
        // Scheduler for round robin w/ time slice of 5
        Job[] jobsRR5 = jobsWithFile(fileName);
        Scheduler rr5 = new RR(jobsRR5, 5);
        // Add all schedulers to list and display their Gantt Charts and other information
        LinkedList<Scheduler> schedulers = new LinkedList<Scheduler>();
        schedulers.add(fcfs);
        schedulers.add(sjf);
        schedulers.add(rr3);
        schedulers.add(rr5);
        displayGanttChartForSchedulers(schedulers);
    }
    
    // Custom class, GanttChartFormatter displays the GanttChart for any object that is
    // of type Scheduler
    private static void displayGanttChartForSchedulers(LinkedList<Scheduler> schedulers) {
        for (Scheduler scheduler : schedulers) {
            // Display the Gantt Chart with a maximum column width of 80
            GanttChartFormatter.displayGanttChart(scheduler, 80);
            System.out.println();
        }
    }
    
    private static void printJobInformationFromFile(String fileName) throws FileNotFoundException {
        final int column = 7;
        
        Scanner sc = new Scanner(new File(fileName));
        
        System.out.printf("%" + column + "s%" + column + "s%n", "Name", "Time");
        
        while (sc.hasNextLine()) {
            System.out.printf("%" + column + "s%" + column + "s%n", sc.nextLine(), sc.nextLine());
        }
        
        sc.close();
    }
}
