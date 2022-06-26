package ru.sergeysemenov.appforyandexac.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sergeysemenov.appforyandexac.entities.ShopUnit;


import java.util.Optional;

@Repository
public interface ShopUnitRepository extends JpaRepository<ShopUnit, String> {



    @Query("SELECT AVG (u.price) FROM ShopUnit u WHERE u.parentId = :id")
    Optional<Long> findAvgCategoryPrice(@Param("id") String id);


}
