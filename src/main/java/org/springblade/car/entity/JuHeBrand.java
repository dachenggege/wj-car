package org.springblade.car.entity;

import lombok.Data;

/**
 * @author bond
 * @date 2021/9/24 17:26
 * @desc
 */
@Data
public class JuHeBrand {
    //id	brand_name	first_letter	brand_logo
    private int id;
    private String brand_name;
    private String first_letter;
    private String brand_logo;
}
