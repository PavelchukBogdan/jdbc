package db.entity;

import db.entity.Category;
import lombok.*;

/**
 * Product -
 *
 * @author Павельчук Богдан (pavelchuk.b)
 * @since 15.07.2020
 */

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    private Integer id;
    private String name;
    private Category category;
}
