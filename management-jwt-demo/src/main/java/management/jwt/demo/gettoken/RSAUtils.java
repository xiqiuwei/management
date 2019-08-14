package management.jwt.demo.gettoken;

import management.jwt.demo.exception.CommonException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
/**
 * @Author xiqiuwei
 * @Date Created in 10:00 2019/8/9
 * @Description RSA非对称加密，公钥私钥生成获取等操作
 * @Modified By:
 */

public class RSAUtils {
    /**
     * 密钥长度 于原文长度对应 以及越长速度越慢
     */
    private final static int KEY_SIZE = 1024;

    /**
     * @return void
     * @Author xiqiuwei
     * @Date 10:24  2019/8/9
     * @Param []
     * @Description
     */
    public void genKeyPair() {
        try {
            // KeyPairGenerator生成RSA的公钥和私钥对
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            // 初始化KeyPairGenerator生成器
            keyPairGenerator.initialize(KEY_SIZE);
            // 生成一个公私钥对存放在keyPair中
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 公钥
            RSAPublicKey aPublic = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
            // RSA 加密或签名后的结果是不可读的二进制，使用时经常会转为 BASE64 码再传输
            String publicKey = Base64.getEncoder().encodeToString(aPublic.getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(aPrivate.getEncoded());
            this.writePubRSAIntoResource(publicKey);
            this.writePriRSAIntoResource(privateKey);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            throw new CommonException("生成公钥私钥的时候出现问题",-1);
        }

    }


    /**
     * @return java.lang.String
     * @Author xiqiuwei
     * @Date 10:41  2019/8/9
     * @Param [encrypt, pubKey]
     * @Description 公钥加密明文
     */
    public String publicKeyEncrypt(String string, String pubKey) throws Exception {
        RSAPublicKey rsaPublicKey = this.getPublicKey(pubKey);
        // 进行RSA加密
        Cipher rsa = Cipher.getInstance("RSA");
        rsa.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        return Base64.getEncoder().encodeToString(rsa.doFinal(string.getBytes(StandardCharsets.UTF_8)));

    }

    /**
     * @return java.lang.String
     * @Author xiqiuwei
     * @Date 11:04  2019/8/9
     * @Param [encrypt, priKey]
     * @Description 私钥解密密文
     */
    public String privateKeyDecrypt(String encrypt, String priKey) throws Exception {
        byte[] decode = Base64.getDecoder().decode(encrypt);
        RSAPrivateKey rsaPrivateKey = this.getPrivateKey(priKey);
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        return new String(cipher.doFinal(decode));
    }

    /**
     * @return void
     * @Author xiqiuwei
     * @Date 14:16  2019/8/12
     * @Param [RSAKey]
     * @Description 将公钥写入到文件当中
     */
    public void writePubRSAIntoResource(String RSAKey) throws IOException {
        String s = new ClassPathResource("/static/rsa.pub").getURL().toString();
        String substring = s.substring(6, s.length());
        String replace = substring.replace("/target/classes", "/src/main/resources");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(replace)));
        bw.write(RSAKey);
        bw.flush();
        bw.close();
    }

    /**
     * @return void
     * @Author xiqiuwei
     * @Date 14:17  2019/8/12
     * @Param [RSAKey]
     * @Description 将私钥写到文件当中
     */
    public void writePriRSAIntoResource(String RSAKey) throws IOException {
        String s = new ClassPathResource("/static/rsa.pri").getURL().toString();
        String substring = s.substring(6, s.length());
        String replace = substring.replace("/target/classes", "/src/main/resources");
        System.out.println(replace);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(replace)));
        bw.write(RSAKey);
        bw.flush();
        bw.close();
    }

    /**
     * @return java.security.interfaces.RSAPublicKey
     * @Author xiqiuwei
     * @Date 14:01  2019/8/12
     * @Param []
     * @Description 获取公钥的方法
     */
    public RSAPublicKey getPublicKey(String pubKey) throws Exception {
        byte[] decode = Base64.getDecoder().decode(pubKey);
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decode));
    }

    /**
     * @return java.security.interfaces.RSAPrivateKey
     * @Author xiqiuwei
     * @Date 14:01  2019/8/12
     * @Param []
     * @Description 获取私钥的方法
     */
    public RSAPrivateKey getPrivateKey(String priKey) throws Exception {
        byte[] decode = Base64.getDecoder().decode(priKey);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decode));
    }

    /**
     * @return java.lang.String
     * @Author xiqiuwei
     * @Date 16:23  2019/8/9
     * @Param [inputStream]
     * @Description 读取流内容，这里就固定去读私钥
     */
    public String readFile(InputStream inputStream) throws IOException {
        // InputStream inputStream = new ClassPathResource("/static/rsa.pri").getInputStream();
        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bfReader = new BufferedReader(reader);
            String tmpContent = null;
            while ((tmpContent = bfReader.readLine()) != null) {
                builder.append(tmpContent);
            }
            bfReader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this.filter(builder.toString());
    }

    /**
     *@Author xiqiuwei
     *@Date 14:21  2019/8/12
     *@Param [rsaKey]
     *@return java.lang.String
     *@Description 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
     */
    private String filter(String rsaKey) {
        return rsaKey.replaceAll("/\\*[\\s\\S]*?\\*/", "");
    }


    public static void main(String[] args) throws Exception {
        RSAUtils rsaUtils = new RSAUtils();
        InputStream inputStream = new ClassPathResource("/static/rsa.pub").getInputStream();
        String s = rsaUtils.readFile(inputStream);
        System.out.println(s);
        RSAPublicKey publicKey = rsaUtils.getPublicKey(s);
        System.out.println(publicKey);
       /* InputStream inputStream = path.getInputStream();
        String s = new RSAUtils().readFile(inputStream);
        System.out.println("这是流读出来得数据" + s);*/
      /*  long temp = System.currentTimeMillis();
        Map<Integer, Object> integerObjectMap1 = genKeyPair();
        System.out.println("公钥:" + integerObjectMap1.get(0));
        System.out.println("私钥:" + integerObjectMap1.get(1));
        System.out.println((System.currentTimeMillis() - temp) / 1000.0);
        String str = "我:123456上山打老虎";
        String encode = URLEncoder.encode(str, "utf-8");
        Map<Integer, Object> integerObjectMap = genKeyPair();
        String xiqiuwei = publicKeyEncrypt(encode, (String) integerObjectMap.get(0));
        System.out.println("这是密文:" + xiqiuwei);

        String decode = privateKeyDecrypt(xiqiuwei, (String) integerObjectMap.get(1));
        String string = URLDecoder.decode(decode, "utf-8");
        System.out.println("这是明文:" + string);*/
    }

}
