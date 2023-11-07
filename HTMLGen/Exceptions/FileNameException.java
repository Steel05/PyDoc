package Exceptions;

/**
 * Represents an error in a file name.
 * <p>Ex. {@code .txt}
 */
public class FileNameException extends Exception{
    protected String attemptedName;

    public FileNameException(String fileName){
        attemptedName = fileName;
    }

    @Override
    public void printStackTrace(){
        super.printStackTrace();

        System.err.printf("Cannot write file with name \"%s\"\n", attemptedName);   
    }
}
