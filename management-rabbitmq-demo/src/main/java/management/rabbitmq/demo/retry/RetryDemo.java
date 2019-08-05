package management.rabbitmq.demo.retry;

import lombok.extern.slf4j.Slf4j;
import management.rabbitmq.demo.entity.User;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author xiqiuwei
 * @Date Created in 14:17 2019/8/5
 * @Description
 * @Modified By:
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/retry")
@Slf4j
public class RetryDemo {

    @Resource
    private RetryTemplate retryTemplate;

    @PostMapping("/demo")
    public String retryDemo(@RequestBody User user) {
        // 重试次数内会走这个方法
        RetryCallback<Object, Exception> retryCallback = new RetryCallback<Object, Exception>() {
            @Override
            public Object doWithRetry(RetryContext retryContext) throws Exception {
                // TODO 这里可以写自己的逻辑业务
                if ("三土".equals(user.getName())) {
                    return "success";
                }
                log.info("这是重试的次数:{}", retryContext);
                // 可以给当前的context传递一些键值对信息
                retryContext.setAttribute("error", "看三土打篮球像菜虚困");
                throw new Exception("重试次数达到上限还是报错");
            }
        };
        // 这个方法是重试次数达到了上限并且还抛异常的情况下才会走
        RecoveryCallback<Object> recoveryCallback = new RecoveryCallback<Object>() {
            @Override
            public Object recover(RetryContext retryContext) throws Exception {
                // TODO 这里可以写自己的逻辑业务
                log.info("这是报错信息:{}", retryContext.getLastThrowable().getMessage());
                return null;
            }
        };
        try {
            retryTemplate.execute(retryCallback, recoveryCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
}
