package ru.sergeysemenov.appforyandexac.entities;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "shop_units")
@Getter
@Setter
public class ShopUnit {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column (name = "type", nullable = false)
    private String type;

    @Column (name = "price")
    private Long price;

    @Column (name = "parent_id")
    private String parentId;

    @Column (name = "date")
    private OffsetDateTime date;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private List<ShopUnit> children;

}
