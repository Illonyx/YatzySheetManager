package com.bigbeard.yatzystats.ui;

public enum UiSceneRole {
    STARTING_SCENE(0), GAME_MODE_SCENE(1),
    GAMES_CHOICE_SCENE(2), STATS_MODE_SCENE(3), CONFRONTATIONS_SCENE(4),
    CREATE_SHEET_SCENE(5);

    private int order;

    UiSceneRole(int order){
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public static UiSceneRole fromValue(int value) {
        for(UiSceneRole s : UiSceneRole.values()){
            if(s.order == value) return s;
        }
        return null;
    }

    public static UiSceneRole getLastScene(UiSceneRole role){
        int currentSceneOrder = role.getOrder();
        int lastSceneOrder = --currentSceneOrder;
        if(lastSceneOrder < 0) return null;
        else return UiSceneRole.fromValue(lastSceneOrder);
    }

    public static UiSceneRole getNextScene(UiSceneRole role){
        int currentSceneOrder = role.getOrder();
        int nextSceneOrder = ++currentSceneOrder;
        if(nextSceneOrder >= UiSceneRole.values().length) return null;
        else return UiSceneRole.fromValue(nextSceneOrder);
    }

}
