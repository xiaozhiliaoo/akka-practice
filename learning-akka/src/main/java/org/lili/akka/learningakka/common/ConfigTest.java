package org.lili.akka.learningakka.common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author lili
 * @date 2022/3/27 14:22
 */
public class ConfigTest {
    public static void main(String[] args) {
        Config config1 = ConfigFactory.load("config-test.conf");
        System.out.println("config1, complex-app.something="
                + config1.getString("complex-app.something"));

    }
}
