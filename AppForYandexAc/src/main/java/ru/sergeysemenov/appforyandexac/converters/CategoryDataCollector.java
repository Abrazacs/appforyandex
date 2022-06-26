package ru.sergeysemenov.appforyandexac.converters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDataCollector {
    private Long price;
    private OffsetDateTime date;
    private int offersCount;
}
