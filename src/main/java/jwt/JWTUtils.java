package jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * JWT工具类
 */
public class JWTUtils {
    //密钥
    private static final String SING = "！Q23dgdfsdf";

    /**
     * 生成token  header.payload.signature
     */
    public static String getToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 1);//默认1天过期
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        String token = builder.withExpiresAt(instance.getTime())//指定令牌过期时间
                .sign(Algorithm.HMAC256(SING));//签名
        return token;
    }

    /**
     * 验证token合法性， 获取token信息方法
     */
    public static DecodedJWT verify(String token) {
        DecodedJWT verify=JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return verify;
    }

}
