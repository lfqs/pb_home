package com.lfq.common.security;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * @作者 lfq
 * @DATE 2023-11-21
 * current year
 **/
public class RsaKeyPairGenerator {

    public static void main(String[] args) {
        RSA rsa = SecureUtil.rsa();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        String publicKeyBase64 = rsa.getPublicKeyBase64();
        System.out.println(privateKeyBase64);
        System.out.println(publicKeyBase64);
    }

}
