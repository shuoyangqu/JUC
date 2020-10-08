package password.digest;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要算法  MD5  sha1  sha256  sha512
 */
public class DigestDemo1 {
    public static void main(String[] args) throws Exception {
        /**
         * 提问1：怎么得到消息摘要算法
         * 答：使用MessageDigest
         * 步骤：
         * 1、原文
         * 2、算法
         * 3、创建消息摘要对象
         * 4、执行消息摘要算法
         * 5、对密文进行迭代
         *      把密文转换成16进制
         *      判断如果密文的长度是1，需要在高位进行补0
         */
        //http://encode.chahuo.com/ : 4124bc0a9335c27f086f24ba207a4912
//                                    4124bc0a9335c27f086f24ba207a4912
        //原文
        String input = "aa";
        //算法
        String algorithm = "MD5";
        System.out.println(getDigest(input, algorithm));

    }

    /**
     * 获取数字摘要
     * @param input  原文
     * @param algorithm  算法
     * @return
     * @throws Exception
     */
    private static String getDigest(String input, String algorithm) throws Exception {
        //创建消息摘要对象
        MessageDigest md = MessageDigest.getInstance(algorithm);
        //执行消息摘要算法
        byte[] digest = md.digest(input.getBytes());
        return toHex(digest);
    }

    private static String toHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            String s = Integer.toHexString(b & 0xff);
            if (s.length() == 1) {
                s = "0" + s;
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
