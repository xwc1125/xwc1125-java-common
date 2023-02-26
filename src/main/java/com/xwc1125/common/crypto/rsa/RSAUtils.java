package com.xwc1125.common.crypto.rsa;

import com.xwc1125.common.util.base64.Base64Utils;
import com.xwc1125.common.util.string.StringUtils;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 *
 */
public class RSAUtils {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * @param @param  byte_en_data 已加密数据
     * @param @param  privateKey 私钥(BASE64编码)
     * @param @return
     * @param @throws Exception
     * @return String
     * @Title sign
     * @Description 用私钥对信息生成数字签名
     * @author xwc1125
     * @date 2016年1月11日 下午5:46:07
     */
    public static String sign(byte[] byte_en_data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(byte_en_data);
        return Base64Utils.encode(signature.sign());
    }

    /**
     * @param @param  byte_en_data 已加密数据
     * @param @param  publicKey 公钥(BASE64编码)
     * @param @param  sign 数字签名
     * @param @return
     * @param @throws Exception
     * @return boolean
     * @Title verify
     * @Description 校验数字签名
     * @author xwc1125
     * @date 2016年1月11日 下午5:46:48
     */
    public static boolean verify(byte[] byte_en_data, String publicKey,
                                 String sign) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(byte_en_data);
        return signature.verify(Base64Utils.decode(sign));
    }

    /**
     * @param @param  byte_en_data 已加密数据
     * @param @param  privateKey 私钥(BASE64编码)
     * @param @return
     * @param @throws Exception
     * @return byte[]
     * @Title decryptByPrivateKey
     * @Description 私钥解密
     * @author xwc1125
     * @date 2016年1月11日 下午5:48:01
     */
    public static byte[] decryptByPrivateKey(byte[] byte_en_data,
                                             String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = byte_en_data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(byte_en_data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(byte_en_data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * @param @param  byte_en_data 已加密数据
     * @param @param  publicKey 公钥(BASE64编码)
     * @param @return
     * @param @throws Exception
     * @return byte[]
     * @Title decryptByPublicKey
     * @Description 公钥解密
     * @author xwc1125
     * @date 2016年1月11日 下午5:49:12
     */
    public static byte[] decryptByPublicKey(byte[] byte_en_data,
                                            String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = byte_en_data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(byte_en_data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(byte_en_data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * @param @param  byte_de_data 原始数据
     * @param @param  publicKey 公钥(BASE64编码)
     * @param @return
     * @param @throws Exception
     * @return byte[]
     * @Title encryptByPublicKey
     * @Description 公钥加密
     * @author xwc1125
     * @date 2016年1月11日 下午5:49:56
     */
    public static byte[] encryptByPublicKey(byte[] byte_de_data,
                                            String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = byte_de_data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(byte_de_data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(byte_de_data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * @param @param  byte_de_data 原始数据
     * @param @param  privateKey 私钥(BASE64编码)
     * @param @return
     * @param @throws Exception
     * @return byte[]
     * @Title encryptByPrivateKey
     * @Description 私钥加密
     * @author xwc1125
     * @date 2016年1月11日 下午5:50:43
     */
    public static byte[] encryptByPrivateKey(byte[] byte_de_data,
                                             String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = byte_de_data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(byte_de_data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(byte_de_data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static String encryptByPrivateKeyToBase64(String byte_de_data,
                                                     String privateKey, String charset) throws Exception {
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        byte[] dataByte = byte_de_data.getBytes(charset);
        byte[] encodedData = encryptByPrivateKey(dataByte, privateKey);
        return Base64Utils.encode(encodedData);
    }

    public static String encryptByPublicKeyToBase64(String de_data,
                                                    String publicKey, String charset) throws Exception {
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        byte[] data = de_data.getBytes(charset);
        byte[] encodedData = encryptByPublicKey(data, publicKey);
        return Base64Utils.encode(encodedData);
    }

    public static String decryptByPrivateKeyFromBase64(String base64_en_data,
                                                       String privateKey, String charset) throws Exception {
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        byte[] bcd = Base64Utils.decode(base64_en_data);
        byte[] decodedData2 = decryptByPrivateKey(bcd, privateKey);
        String target2 = new String(decodedData2, charset);
        return target2;
    }

    public static String decryptByPublicKeyFromBase64(String base64_en_data,
                                                      String publicKey, String charset) throws Exception {
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        byte[] bcd = Base64Utils.decode(base64_en_data);
        byte[] de_data = decryptByPublicKey(bcd, publicKey);
        String target = new String(de_data, charset);
        return target;
    }

    /**
     * @param @param  keyMap 密钥对
     * @param @return
     * @param @throws Exception
     * @return String
     * @Title getPrivateKey
     * @Description 获取私钥
     * @author xwc1125
     * @date 2016年1月11日 下午5:51:44
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * @param @param  keyMap 密钥对
     * @param @return
     * @param @throws Exception
     * @return String
     * @Title getPublicKey
     * @Description 获取公钥
     * @author xwc1125
     * @date 2016年1月11日 下午5:52:01
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * @param @param  ascii ascii数据
     * @param @param  asc_len ascii数据长度
     * @param @return
     * @return byte[]
     * @Title ASCII_To_BCD
     * @Description ASCII码转BCD码
     * @author xwc1125
     * @date 2016年1月11日 下午1:49:07
     */
    private static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    /**
     * @param @param  asc 单个ascii
     * @param @return
     * @return byte
     * @Title asc_to_bcd
     * @Description asc转为bcd
     * @author xwc1125
     * @date 2016年1月11日 下午1:49:18
     */
    private static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9')) {
            bcd = (byte) (asc - '0');
        } else if ((asc >= 'A') && (asc <= 'F')) {
            bcd = (byte) (asc - 'A' + 10);
        } else if ((asc >= 'a') && (asc <= 'f')) {
            bcd = (byte) (asc - 'a' + 10);
        } else {
            bcd = (byte) (asc - 48);
        }
        return bcd;
    }

    /**
     * @param @param  bytes
     * @param @return
     * @return String
     * @Title bcd2Str
     * @Description BCD转字符串
     * @author xwc1125
     * @date 2016年1月11日 下午1:49:35
     */
    private static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * @param @param  byte_de_data 原始数据
     * @param @param  privateKey 私钥(BASE64编码)
     * @param @return
     * @param @throws Exception
     * @return String
     * @Title encryptByPrivateKey
     * @Description 加密
     * @author xwc1125
     * @date 2016年1月11日 下午5:43:41
     */
    public static String encryptByPrivateKeyToBCD(String byte_de_data,
                                                  String privateKey, String charset) throws Exception {
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        byte[] dataByte = byte_de_data.getBytes(charset);
        byte[] encodedData = encryptByPrivateKey(dataByte, privateKey);
        return bcd2Str(encodedData);
    }

    /**
     * @param @param  de_data 原始数据
     * @param @param  publicKey 公钥(BASE64编码)
     * @param @return
     * @return String 返回bcd的字符串
     * @throws Exception
     * @Title encryptByPublicKey
     * @Description 通过公钥进行加密（返回bcd的字符串）
     * @author xwc1125
     * @date 2016年1月11日 下午5:09:04
     */
    public static String encryptByPublicKeyToBCD(String de_data,
                                                 String publicKey, String charset) throws Exception {
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        byte[] data = de_data.getBytes(charset);
        byte[] encodedData = encryptByPublicKey(data, publicKey);
        return bcd2Str(encodedData);
    }

    /**
     * @param @param  bcd_en_data 加密后的数据（bcd格式）
     * @param @param  privateKey 私钥(BASE64编码)
     * @param @return
     * @return String 返回原始数据
     * @throws Exception
     * @Title decryptByPrivateKey
     * @Description 通过私钥进行解密
     * @author xwc1125
     * @date 2016年1月11日 下午5:13:23
     */
    public static String decryptByPrivateKeyFromBCD(String bcd_en_data,
                                                    String privateKey, String charset) throws Exception {
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        byte[] bytes = bcd_en_data.getBytes(charset);
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        byte[] decodedData2 = decryptByPrivateKey(bcd, privateKey);
        String target2 = new String(decodedData2);
        return target2;
    }

    /**
     * @param @param  bcd_en_data 加密后的数据（bcd格式）
     * @param @param  publicKey 公钥(BASE64编码)
     * @param @return
     * @return String
     * @throws Exception
     * @Title decryptByPublicKey
     * @Description 通过公钥进行解密
     * @author xwc1125
     * @date 2016年1月11日 下午5:17:05
     */
    public static String decryptByPublicKeyFromBCD(String bcd_en_data,
                                                   String publicKey, String charset) throws Exception {
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        byte[] bytes = bcd_en_data.getBytes(charset);
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        byte[] de_data = decryptByPublicKey(bcd, publicKey);
        String target = new String(de_data);
        return target;
    }

    /**
     * @param @param  bdc_en_data 加密后的数据（bcd格式）
     * @param @param  privateKey 私钥(BASE64编码)
     * @param @return
     * @return String
     * @throws Exception
     * @Title sign
     * @Description 用私钥对信息生成数字签名
     * @author xwc1125
     * @date 2016年1月11日 下午5:20:19
     */
    public static String sign(String bdc_en_data, String privateKey,
                              String charset) throws Exception {
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        byte[] en_data_byte = bdc_en_data.getBytes(charset);
        byte[] bcd = ASCII_To_BCD(en_data_byte, en_data_byte.length);
        String sign = sign(bcd, privateKey);
        return sign;
    }

    /**
     * @param @param  bcd_en_data 加密后的数据（bcd格式）
     * @param @param  publicKey 公钥(BASE64编码)
     * @param @param  sign 签名
     * @param @return
     * @return boolean
     * @throws Exception
     * @Title verify
     * @Description 校验数字签名
     * @author xwc1125
     * @date 2016年1月11日 下午5:21:35
     */
    public static boolean verify(String bcd_en_data, String publicKey,
                                 String sign, String charset) throws Exception {
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        byte[] en_data_byte = bcd_en_data.getBytes(charset);
        byte[] bcd = ASCII_To_BCD(en_data_byte, en_data_byte.length);
        return verify(bcd, publicKey, sign);
    }

    // ==============生成后存入文件==================

    /**
     * 获取公钥
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String filename) throws Exception {
        InputStream resourceAsStream = RSAUtils.class.getClassLoader().getResourceAsStream(filename);
        DataInputStream dis = new DataInputStream(resourceAsStream);
        byte[] keyBytes = new byte[resourceAsStream.available()];
        dis.readFully(keyBytes);
        dis.close();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    /**
     * 获取密钥
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String filename) throws Exception {
        InputStream resourceAsStream = RSAUtils.class.getClassLoader().getResourceAsStream(filename);
        DataInputStream dis = new DataInputStream(resourceAsStream);
        byte[] keyBytes = new byte[resourceAsStream.available()];
        dis.readFully(keyBytes);
        dis.close();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /**
     * Description: 获取公钥
     * </p>
     *
     * @param publicKeyBase64 base64
     * @return java.security.PublicKey
     * @Author: xwc1125
     * @Date: 2019-04-09 18:13:37
     */
    public static PublicKey getPublicKeyFromBase64(String publicKeyBase64) throws Exception {
        byte[] publicKey = Base64Utils.decode(publicKeyBase64);
        return getPublicKey(publicKey);
    }

    /**
     * 获取公钥
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(byte[] publicKey) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    /**
     * 获取密钥
     *
     * @param privateKeyBase64 base64
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyFromBase64(String privateKeyBase64) throws Exception {
        byte[] privateKey = Base64Utils.decode(privateKeyBase64);
        return getPrivateKey(privateKey);
    }

    /**
     * 获取密钥
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(byte[] privateKey) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /**
     * 生存rsa公钥和密钥
     *
     * @param publicKeyFilename
     * @param privateKeyFilename
     * @param password
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String password) throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        FileOutputStream fos = new FileOutputStream(publicKeyFilename);
        fos.write(publicKeyBytes);
        fos.close();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        fos = new FileOutputStream(privateKeyFilename);
        fos.write(privateKeyBytes);
        fos.close();
    }

    /**
     * 生存rsa公钥
     *
     * @param password
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] generatePublicKey(String password) throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        return keyPair.getPublic().getEncoded();
    }

    /**
     * 生存rsa公钥
     *
     * @param password
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] generatePrivateKey(String password) throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        return keyPair.getPrivate().getEncoded();
    }

    /**
     * Description: 生成公私钥
     * </p>
     *
     * @param password
     * @Author: xwc1125
     * @Date: 2019-04-09 18:15:12
     */
    public static Map<String, byte[]> generateKey(String password) throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        Map<String, byte[]> map = new HashMap<String, byte[]>();
        map.put("pub", publicKeyBytes);
        map.put("pri", privateKeyBytes);
        return map;
    }

    /***
     * Description: 生成公私钥（base64）
     * </p>
     * @param password
     *
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @Author: xwc1125
     * @Date: 2019-04-09 18:15:01
     */
    public static Map<String, String> generateKeyToBase64(String password) throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        Map<String, String> map = new HashMap<>();
        map.put("pub", Base64Utils.encode(publicKeyBytes));
        map.put("pri", Base64Utils.encode(privateKeyBytes));
        return map;
    }

    public static String toBase64(byte[] b) {
        return Base64Utils.encode(b);
    }

    public static final byte[] toBytes(String base64Str) {
        return Base64Utils.decode(base64Str);
    }
}
