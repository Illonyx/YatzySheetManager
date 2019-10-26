package com.bigbeard.yatzystats.ui;

public enum UiSceneRole {
    GAME_MODE_SCENE(0), GAMES_CHOICE_SCENE(1);

    private int order;

    public int getOrder() {
        return order;
    }

    public static UiSceneRole fromValue(int value) {
        for(UiSceneRole s : UiSceneRole.values()){
            if(s.order == value) return s;
        }
        return null;
    }

    UiSceneRole(int order){
        this.order = order;
    }
}
