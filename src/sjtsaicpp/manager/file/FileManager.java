// Written by Simon Tsai
//
// CS 431 - Operating Systems
//
// Project - Scheduling Algorithms

package sjtsaicpp.manager.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileManager {
    private Scanner sc;
    private String fileName;
    
    public FileManager() {
        super();
    }
    
    public FileManager(String fileName) throws FileManagerCouldNotOpenFileException {
        this.fileName = fileName;
        openFile(fileName);
    }
    
    public boolean hasOpenFile() {
        return sc != null;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public void openFile(String fileName) throws FileManagerCouldNotOpenFileException {
        // Close any open file first
        closeFile();
        
        try {
            sc = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            throw new FileManagerCouldNotOpenFileException("FileManager contains a file that " +
                                                           "does not exist.");
        }
    }
    
    public void reopenFile() throws FileManagerCouldNotOpenFileException,
                                    FileManagerDoesNotContainFileException {
        if (hasOpenFile()) {
            closeFile();
            openFile(fileName);
        } else {
            if (fileName != null) {
                openFile(fileName);
            } else {
                throw new FileManagerDoesNotContainFileException("FileManager does not have a " +
                                                                 "file to work with.");
            }
        }
    }
    
    public void closeFile() {
        if (hasOpenFile()) {
            sc.close();
            sc = null;
        }
    }
    
    public boolean fileHasNextLine(boolean reopenIfEOF) throws 
                                                        FileManagerCouldNotOpenFileException, 
                                                        FileManagerDoesNotContainFileException {
        if (fileName == null) {
            throw new FileManagerDoesNotContainFileException("FileManager does not have a " +
                                                             "file to work with.");
        }
        
        if (sc == null) {
            return false;
        }
                
        if (!sc.hasNextLine()) {
            if (reopenIfEOF) {
                reopenFile();
            } else {
                closeFile();
            }
            
            return false;
        }
        
        return true;
    }
    
    public String getNextLine() {
        if (hasOpenFile()) {
            if (sc.hasNextLine()) {
                return sc.nextLine();
            } else {
                closeFile();
                return null;
            }
        } else {
            return null;
        }
    }
    
    public Integer getNextInt() {
        if (hasOpenFile()) {
            if (sc.hasNextInt()) {
                return sc.nextInt();
            } else {
                closeFile();
                return null;
            }
        } else {
            return null;
        }
    }
    
    public void printFileContent() throws FileManagerCouldNotOpenFileException, 
                                          FileManagerDoesNotContainFileException {
        if (hasOpenFile()) {
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
            
            reopenFile();
        }
    }
}
