package management.elasticsearch.demo.controller;

import management.elasticsearch.demo.entity.Book;
import management.elasticsearch.demo.entity.ResponseEntity;
import management.elasticsearch.demo.entity.Student;
import management.elasticsearch.demo.entity.UserInfo;
import management.elasticsearch.demo.repository.ElasticSearchRepository;
import management.elasticsearch.demo.repository.StudentElasticSearchRepository;
import management.elasticsearch.demo.service.IElasticSearchService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private StudentElasticSearchRepository studentElasticSearchRepository;

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
     * @Param student
     * @Description
     */
    @PostMapping("saveStudent")
    public ResponseEntity<Student> saveBookInfoIntoRepository(@RequestBody Student student) {
        try {
            Student save = studentElasticSearchRepository.save(student);
            return ResponseEntity.success(save);
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
    public ResponseEntity<List<Book>> queryUserInfoByBimInfoId(@RequestParam("id") String id,
                                                   @RequestParam("bimInfoId") String bimInfoId,
                                                   @PageableDefault Pageable pageable) {
        try {
            SearchQuery query = new NativeSearchQueryBuilder()
                    .withQuery(queryStringQuery(id))
                    .withQuery(queryStringQuery(bimInfoId))
                    .withPageable(pageable)
                    .withSort(SortBuilders.fieldSort("age").order(SortOrder.DESC))
                    .build();
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
    @GetMapping("queryUserName")
    public ResponseEntity<List<UserInfo>> queryUserInfoByFuzzyQuery(@RequestParam("name") String name) {
        // 构建查询条件
        // BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // 左右模糊查询
        QueryBuilder builder = QueryBuilders.queryStringQuery("userName").field(name);
        // 精确的匹配，采用的是默认的分词器
        // builder.must(QueryBuilders.matchQuery("userName", name));
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
     *@Author xiqiuwei
     *@Date 8:44  2019/8/27
     *@Param [subject]
     *@return management.elasticsearch.demo.entity.ResponseEntity
     *@Description 简单的聚合函数，根据学课来归类一类学生
     */
    @GetMapping("aggeregation")
    public ResponseEntity aggregationBySubject () {
        // 查询当前索引库的所有数据
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder
                .withQuery(matchAllQueryBuilder)
                // 搜索类型
                .withSearchType(SearchType.DEFAULT)
                // 索引库名,就是javaBean的indexName
                .withIndices("student_info")
                // 表名，javaBean的type
                .withTypes("student");
        // 给聚合函数取名
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("school_subject").field("name");
        nativeSearchQueryBuilder.addAggregation(aggregation);
        NativeSearchQuery build = nativeSearchQueryBuilder.build();
        // 通过elastic模板的query方法获得聚合
        Aggregations aggregations = elasticsearchTemplate.query(build, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse searchResponse) {
                return searchResponse.getAggregations();
            }
        });
   /*     List<Aggregation> aggregationsList = aggregations.asList();
        StringTerms stringTerms = (StringTerms) aggregationsList;
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        Iterator<StringTerms.Bucket> iterator = buckets.iterator();
        List<String> subjectList = new ArrayList<>();
        while (iterator.hasNext()) {
            String subject = iterator.next().getKey().toString();
            subjectList.add(subject);
        }*/
        Map<String, Aggregation> stringAggregationMap = aggregations.asMap();
        //获得对应的聚合函数的聚合子类，该聚合子类也是个map集合,里面的value就是桶Bucket，我们要获得Bucket
        StringTerms stringTerms = (StringTerms) stringAggregationMap.get("name");
        //获得所有的桶
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        //将集合转换成迭代器遍历桶,当然如果你不删除buckets中的元素，直接foreach遍历就可以了
        Iterator<StringTerms.Bucket> iterator = buckets.iterator();
        List<String> subjectList = new ArrayList<>();
        while(iterator.hasNext()) {
            //bucket桶也是一个map对象，我们取它的key值就可以了
            String username = iterator.next().getKeyAsString();//或者bucket.getKey().toString();
            //根据username去结果中查询即可对应的文档，添加存储数据的集合
            subjectList.add(username);
        }

        return ResponseEntity.success(subjectList);
    }
}
