package com.management.elasticsearch.demo;

import management.elasticsearch.demo.ManagementElasticSearchDemoApplication;
import management.elasticsearch.demo.entity.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author xiqiuwei
 * @Date Created in 10:00 2019/8/23
 * @Description
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagementElasticSearchDemoApplication.class)
public class ManagementElasticsearchApplicationTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Test
    public void test1 () {
        elasticsearchTemplate.createIndex(UserInfo.class);
        elasticsearchTemplate.putMapping(UserInfo.class);
    }
}
