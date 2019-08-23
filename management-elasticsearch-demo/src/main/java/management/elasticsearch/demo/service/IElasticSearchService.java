package management.elasticsearch.demo.service;

import management.elasticsearch.demo.entity.Book;
import management.elasticsearch.demo.entity.UserInfo;

/**
 * @Author xiqiuwei
 * @Date Created in 15:00 2019/8/22
 * @Description
 * @Modified By:
 */
public interface IElasticSearchService {
    /**
     * 保存用户信息
     */
    UserInfo save(UserInfo userInfo);

    Book saveBookInfo(Book book);
}
