package ru.sergeysemenov.appforyandexac.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sergeysemenov.appforyandexac.dtos.ShopImportRequest;
import ru.sergeysemenov.appforyandexac.exceptions.AppError;
import ru.sergeysemenov.appforyandexac.exceptions.ValidationException;
import ru.sergeysemenov.appforyandexac.servicies.ShopUnitService;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class ShopUnitController {
    private final ShopUnitService shopUnitService;


    @GetMapping("/nodes/{id}")
    public ResponseEntity<?> getShopUnitById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(shopUnitService.findShopUnit(id), HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(new AppError(404, "Item not found"), HttpStatus.NOT_FOUND);
        }catch (ValidationException e){
            return new ResponseEntity<>(new AppError(400, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/imports")
    public ResponseEntity<?> saveShopUnits(@RequestBody ShopImportRequest importRequest)  {
        try {
            shopUnitService.saveOrUpdate(importRequest);
        } catch (ValidationException e){
            return new ResponseEntity<>(new AppError(400, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteShopUnit(@PathVariable String id){
        try {
            shopUnitService.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(new AppError( 404, "Item not found"), HttpStatus.NOT_FOUND);
        }catch (ru.sergeysemenov.appforyandexac.exceptions.ValidationException e){
            return new ResponseEntity<>(new AppError(400, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
