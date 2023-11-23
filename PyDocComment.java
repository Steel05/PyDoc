import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PyDocComment {
    public static final String FUNCTIONFIND_REGEX = "(?<=def )[\\w-]+(?=\\([\\s\\S]*\\):)";
    public static final String CLASSFIND_REGEX = "(?<=class )[\\w-]+(?=[:(])";
    public static final String PARAMFIND_REGEX = "(?<=:param )[^\\n]+";
    public static final String RETURNFIND_REGEX = "(?<=:return )[^\\n]+";
    public static final String DESCRIPTIONFIND_REGEX = "(?<=\"\"\"d\\n)[\\{\\}\\[\\]\\(\\),\\.\\w\\s:;-]+?(?=\\n\\s*:?)";
    public static final String OPERATORFIND_REGEX = "(?<=ops :- )[^\\n]+";

    private final String rawComment;
    private final String description;
    private final String[] parameters;
    private final HashMap<String, String> paramDescriptions;
    private final HashMap<String, String> paramTypes;
    private final String returnDesctiption;
    private final String returnType;
    private final String name;
    private final boolean isClass;
    private final String supportedOperators;

    private boolean containedInClass = false;
    private String parentClassName = "None";

    public PyDocComment(String comment){
        rawComment = comment;

        Matcher descriptionMatcher = Pattern.compile(DESCRIPTIONFIND_REGEX).matcher(comment);
        // Null description case [Early return]
        if (!descriptionMatcher.find()){
            description = "";
            parameters = new String[0];
            paramDescriptions = null;
            paramTypes = null;
            returnDesctiption = "";
            returnType = "";
            name = "";
            isClass = false;
            supportedOperators = "";
            return;
        }

        description = comment.substring(descriptionMatcher.start(), descriptionMatcher.end());

        Matcher classMatcher = Pattern.compile(CLASSFIND_REGEX).matcher(comment);
        isClass = classMatcher.find();
        if (isClass){
            name = comment.substring(classMatcher.start(), classMatcher.end());
            returnDesctiption = "";
            returnType = "";
            parameters = new String[0];
            paramDescriptions = null;
            paramTypes = null;

            Matcher opMatcher = Pattern.compile(OPERATORFIND_REGEX).matcher(comment);
            if(!opMatcher.find()){
                supportedOperators = "";
                return;
            }
            supportedOperators = comment.substring(opMatcher.start(), opMatcher.end());

            return;
        }
        
        supportedOperators = "";
        Matcher functionMatcher = Pattern.compile(FUNCTIONFIND_REGEX).matcher(comment);
        functionMatcher.find();
        name = comment.substring(functionMatcher.start(), functionMatcher.end());

        if(name.substring(0, 2).equals("__")){
            
        }

        paramDescriptions = new HashMap<>();
        paramTypes = new HashMap<>();
        ArrayList<String> params = new ArrayList<>();

        Matcher paramMatcher = Pattern.compile(PARAMFIND_REGEX).matcher(comment);
        while (paramMatcher.find()){
            String[] paramParts = comment.substring(paramMatcher.start(), paramMatcher.end()).split(" :- ");
            String paramName = paramParts[0];
            params.add(paramName);
            paramDescriptions.put(paramName, paramParts[1]);
            paramTypes.put(paramName, paramParts[2]);
        }
        parameters = params.toArray(new String[params.size()]);

        Matcher returnMatcher = Pattern.compile(RETURNFIND_REGEX).matcher(comment);
        if (!returnMatcher.find()){
            returnType = "None";
            returnDesctiption = "";
            return;
        }

        String[] returnParts = comment.substring(returnMatcher.start(), returnMatcher.end()).split(" :- ");
        returnDesctiption = returnParts[0];
        returnType = returnParts[1];
    }

    public void bindParentClass(String parentClassName){
        this.parentClassName = parentClassName;
        containedInClass = true;
    }

    public boolean describesClass(){
        return isClass;
    }
    public boolean describesFunction(){
        return !isClass;
    }

    public String getRawComment(){
        return rawComment;
    }
    public String name(){
        return name;
    }
    public String description(){
        return description;
    }
    public String returnDescription(){
        return returnDesctiption;
    }
    public String returnType(){
        return returnType;
    }
    public String[] parameters(){
        return parameters;
    }
    public String[] getParamInfo(String param){
        return new String[]{ paramDescriptions.get(param), paramTypes.get(param) };
    }
    public String getSupportedOperators(){
        return supportedOperators;
    }

    public boolean containedInClass(){
        return containedInClass;
    }
    public String parentClassName(){
        return parentClassName;
    }

    @Override
    public String toString(){
        StringBuilder out = new StringBuilder();
        out.append(name + ": ");
        out.append("(" + (isClass ? "class" : "function") + ")\n");
        out.append(description);

        if (isClass){
            out.append("\nSupported Operators:\n\t" + supportedOperators);
            return out.toString();
        }

        out.append("\nTakes in:\n");
        for (String param : parameters){
            String[] paramInfo = getParamInfo(param);
            out.append(String.format("\t%s (%s): %s\n", param, paramInfo[0], paramInfo[1]));
        }
        if (!returnDesctiption.isEmpty()){
            out.append("Returns (" + returnType + ") - " + returnDesctiption);
        }

        return out.toString();
    }
}
