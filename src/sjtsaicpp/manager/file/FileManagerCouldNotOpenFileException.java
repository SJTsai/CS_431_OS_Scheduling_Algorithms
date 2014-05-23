// Written by Simon Tsai
//
// CS 431 - Operating Systems
//
// Project - Scheduling Algorithms

package sjtsaicpp.manager.file;

public class FileManagerCouldNotOpenFileException extends Exception {
    
    private static final long serialVersionUID = -8852904402283086286L;

    public FileManagerCouldNotOpenFileException() {
        super();
    }
    
    public FileManagerCouldNotOpenFileException(String message) {
        super(message);
    }
}
