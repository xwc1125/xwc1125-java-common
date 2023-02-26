package com.xwc1125.common.crypto.aes;

import com.xwc1125.common.util.base64.Base64Utils;
import com.xwc1125.common.util.binary.BinaryUtils;
import com.xwc1125.common.util.string.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>
 * Title: AESUtil
 * </p>
 * <p>
 * Description: AES加密解密方法
 * </p>
 * <p>
 *
 * </p>
 *
 * @author xwc1125
 * @date 2015-7-20下午1:47:56
 */
public class AESUtils {
    private static String charset = "utf-8";

    private static String keySecretKeySpec = "AES";
    private static String keyCipher = "AES";

    public static String getCharset() {
        return charset;
    }

    public static String getKeySecretKeySpec() {
        return keySecretKeySpec;
    }

    public static void setKeySecretKeySpec(String keySecretKeySpec) {
        AESUtils.keySecretKeySpec = keySecretKeySpec;
    }

    public static String getKeyCipher() {
        return keyCipher;
    }

    public static void setKeyCipher(String keyCipher) {
        AESUtils.keyCipher = keyCipher;
    }

    /**
     * to hex
     *
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String Encrypt2Hex(String sSrc, String sKey, String ivParameter) throws Exception {
        byte[] encrypt = Encrypt(sSrc, sKey, ivParameter);
        return BinaryUtils.byte2hex(encrypt).toLowerCase();
    }

    /**
     * to base64
     *
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String Encrypt2Base64(String sSrc, String sKey, String ivParameter) throws Exception {
        byte[] encrypt = Encrypt(sSrc, sKey, ivParameter);
        return Base64Utils.encode(encrypt);
    }

    /**
     * <p>
     * Title: Encrypt
     * </p>
     * <p>
     * Description: AES加密
     * </p>
     * <p>
     *
     * </p>
     *
     * @param sSrc 要加密的字串
     * @param sKey 加密的秘钥
     * @return 返回加密后的结果
     * @throws Exception
     * @author xwc1125
     * @date 2015-7-20下午1:46:32
     */
    public static byte[] Encrypt(String sSrc, String sKey, String ivParameter) throws Exception {
        if (sSrc == null || sSrc.length() == 0 || sSrc.trim().length() == 0) {
            return null;
        }
        if (sKey == null) {
            throw new Exception("encrypt key is null");
        }
        if (sKey.length() != 16) {
            throw new Exception("encrypt key length error");
        }
        byte[] raw = sKey.getBytes(charset);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, keySecretKeySpec);
        Cipher cipher = Cipher.getInstance(keyCipher);
        if (StringUtils.isEmpty(ivParameter)) {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        } else {
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        }
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(charset));
        return encrypted;
    }

    /**
     * from hex
     *
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String DecryptFromHex(String sSrc, String sKey, String ivParameter) throws Exception {
        if (sSrc == null || sSrc.length() == 0 || sSrc.trim().length() == 0) {
            return null;
        }
        byte[] encrypted1 = BinaryUtils.hex2byte(sSrc);
        return Decrypt(encrypted1, sKey, ivParameter);
    }


    /**
     * from base64
     *
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String DecryptFromBase64(String sSrc, String sKey, String ivParameter) throws Exception {
        if (sSrc == null || sSrc.length() == 0 || sSrc.trim().length() == 0) {
            return null;
        }
        byte[] encrypted1 = Base64Utils.decode(sSrc);
        return Decrypt(encrypted1, sKey, ivParameter);
    }

    /**
     * <p>
     * Title: Decrypt
     * </p>
     * <p>
     * Description: AES解密
     * </p>
     * <p>
     *
     * </p>
     *
     * @param encrypted 要解密的加密后字串
     * @param sKey      解密秘钥
     * @return 返回解密后的结果
     * @throws Exception
     * @author xwc1125
     * @date 2015-7-20下午1:47:06
     */
    public static String Decrypt(byte[] encrypted, String sKey, String ivParameter) throws Exception {
        try {
            if (encrypted == null) {
                return null;
            }
            if (sKey == null) {
                throw new Exception("decrypt key is null");
            }
            if (sKey.length() != 16) {
                throw new Exception("decrypt key length error");
            }
            byte[] raw = sKey.getBytes(charset);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, keySecretKeySpec);
            Cipher cipher = Cipher.getInstance(keyCipher);
            if (StringUtils.isEmpty(ivParameter)) {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            } else {
                IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            }
            try {
                byte[] original = cipher.doFinal(encrypted);
                String originalString = new String(original, charset);
                return originalString;
            } catch (Exception e) {
                throw new Exception("decrypt errot", e);
            }
        } catch (Exception ex) {
            throw new Exception("decrypt errot", ex);
        }
    }

    public static void main(String[] args) throws Exception {
        String sKey = "1234567890123456";
        String ivParameter = "UGWalletAESIVStr";
        // 需要加密的字串
        String cSrc = "您好";
        System.out.println("加密前的字串是：" + cSrc);
        // 加密
        String enString = AESUtils.Encrypt2Hex(cSrc, sKey, ivParameter);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = AESUtils.DecryptFromHex(enString, sKey, ivParameter);
        System.out.println("解密后的字串是：" + DeString);
    }
}
