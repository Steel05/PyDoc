package Utils;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Optional;

import Exceptions.FileExtensionException;
import Exceptions.FileNameException;

public class FileUtils {
    public static void dumpString(String text, String fileName){
        try{
            DataOutputStream file;

            file = new DataOutputStream(new FileOutputStream(fileName));

            char[] data = text.toCharArray();

            //Write
            for (char character : data){
                file.write(character);
            }

            file.close();
        }
        catch(Exception e){
            System.err.println("ERROR: Dumping file");
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param fileName The desired name of the file
     * @param acceptableExtension The acceptable extension for the file name
     * @return
     */
    public static Optional<String> parseFileName(String fileName, String acceptableExtension){
        String[] fileNameParts = fileName.split("\\.");

        try{
            if (fileName.isBlank() || fileName.isEmpty()){
                throw new FileNameException(fileName);
            }
            else if (fileNameParts.length > 2){
                throw new FileExtensionException(fileName);
            }
            else if (fileNameParts.length == 2 && !fileNameParts[1].equals(acceptableExtension)){
                throw new FileExtensionException(fileName);
            }
        }
        catch(Exception e){
            System.out.println("Error saving file");
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(fileNameParts[0] + "." + acceptableExtension);
    }
}