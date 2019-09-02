package management.elasticsearch.demo.repository;

import management.elasticsearch.demo.entity.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author xiqiuwei
 * @Date Created in 9:03 2019/8/27
 * @Description
 * @Modified By:
 */
public interface StudentElasticSearchRepository extends ElasticsearchRepository<Student,String> {
}
