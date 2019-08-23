package management.elasticsearch.demo.service.impl;

import management.elasticsearch.demo.entity.Book;
import management.elasticsearch.demo.entity.UserInfo;
import management.elasticsearch.demo.repository.BookElasticSearchRepository;
import management.elasticsearch.demo.repository.ElasticSearchRepository;
import management.elasticsearch.demo.service.IElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author xiqiuwei
 * @Date Created in 15:04 2019/8/22
 * @Description
 * @Modified By:
 */
@Service
public class ElasticSearchServiceImpl implements IElasticSearchService {
    @Autowired
    private ElasticSearchRepository elasticSearchRepository;
    @Autowired
    private BookElasticSearchRepository bookElasticSearchRepository;

    @Override
    public UserInfo save(UserInfo userInfo) {
        return elasticSearchRepository.save(userInfo);
    }

    @Override
    public Book saveBookInfo(Book book) {
        return bookElasticSearchRepository.save(book);
    }
}
