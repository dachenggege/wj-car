package org.springblade.car.entity;

import lombok.Data;

/**
 * @author bond
 * @date 2021/9/25 17:41
 * @desc
 */
@Data
public class JuHeALL {
//brand_first_letter	brand_id	brand_name	series_id	series_name	group_name	level_name	model_id	name	guide_price	year	configuration
//
    private String brand_first_letter;
    private int brand_id;
    private String brand_name;
    private int series_id;
    private String series_name;

    private String group_name;
    private String level_name;
    private int model_id;
    private String name;
    private String guide_price;
    private String year;
    private String configuration;
}
