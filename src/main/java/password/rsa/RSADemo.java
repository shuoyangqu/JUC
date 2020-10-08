package password.rsa;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSADemo {
    public static void main(String[] args) throws Exception {
        /**非对称加密--生成公钥和私钥
         * 步骤：
         * 1、创建密钥对
         * 2、生成密钥对
         * 3、生成私钥
         * 4、生成公钥
         * 5、获取私钥的字节数组
         * 6、获取公钥字节数组
         * 7、使用base64进行编码
         *
         * 8、创建加密对象
         * 9、对加密进行初始化
         *      第一个参数：加密的模式
         *      第二个参数：你想使用公钥还是私钥进行加密
         *
         *      私钥进行加密  公钥解密
         *      
         *      读取私钥
         */
        String input = "硅谷";
        String algorithm = "RSA";
        Key privateKey=getPrivateKey("a.pri",algorithm);
        Key publicKey=getPublicKey("a.pub",algorithm);
        //把公钥、私钥保存到根目录
//        generateKeyToFile(algorithm, "a.pub", "a.pri");

//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
//        KeyPair keyPair = keyPairGenerator.generateKeyPair();
//        PrivateKey privateKey = keyPair.getPrivate();
//        PublicKey publicKey = keyPair.getPublic();
//        byte[] privateKeyEncoded = privateKey.getEncoded();
//        byte[] publicKeyEncoded = publicKey.getEncoded();
//        String privateKeyEncodedString = Base64.encode(privateKeyEncoded);
//        String publicKeyEncodedString = Base64.encode(publicKeyEncoded);
//        System.out.println(privateKeyEncodedString);
//        System.out.println(publicKeyEncodedString);

        String s = encryptRSA(algorithm, privateKey, input);
        System.out.println(s);
        String s1 = decryptRSA(algorithm, publicKey, s);
        System.out.println(s1);
//        Cipher cipher = Cipher.getInstance(algorithm);
//        cipher.init(Cipher.ENCRYPT_MODE,privateKey);
//        byte[] bytes = cipher.doFinal(input.getBytes());
//        System.out.println(Base64.encode(bytes));

//        cipher.init(Cipher.DECRYPT_MODE, publicKey);
//        byte[] bytes1 = cipher.doFinal(bytes);
//        System.out.println(new String(bytes1));


    }

    /**
     * 读取公钥
     * @param pubPaths  公钥路径
     * @param algorithm 算法
     * @return 公钥key对象
     */
    private static PublicKey getPublicKey(String pubPaths, String algorithm) throws Exception {
        String publicKeyString = FileUtils.readFileToString(new File(pubPaths), Charset.forName("UTF-8"));
        KeyFactory keyFactory =  KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    /**
     * 读取私钥
     * @param priPath  私钥路径
     * @param algorithm 算法
     * @return  私钥key对象
     */
    private static PrivateKey getPrivateKey(String priPath, String algorithm) throws Exception {
        String privateKeyString = FileUtils.readFileToString(new File(priPath), Charset.forName("UTF-8"));
        //创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        //key规则
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    /**
     * 保存公钥和私钥，把公钥和私钥保存到根目录
     *
     * @param algorithm
     * @param priPath
     * @param pubPath
     */
    private static void generateKeyToFile(String algorithm, String pubPath, String priPath) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        byte[] privateKeyEncoded = privateKey.getEncoded();
        byte[] publicKeyEncoded = publicKey.getEncoded();
        String privateKeyEncodedString = Base64.encode(privateKeyEncoded);
        String publicKeyEncodedString = Base64.encode(publicKeyEncoded);
        //把公钥和私钥保存到根目录
        //把公钥和私钥保存到根目录
        FileUtils.writeStringToFile(new File(pubPath), publicKeyEncodedString, Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File(priPath), privateKeyEncodedString, Charset.forName("UTF-8"));
    }

    /**
     * 使用密钥加密数据
     *
     * @param algorithm  算法
     * @param privateKey 密钥
     * @param input      原文
     * @return 密文
     */
    public static String encryptRSA(String algorithm, Key privateKey, String input) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(input.getBytes());
        return Base64.encode(bytes);
    }

    /**
     * 解密方法
     */
    public static String decryptRSA(String algorithm, Key publicKey,String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decode = Base64.decode(encrypted);
        byte[] bytes1 = cipher.doFinal(decode);
        return new String(bytes1);
    }
}
