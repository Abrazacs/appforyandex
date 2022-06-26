package ru.sergeysemenov.appforyandexac.converters;


import org.springframework.stereotype.Component;
import ru.sergeysemenov.appforyandexac.dtos.ShopUnitExport;
import ru.sergeysemenov.appforyandexac.dtos.ShopUnitImport;
import ru.sergeysemenov.appforyandexac.entities.ShopUnit;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class UnitConverter {

    public ShopUnitExport convertUnitToExportUnit(ShopUnit unit) {
        ShopUnitExport exportUnit = new ShopUnitExport();
        exportUnit.setId(unit.getId());
        exportUnit.setName(unit.getName());
        exportUnit.setType(unit.getType());
        exportUnit.setParentId(unit.getParentId());

        switch (unit.getType()) {
            case "OFFER" -> {
                exportUnit.setPrice(unit.getPrice());
                exportUnit.setDate(unit.getDate());
            }
            case "CATEGORY" -> {
                exportUnit.setChildren(unit.getChildren());
                if(!unit.getChildren().isEmpty()){
                    CategoryDataCollector collector = new CategoryDataCollector(0l,unit.getDate(),0);
                    collector = collectData(unit.getChildren(), collector);
                    exportUnit.setPrice(collector.getPrice()/collector.getOffersCount());
                    exportUnit.setDate(collector.getDate());
                }
            }
        }
        return exportUnit;
    }

    public ShopUnit convertImportUnitToShopUnit(ShopUnitImport importUnit, OffsetDateTime date) {
        ShopUnit shopUnit = new ShopUnit();
        shopUnit.setId(importUnit.getId());
        shopUnit.setName(importUnit.getName());
        if (importUnit.getPrice().isPresent()) {
            shopUnit.setPrice(importUnit.getPrice().get());
        }
        shopUnit.setType(importUnit.getType());
        shopUnit.setDate(date);
        if (importUnit.getParentId().isPresent())
            shopUnit.setParentId(importUnit.getParentId().get());
        return shopUnit;
    }

    /*
    * Метод для реурсивного сбора данных по субкатегориям
    * Собираются данны о кол-ве товаров и их стоимости, а также о самой поздней дате обновления
    * */
    private CategoryDataCollector collectData(List<ShopUnit> unitList, CategoryDataCollector collector){
        for (ShopUnit unit:unitList) {
            if(!unit.getChildren().isEmpty()){
                collectData(unit.getChildren(), collector);
            }
            if (unit.getPrice() != null) collector.setPrice(collector.getPrice() + unit.getPrice());
            if(unit.getDate().isAfter(collector.getDate()))collector.setDate(unit.getDate());
            if(unit.getType().equals("OFFER")) collector.setOffersCount(collector.getOffersCount()+1);
        }
        return collector;
    }
}


