package com.bigbeard.yatzystats.core.model.rules;

public enum SheetRulesIdentifiers {
   YATZY("Yatzy - Scandinavian Yathzee", "scandinavian-yatzy-rules.json"),
   MAXI_YATZY("Maxi Yatzy - 6 dice Scandinavian Yathzee", "scandinavian-maxiyatzy-rules.json");

   private String value;

   private String path;

   SheetRulesIdentifiers(String value, String path) {
       this.value=value;
       this.path=path;
   }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    public static SheetRulesIdentifiers fromValue(String value) {
       for(SheetRulesIdentifiers s : SheetRulesIdentifiers.values()){
           if(s.value.equals(value)) return s;
       }
       return null;
   }

    public static SheetRulesIdentifiers fromPath(String path) {
        for(SheetRulesIdentifiers s : SheetRulesIdentifiers.values()){
            if(s.path.equals(path)) return s;
        }
        return null;
    }

}
