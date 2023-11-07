package CSS;

public enum CSSUnit {
    cm("cm"),
    mm("mm"),
    in("in"),
    px("px"),
    pt("pt"),
    pc("pc"),
    em("em"),
    ex("ex"),
    ch("ch"),
    rem("rem"),
    vw("vw"),
    vh("vh"),
    vmin("vmin"),
    vmax("vmax"),
    percent("%"),
    /** {@code toString} returns an empty string */
    nullUnit("");

    private final String asString;
    private CSSUnit(String s){
        asString = s;
    }
    public String toString(){
        return asString;
    }

    public static CSSUnit parseUnit(String s){
        if (s.equals("%")){
            return percent;
        }

        if (s.isEmpty()){
            return nullUnit;
        }

        return CSSUnit.valueOf(s);
    }
}
