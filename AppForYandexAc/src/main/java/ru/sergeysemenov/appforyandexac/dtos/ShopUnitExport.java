package ru.sergeysemenov.appforyandexac.dtos;
import lombok.Getter;
import lombok.Setter;
import ru.sergeysemenov.appforyandexac.entities.ShopUnit;

import java.time.OffsetDateTime;
import java.util.List;


@Setter
@Getter
public class ShopUnitExport {
    private String id;
    private String name;
    private String type;
    private Long price;
    private String parentId;
    private OffsetDateTime date;
    private List<ShopUnit> children;

}
