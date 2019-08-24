package management.elasticsearch.demo.controller;

import management.elasticsearch.demo.entity.Book;
import management.elasticsearch.demo.entity.ResponseEntity;
import management.elasticsearch.demo.entity.UserInfo;
import management.elasticsearch.demo.repository.ElasticSearchRepository;
import management.elasticsearch.demo.service.IElasticSearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@SuppressWarnings("all")
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
    @Autowired
    private ElasticSearchRepository elasticSearchRepository;

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
     * @return management.elasticsearch.demo.entity.ResponseEntity<java.util.List                                                               <                                                               management.elasticsearch.demo.entity.UserInfo>>
     * @Author xiqiuwei
     * @Date 11:26  2019/8/23
     * @Param [word, name, pageable]
     * @Description
     */
    @GetMapping("query")
    public ResponseEntity<List<UserInfo>> queryUserInfoByWord(@RequestParam("age") String name,
                                                              @PageableDefault Pageable pageable) {
        try {
            SearchQuery query = new NativeSearchQueryBuilder()
                    .withQuery(queryStringQuery(name))
                    .withPageable(pageable).build();
            List<UserInfo> userInfos = elasticsearchTemplate.queryForList(query, UserInfo.class);
            return ResponseEntity.success(userInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.fail("查询又出错了");
    }

    /**
     * @return management.elasticsearch.demo.entity.ResponseEntity
     * @Author xiqiuwei
     * @Date 15:36  2019/8/23
     * @Param [id, bimInfoId, pageable]
     * @Description 根据条件精确匹配
     */
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

    /**
     * @return management.elasticsearch.demo.entity.ResponseEntity
     * @Author xiqiuwei
     * @Date 15:45  2019/8/23
     * @Param [name]
     * @Description 根据关键字模糊匹配到查询的内容
     */
    @GetMapping("queryFuzzyQuery")
    public ResponseEntity<List<UserInfo>> queryUserInfoByFuzzyQuery(@RequestParam("name") String name) {
        // 构建查询条件
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // 模糊查询关键字
        builder.must(QueryBuilders.matchQuery("userName", name));
        // 构建需要排序的字段
        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("age").order(SortOrder.DESC);
        // 分页查询，一页显示5条数据从第一页开始
        PageRequest pageRequest = new PageRequest(0, 10);
        // 构建查询语句
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery build = nativeSearchQueryBuilder
                .withQuery(builder)
                .withSort(fieldSortBuilder)
                .withPageable(pageRequest)
                .build();
        //List<UserInfo> userInfos = elasticsearchTemplate.queryForList(build, UserInfo.class);
        Page<UserInfo> search = elasticSearchRepository.search(build);
        // 返回给前端的总页数
        int totalPages = (int) search.getTotalPages();
        // 返回给前端的总数据量
        int totalElements = (int) search.getTotalElements();
        // 这是索引库查询到的数据
        List<UserInfo> content = search.getContent();
        return ResponseEntity.success(content);
    }

    /**
     * @return management.elasticsearch.demo.entity.ResponseEntity
     * @Author xiqiuwei
     * @Date 19:23  2019/8/23
     * @Param [userInfo]
     * @Description
     */
    @PostMapping("updata")
    public ResponseEntity updataUserInfoByWord(@RequestBody UserInfo userInfo) {
        return null;
    }
}
