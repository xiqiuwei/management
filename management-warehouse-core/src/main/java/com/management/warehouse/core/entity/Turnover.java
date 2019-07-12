package com.management.warehouse.core.entity;

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
@TableName("tb_turnover")
public class Turnover extends Model<Turnover> {

    private static final long serialVersionUID = 1L;

    /**
     * 进出货流水表id
     */
    private Integer id;

    private Integer goodsId;

    /**
     * 类型，进货还是出货(in/out)
     */
    private String type;

    private LocalDateTime createTime;

    /**
     * 数量
     */
    private Integer count;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
