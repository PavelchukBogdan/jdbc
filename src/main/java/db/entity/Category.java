package db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Category -
 *
 * @author Павельчук Богдан (pavelchuk.b)
 * @since 15.07.2020
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Category {

    private Integer id;
    private String name;
}
