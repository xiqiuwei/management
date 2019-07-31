package management.rabbitmq.demo.entity;



import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiqiuwei
 * @since 2019-07-12
 */
@Data
public class User {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String password;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;



}
