package management.elasticsearch.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Author xiqiuwei
 * @Date Created in 11:18 2019/8/23
 * @Description
 * @Modified By:
 */
@Data
@Document(indexName = "books",type = "docs")
public class Book {
    @Id
    private Long id;
    private Long bimInfoId;
    private Long userId;
    private Integer state;
}
