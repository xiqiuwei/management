package management.elasticsearch.demo.repository;

import management.elasticsearch.demo.entity.UserInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author xiqiuwei
 * @Date Created in 15:02 2019/8/22
 * @Description
 * @Modified By:
 */
public interface ElasticSearchRepository extends ElasticsearchRepository<UserInfo,Long> {
}
