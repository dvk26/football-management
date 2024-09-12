package com.webapp.ftm.enums;

import java.util.HashMap;
import java.util.Map;

public enum PlayerType {
    FOREIGN("Cầu thủ nước ngoài"),
    NATIONAL("Cầu thủ trong nước");

    private final String name;

    PlayerType(String name){
        this.name=name;
    }
    public static Map<String,String> goalType(){
        Map<String,String> typeCodes=new HashMap<>();
        for(PlayerType it: PlayerType.values()){
            typeCodes.put(it.toString(),it.name);
        }
        return typeCodes;
    }
}
