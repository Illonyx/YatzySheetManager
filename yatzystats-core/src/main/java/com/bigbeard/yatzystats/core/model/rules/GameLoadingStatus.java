package com.bigbeard.yatzystats.core.model.rules;

import com.bigbeard.yatzystats.core.model.sheets.SheetDto;

import java.util.List;

public class GameLoadingStatus {
    private List<String> loadingErrors;

    private List<SheetDto> sheetDtoList;

    public GameLoadingStatus(List<String> loadingErrors, List<SheetDto> sheetDtoList) {
        this.loadingErrors = loadingErrors;
        this.sheetDtoList = sheetDtoList;
    }

    public List<String> getLoadingErrors() {
        return loadingErrors;
    }

    public void setLoadingErrors(List<String> loadingErrors) {
        this.loadingErrors = loadingErrors;
    }

    public List<SheetDto> getSheetDtoList() {
        return sheetDtoList;
    }

    public void setSheetDtoList(List<SheetDto> sheetDtoList) {
        this.sheetDtoList = sheetDtoList;
    }
}
