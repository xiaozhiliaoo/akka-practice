package ch6.spring;

import akka.actor.Extension;
import akka.actor.Props;
import org.springframework.context.ApplicationContext;

public class SpringExt  implements Extension {
    private ApplicationContext applicationContext;

    public void initApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Props createProps(String actorBeanName) {
        return Props.create(SpringDI.class, this.applicationContext, actorBeanName);
    }
}
