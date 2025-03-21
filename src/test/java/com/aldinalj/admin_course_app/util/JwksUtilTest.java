package com.aldinalj.admin_course_app.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

public class JwksUtilTest {

    @Test
    public void generateRSAKeyTest() {

        KeyPair rsaKey = JwksUtil.generateRsaKey();
        Assertions.assertNotNull(rsaKey);
        Assertions.assertNotNull(rsaKey.getPublic());
        Assertions.assertNotNull(rsaKey.getPrivate());


    }
}
