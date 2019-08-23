package management.elasticsearch.demo.entity;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("all")
/**
 * @Author xiqiuwei
 * @Date Created in 8:56 2019/5/17
 * @Description 自定义结果集，如果底层抛异常会去匹配异常类型返回前端自定义信息
 * @Modified By:
 */
@Slf4j
@Getter
public class ResponseEntity<T> {
    /*状态码*/
    private int code;
    /*返回的说明信息*/
    private String msg;
    /*返回的结果集*/
    private T data;


    // 成功返回值
    public static <T> ResponseEntity<T> success(T result) {
        return new ResponseEntity<T>()
                .setCode(0)
                .setMessage("SUCCESS")
                .setResult(result);
    }


    public static <T> ResponseEntity<T> fail (String msg) {
        return new ResponseEntity<T>()
                .setCode(1)
                .setMessage(msg);
    }

    public  static <T> ResponseEntity<T> fail (String msg,int code) {
        return new ResponseEntity<T>()
                .setCode(code)
                .setMessage(msg);
    }

    // 每次返回都是一个ResponseEntity对象所以可以链式编程
    public ResponseEntity<T> setCode(int code) {
        this.code = code;
        return this;
    }

    private ResponseEntity<T> setMessage(String message) {
        this.msg = message;
        return this;
    }

    private ResponseEntity<T> setResult(T result) {
        this.data = result;
        return this;
    }

}
