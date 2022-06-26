package ru.sergeysemenov.appforyandexac.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopImportRequest {
    private List<ShopUnitImport> items;
    private String updateDate;

}
