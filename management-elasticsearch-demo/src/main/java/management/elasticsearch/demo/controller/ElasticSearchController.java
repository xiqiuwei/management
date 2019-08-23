package management.elasticsearch.demo.controller;

import management.elasticsearch.demo.entity.Book;
import management.elasticsearch.demo.entity.ResponseEntity;
import management.elasticsearch.demo.entity.UserInfo;
import management.elasticsearch.demo.service.IElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * @Author xiqiuwei
 * @Date Created in 14:55 2019/8/22
 * @Description
 * @Modified By:
 */
@RestController
@RequestMapping("elasticSearch")
public class ElasticSearchController {
    @Autowired
    private IElasticSearchService elasticSearchService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * @return management.elasticsearch.demo.entity.ResponseEntity
     * @Author xiqiuwei
     * @Date 14:59  2019/8/22
     * @Param [userInfo]
     * @Description 将用户信息保存到索引库中
     */
    @PostMapping("save")
    public ResponseEntity<UserInfo> saveUserInfoIntoRepository(@RequestBody UserInfo userInfo) {
        try {
            UserInfo save = elasticSearchService.save(userInfo);
            return ResponseEntity.success(save);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.fail("保存失败");
        }
    }

    /**
     * @return management.elasticsearch.demo.entity.ResponseEntity
     * @Author xiqiuwei
     * @Date 11:26  2019/8/23
     * @Param [book]
     * @Description
     */
    @PostMapping("saveBookInfo")
    public ResponseEntity saveBookInfoIntoRepository(@RequestBody Book book) {
        try {
            Book bookInfo = elasticSearchService.saveBookInfo(book);
            return ResponseEntity.success(bookInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.fail("保存失败");
        }
    }

    /**
     * @return management.elasticsearch.demo.entity.ResponseEntity<java.util.List               <               management.elasticsearch.demo.entity.UserInfo>>
     * @Author xiqiuwei
     * @Date 11:26  2019/8/23
     * @Param [word, name, pageable]
     * @Description
     */
    @GetMapping("query")
    public ResponseEntity<List<UserInfo>> queryUserInfoByWord(@RequestParam("userId") String word,
                                                              @RequestParam("age") String name,
                                                              @PageableDefault Pageable pageable) {
        try {
            SearchQuery query = new NativeSearchQueryBuilder()
                    .withQuery(queryStringQuery(word))
                    .withQuery(queryStringQuery(name))
                    .withPageable(pageable).build();
            List<UserInfo> userInfos = elasticsearchTemplate.queryForList(query, UserInfo.class);
            return ResponseEntity.success(userInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.fail("查询又出错了");
    }

    @GetMapping("queryBook")
    public ResponseEntity queryUserInfoByBimInfoId(@RequestParam("id") String id,
                                                   @RequestParam("bimInfoId") String bimInfoId,
                                                   @PageableDefault Pageable pageable) {
        try {
            SearchQuery query = new NativeSearchQueryBuilder()
                    .withQuery(queryStringQuery(id))
                    .withQuery(queryStringQuery(bimInfoId))
                    .withPageable(pageable).build();
            List<Book> bookList = elasticsearchTemplate.queryForList(query, Book.class);
            return ResponseEntity.success(bookList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.fail("查询又出错了");
    }
}
