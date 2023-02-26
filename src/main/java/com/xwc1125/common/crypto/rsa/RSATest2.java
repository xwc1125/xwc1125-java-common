package com.xwc1125.common.crypto.rsa;

import java.net.URLDecoder;
import java.util.Map;

/**
 * @ClassName RSATester
 * @Description TODO(describe the types)
 * @author xwc1125
 * @date 2016年1月11日 下午12:47:22
 */
public class RSATest2 {
    static String publicKey;
    static String privateKey;

    static {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("公钥长度: \n\r" + publicKey.length());
            System.err.println("私钥： \n\r" + privateKey);
            System.err.println("私钥长度： \n\r" + privateKey.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        test();
//		testSign();
//		test2();
//		testSign2();
//		test3();
    }

    /**
     * <p>
     * Description: TODO(describe the methods)
     * </p>
     * <p>
     *
     * </p>
     *
     * @author xwc1125
     * @date 2017年9月6日 上午9:37:35
     */
    private static void test3() {
        String en_by_private = "7654DFEB420C3E11E7E42C90506D7076203A790420AC6F5890AD6E887B00C65D9BE496137C9F9736646CB77BEF8F0513D6A98C59B7CBBE9721B79BC38A39F69166EE213F84AAA50448BE7A09A2C6A87E48C80D1D509D94BEFF0435232ACA4ECF18A742DA44375C3173A699A5A44BAF1BFA0A567DFF3648F51B14FB3ACC60F41226D9B4C7F61FB94FDDA2F2A765E4D23EB198DFB93466C9E26897BACBA5C7C85AA3AD690A9D8DD001580A79581E1621355DB823311B0D2C662C13F1224AB1B575184A018A393EA1F5559851EE2D5540565AC17A328628F84D530D609751B96A709B4A6522AE24D50782F56F33011CFC3AB61598406C30959A7D298CB9FEAEF76F5BC82450AF496A9A92B381BD3A85190B3B1621A54AAB0376133798C990D0BBE231D2EE7D9B9ECFD5965D88803F3573C09B27C777B59DBE0CB65E3A6805282395B41E6A31DDF87DD416FE57E7AA256890B66A276E052791546E509BDECB975102ACACFA513155249753A9463C2862D93144F1637961DA6D0C3D6960D63528987B7E59D493EE631402423B3922BA44E895E0BB042E4208200766D187D68F579546DE0E2A1A34754E125756933E3E89B78830260406D3699F632DE06D8ECF05CB9B7F5CD2BC08240A58F4F91D107B00E3F556E277DDEBF9E91114C78FC9CC836335ADE3957EF885A9003BB463C4516113BAF5BE1AC85CF2E1586E9F8A22AED2C6540CF0401CEC76F28766414E1E6D8C1D87B5A7B5BD75DB9DAA42314A6F5158DDBCE07F07466FF5C2CA455D5F37ABDAAA01F07402771C845CD8066CC53D44FC0F71F56335211DD3826CF52B20A111B71365359C472FB8406364A56B44680712C63B953D50546641A65CE919382419E2639E1E61D9C739F7720E06B5DBAB8FF91CCF";
        String publicPrivateKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBYTyIdVpWI7Dh1BBFZq/GQ7lZVpivMLH8LX1aMTUsg4O4o7izs/f1m4C4FnccSiCo0qRC/wEKAO2JB/Rp2vfNAJEI97Srd3v28ldBEacrVEOZpl93nvkRvahdyGN4xLUtckF+8KKXyu4f+0oxRtYo9i5pYDzWYj+5v3hPPCZy4wIDAQAB";
        try {
            String de_biz_content = RSAUtils.decryptByPublicKeyFromBCD(en_by_private, publicPrivateKey, "");
            System.out.println(de_biz_content);
            de_biz_content = URLDecoder.decode(de_biz_content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void test() throws Exception {
        System.err.println("==================test1()=================");
        System.out.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));

        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
        System.err.println("==================test()结束=================");
    }

    static void test2() throws Exception {
        System.err.println("==================test2()=================");
        System.out.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);

        source = "0s0D_l~bEPehHT0T";
        publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDS5suSMO4kJCLX7YojpJYOeZGhCGl09zoxCnLJC3qiex" +
                "Fy7G7TLZLrEOAGIqBmABVi6sVYynWi19vdy18cWZnjLHG8JJSw36V9pmWBWiIoV4Lj1EpEhKazEDz0h6WP" +
                "Wa40gYOE2Pi0GCuaHIiHohFmrrJ2IGVdOfH5YJsduQohUwIDAQAB";
        privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANLmy5"
                + "Iw7iQkItftiiOklg55kaEIaXT3OjEKcskLeqJ7EXLsbtMtkusQ4AYioGYAFWLqxVjKdaL"
                + "X293LXxxZmeMscbwklLDfpX2mZYFaIihXguPUSkSEprMQPPSHpY9ZrjSBg4TY+LQYK5o"
                + "ciIeiEWausnYgZV058flgmx25CiFTAgMBAAECgYEAo64JijE9cYE2JZ0um9ENwLgrBO6"
                + "RYfzKBeuVAGFPUTDwbt/+dxkksBkNtNVE5UTlVDre5LVkrPiYpD9FVqxXmCZQx0xowUX"
                + "vHxt/5CCchC40LDno7YYHJ4hsG7bJgxldBOELiANkhJQU4tsecM9+Fvwn69u+bnWYsiV"
                + "PSnuEk4kCQQDti8F3RT5ASIhKWHgIGK2MXlV4qyp/HVosguYtpW5qtbct+puwmAkRmxu"
                + "xACcSrjBFyikZvh9V6luUOuquqELVAkEA40kk0s6PnWnOzDL3yuY6isHasedUc5E9yxd"
                + "noTVDknTFeIlLAdTMLk29YSH+X8otiP/Ge2pVuPNPHEJngHbXhwJBAMRWdxm1ybYoPT3"
                + "10YAfpZIXhl2uW/mksRuJNecUjSb1+umt8XEMuLIsmb66eZEfGBcW+NGmKxBAHGFwMPk"
                + "baeECP2U2LS+yzEFM3MoR6EZyP5+Ks9iFKxZLlzjZM0yWa4DMfyMuADjQihwUgkC+4of"
                + "FgIGBZOGQeJvjvVbYOz75rQJBAM85HMNpud3zBZXS1XYnOBLei0S48mOsZ5vJ3gr375B"
                + "39TTDb9Lmx/qyHuh58jpBF/8T67f/3DRTJK5Hn1CtETQ=";
        String bcd_encodedsource = RSAUtils.encryptByPublicKeyToBCD(source,
                publicKey, null);
        bcd_encodedsource = "44833A0014CFD39AEDAA9F734507630FA381AA3E3952A05315BFF5277D6CE1D2638C4645C2F4C93FF2DEDD5B95353B55A31759C16988584901F5AC57764C85F521241DC550F9DBB1EE1382061FC011074BDA97FAD67029338FA10A9374B34151B49A2E4E74013C0BA4C48731326FF7399053CFCD079511072E672B2D546600B4";

        System.out.println("加密后文字2：\r\n" + bcd_encodedsource);

        String decodedsource = RSAUtils.decryptByPrivateKeyFromBCD(bcd_encodedsource, privateKey, null);
        System.out.println("解密后文字2: \r\n" + decodedsource);

        String sign = RSAUtils.sign(bcd_encodedsource, privateKey, null);
        System.out.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(bcd_encodedsource, publicKey, sign, null);
        System.out.println("验证结果:\r" + status);
        System.err.println("==================test2()结束=================");

    }

    static void testSign() throws Exception {
        System.err.println("==================testSign()=================");
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
        System.err.println("==================testSign()结束=================");
    }

    static void testSign2() throws Exception {
        System.err.println("==================testSign()=================");
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        String encodedData = RSAUtils.encryptByPrivateKeyToBCD(source, privateKey, null);
        System.out.println("加密后：\r\n" + encodedData);

        String decodedData = RSAUtils.decryptByPublicKeyFromBCD(encodedData, publicKey, null);
        System.out.println("解密后: \r\n" + decodedData);
        System.out.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey, null);
        System.out.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign, null);
        System.out.println("验证结果:\r" + status);
        System.err.println("==================testSign()结束=================");
    }
}
