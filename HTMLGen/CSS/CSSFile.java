package CSS;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Optional;

import Interfaces.Savable;
import Utils.FileUtils;

public class CSSFile implements Savable {
    private HashMap<String, CSSClass> classes = new HashMap<>();

    private boolean beenSaved = false;
    private String savedName = "";

    public CSSFile(){}

    public CSSClass createClass(String name){
        CSSClass c = new CSSClass(name);
        classes.put(name, c);
        return c;
    }

    public Optional<String> getSaveName(){
        return beenSaved ? Optional.of(savedName) : Optional.empty();
    }
    public void save(String fileName){
        if (beenSaved){
            System.out.println("File has already been saved");
            return;
        }

        Optional<String> saveName = FileUtils.parseFileName(fileName, "css");

        if (saveName.isEmpty()){
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (CSSClass c : classes.values()){
            builder.append(c.write()).append("\n\n");
        }

        DataOutputStream file;

        try{
            file = new DataOutputStream(new FileOutputStream(saveName.get()));
            char[] data = builder.toString().toCharArray();

            for (char character : data){
                file.write(character);
            }
            
            file.close();

            beenSaved = true;
            savedName = saveName.get();
            
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
