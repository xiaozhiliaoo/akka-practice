package ch6.spring;

import org.springframework.stereotype.Service;

/**
 * @author lili
 * @date 2022/4/10 16:28
 */
@Service
public class MyService {
    public void saveMsg(Object message) {
        System.out.println("Save Message:" + message);
    }
}
