package com.bigbeard.yatzystats.core.model.rules;

import com.bigbeard.yatzystats.core.model.sheets.SheetDto;

import java.util.List;

public record GameLoadingStatus(List<String> loadingErrors, List<SheetDto> sheetDtoList)  {
}
