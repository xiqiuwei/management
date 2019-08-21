package management.zuul.demo.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import management.zuul.demo.entity.FallBackMessage;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author xiqiuwei
 * @Date Created in 8:58 2019/8/20
 * @Description
 * @Modified By:
 */
public class MyZuulProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                // 返回的内容
                ObjectMapper objectMapper = new ObjectMapper();
                FallBackMessage fallBackMessage = new FallBackMessage();
                fallBackMessage.setCode(-1);
                fallBackMessage.setMsg("ZUUL实现了对所有服务的熔断机制！");
                return objectMapper.writeValueAsString(fallBackMessage);
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                // 将信息返回前端
                return new ByteArrayInputStream(getStatusText().getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                // 设置头部消息声明json格式
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return httpHeaders;
            }
        };
    }
}
