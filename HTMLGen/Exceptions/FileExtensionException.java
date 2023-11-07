package Exceptions;

/**
 * Represents an error in the extension of the file.
 * <p>Ex. {@code FileName.txt.pdf}
 */
public class FileExtensionException extends FileNameException {
    public FileExtensionException(String fileName){
        super(fileName);
    }

    @Override
    public void printStackTrace(){
        super.printStackTrace();

        String[] nameParts = attemptedName.split("\\.");
        if (nameParts.length > 2){
            System.err.print("Contains multiple extensions: ");
            for (int i = 1; i < nameParts.length; i++){
                System.err.printf(".%s ", nameParts[i]);
            }
        }
        else if (nameParts.length == 2){
            System.err.printf("Invalid extension: .%s\n", nameParts[1]);
        }
        else{
            System.err.println("Missing file extension");
        }
    }
}
