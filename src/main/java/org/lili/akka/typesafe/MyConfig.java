package org.lili.akka.typesafe;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author lili
 * @date 2021/5/23 12:25
 */
public class MyConfig {
    public static void main(String[] args) {
        Config load = ConfigFactory.load("");
    }
}