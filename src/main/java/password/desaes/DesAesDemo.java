package password.desaes;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DesAesDemo {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, Base64DecodingException {
        /**
         * DES对称加密操作步骤：
         * 1、原文
         * 2、定义key
         * 3、算法
         * 4、加密类型
         * 5、创建加密对象
         * 6、进行加密初始化
         *      第一个参数表示：模式，加密模式、解密模式
         *      第二个参数表示：加密的规则
         * 7、调用加密方法
         * 8、创建Base64对象  Apache包
         * 9、打印密文
         *
         */
        String input="硅谷";
        String key="12345678";
//        String transformation="DES";
        //ECB:表示加密模式
        //PKCS5Padding:表示填充模式qANksk5lvqM=
        String transformation="DES/ECB/PKCS5Padding";
        String algorithm="DES";
        String encode = encrypt(input, key, transformation, algorithm);
        String deEncode = deEncode(encode, key, transformation, algorithm);
        System.out.println("加密："+encode);
        System.out.println("解密："+deEncode);
    }

    private static String deEncode(String encode, String key, String transformation, String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, Base64DecodingException {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        byte[] bytes = cipher.doFinal( Base64.decode(encode));
        return new String(bytes);
    }

    private static String encrypt(String input, String key, String transformation, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        byte[] bytes = cipher.doFinal(input.getBytes());
        return Base64.encode(bytes);
    }

}
