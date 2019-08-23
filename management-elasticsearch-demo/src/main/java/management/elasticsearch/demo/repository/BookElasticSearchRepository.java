package management.elasticsearch.demo.repository;

import management.elasticsearch.demo.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author xiqiuwei
 * @Date Created in 11:21 2019/8/23
 * @Description
 * @Modified By:
 */
public interface BookElasticSearchRepository extends ElasticsearchRepository<Book,Long> {
}
