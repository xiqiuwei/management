package management.zuul.demo.filter;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author xiqiuwei
 * @Date Created in 16:32 2019/8/14
 * @Description 读取配置文件里面的白名单url
 * @Modified By:
 */

@ConfigurationProperties(prefix = "whitelist")
public class FilterProperties {

    private List<String> allowPaths;

    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }
}
