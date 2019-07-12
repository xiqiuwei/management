package com.management.warehouse.core.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiqiuwei
 * @since 2019-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_goods")
public class Goods extends Model<Goods> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String description;

    private BigDecimal price;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
