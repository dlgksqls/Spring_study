package hello.proxy.pureproxy.concreteproxy;

import hello.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import hello.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import hello.proxy.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy(){
        ConcreteLogic logic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(logic);
        client.execute();
    }

    @Test
    void addProxy(){
        ConcreteLogic logic = new ConcreteLogic();
        ConcreteLogic proxy = new TimeProxy(logic);
        // TimeProxy proxy = new TimeProxy(logic); 도 가능 (다형성에 의해)
        ConcreteClient client = new ConcreteClient(proxy);
        client.execute();
    }
}
