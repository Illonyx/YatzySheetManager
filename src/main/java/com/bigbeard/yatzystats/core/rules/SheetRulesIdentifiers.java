package com.bigbeard.yatzystats.core.rules;

public enum SheetRulesIdentifiers {
   YATZY("Yatzy - Scandinavian Yathzee"),
   MAXI_YATZY("Maxi Yatzy - 6 dice Scandinavian Yathzee");

   private String value;

   SheetRulesIdentifiers(String value) {
    this.value=value;
   }

    public String getValue() {
        return value;
    }

    public static SheetRulesIdentifiers fromValue(String value) {
       for(SheetRulesIdentifiers s : SheetRulesIdentifiers.values()){
           if(s.value.equals(value)) return s;
       }
       return null;
   }

}
