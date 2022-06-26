package ru.sergeysemenov.appforyandexac.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class ShopUnitImport {
    private String id;
    private String name;
    private String type;
    private Optional<String> parentId;
    private Optional<Long> price;
}
