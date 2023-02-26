package com.xwc1125.common.util.pem;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.xwc1125.common.util.string.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * @Description:
 * @Author: xwc1125
 * @Date: 2021/1/7 14:22
 * @Copyright Copyright@2021
 */
public class PemUtil {

    private static final CertificateFactory CERTIFICATE_FACTORY;
    static String X509 = "X.509";

    static {
        try {
            CERTIFICATE_FACTORY = CertificateFactory.getInstance(X509);
        } catch (CertificateException e) {
            throw Throwables.propagate(e);
        }
    }

    public static PublicKey getVerifyPublicKey(String signPubKeyCert) throws Exception {
        Preconditions.checkArgument(!StringUtils.isEmpty(signPubKeyCert), "The response message doesn't contains the [signPubKeyCert]");
        InputStream stream = new ByteArrayInputStream(signPubKeyCert.getBytes(StandardCharsets.UTF_8));
        X509Certificate cert = (X509Certificate) CERTIFICATE_FACTORY.generateCertificate(stream);
        return cert.getPublicKey();
    }

    public static boolean verifyPublicKey(String signPubKeyCert) {
        if (StringUtils.isEmpty(signPubKeyCert)) {
            return false;
        }
        try {
            PublicKey publicKey = getVerifyPublicKey(signPubKeyCert);
            return publicKey != null;
        } catch (Exception e) {
            return false;
        }
    }
}
