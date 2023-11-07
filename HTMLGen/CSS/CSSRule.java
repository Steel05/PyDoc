package CSS;

import Interfaces.Writable;

public class CSSRule implements Writable{
    private final String attribute;
    private final String value;

    private final CSSUnit unit;

    public CSSRule(String attribute, String value){
        this.attribute = attribute;
        this.value = value;
        this.unit = CSSUnit.nullUnit;
    }
    public CSSRule(String attribute, String value, CSSUnit unit){
        this.attribute = attribute;
        this.value = value;
        this.unit = unit;
    }

    public CSSUnit unit(){
        return unit;
    }
    public String attribute(){
        return attribute;
    }
    public String value(){
        return value;
    }

    public String write(){
        return toString();
    }
    
    @Override
    public String toString(){
        return String.format("%s: %s%s;", attribute, value, unit);
    }
}
