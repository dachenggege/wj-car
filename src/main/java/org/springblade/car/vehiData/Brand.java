package org.springblade.car.vehiData;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("v_brand")
public class Brand {
    private Integer id;
    private String name;
    private String initial;
    private Integer parentid;
    private String logo;
    private Integer depth;
}
