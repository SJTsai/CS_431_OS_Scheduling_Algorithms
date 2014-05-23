// Written by Simon Tsai
//
// CS 431 - Operating Systems
//
// Project - Scheduling Algorithms

package sjtsaicpp.manager.file;

public class FileManagerDoesNotContainFileException extends Exception {
    
    private static final long serialVersionUID = -4304187407372293185L;
    
    public FileManagerDoesNotContainFileException() {
        super();
    }
    
    public FileManagerDoesNotContainFileException(String message) {
        super(message);
    }
}
