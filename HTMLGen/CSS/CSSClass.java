package CSS;

import java.util.ArrayList;

import Interfaces.Writable;

public class CSSClass implements Writable {
    private ArrayList<CSSRule> rules = new ArrayList<>();
    private final String name;

    protected CSSClass(String name){
        this.name = name;
    }

    public String getName(){
        if (name.charAt(0) == '.'){
            return name.substring(1);
        }
        return name;
    }
    public CSSClass addRules(CSSRule... rules){
        for (CSSRule rule : rules){
            this.rules.add(rule);
        }
        return this;
    }

    public String write(){
        StringBuilder builder = new StringBuilder();
        builder.append(name).append("{\n");
        for (CSSRule rule : rules){
            builder.append("\t").append(rule.write()).append("\n");
        }
        builder.append("}");

        return builder.toString();
    }
}
