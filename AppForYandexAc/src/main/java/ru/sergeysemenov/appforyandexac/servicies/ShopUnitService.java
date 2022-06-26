package ru.sergeysemenov.appforyandexac.servicies;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergeysemenov.appforyandexac.converters.UnitConverter;
import ru.sergeysemenov.appforyandexac.dtos.ShopImportRequest;
import ru.sergeysemenov.appforyandexac.dtos.ShopUnitExport;
import ru.sergeysemenov.appforyandexac.dtos.ShopUnitImport;
import ru.sergeysemenov.appforyandexac.entities.ShopUnit;
import ru.sergeysemenov.appforyandexac.exceptions.ValidationException;
import ru.sergeysemenov.appforyandexac.repositories.ShopUnitRepository;

import java.time.*;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@AllArgsConstructor
public class ShopUnitService {
    private final ShopUnitRepository repository;
    private final String UUID_PATTERN = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$";
    private final String DATE_TIME_PATTERN = "^([+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24:?00)([.,]\\d+(?!:))?)?(\\17[0-5]\\d([.,]\\d+)?)?([zZ]|([+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$";
    private final UnitConverter converter;


    @Transactional
    public void saveOrUpdate(ShopImportRequest importRequest) throws ValidationException {
        if(!validateImportRequest(importRequest)) throw new ValidationException("Validation Failed");
        OffsetDateTime date = OffsetDateTime.parse(importRequest.getUpdateDate());
        List<ShopUnit> shopUnits = importRequest.getItems().stream()
                .map(shopUnitImport -> converter.convertImportUnitToShopUnit(shopUnitImport, date))
                .toList();
        repository.saveAll(shopUnits);
    }

    private boolean validateImportRequest(ShopImportRequest importRequest)  {
        // проверка формата даты
        if(!importRequest.getUpdateDate().matches(DATE_TIME_PATTERN)){
            return false;
        }
        for (ShopUnitImport importUnit: importRequest.getItems()) {
            // проверка id на UUID паттерн
            if (!importUnit.getId().matches(UUID_PATTERN)){
                return false;
            }
            // проверка имени на null
            if(importUnit.getName() == null){
                return false;
            }
            // проверка типа и корректного заполнения price
            switch (importUnit.getType()){
                case "OFFER" -> {
                    if(importUnit.getPrice().isEmpty() || importUnit.getPrice().get()<0)
                       return false;
                }
                case "CATEGORY" -> {
                    if(importUnit.getPrice().isPresent())
                        return false;
                }
                default -> {return false;}
            }
            }
        return true;
    }


    public void deleteById(String id) throws ValidationException {
        if(!isIdFormatOk(id)) throw new ValidationException("Validation Failed");
        repository.deleteById(id);
    }

    public ShopUnitExport findShopUnit (String id) throws ValidationException, NoSuchElementException {
        if(!isIdFormatOk(id)) throw new ValidationException("Validation Failed");
        ShopUnit shopUnit = repository.findById(id).orElseThrow(NoSuchElementException::new);
        return converter.convertUnitToExportUnit(shopUnit);
    }

    private boolean isIdFormatOk (String id){
        return id.matches(UUID_PATTERN);
    }
}

