package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import hello.proxy.pureproxy.proxy.code.Subject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest(){
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest(){
        RealSubject realSubject = new RealSubject();
        // 프록시는 realSubject 참조
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        // 클라이언트는 Proxy 참조
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

        // 결국 클라이언트 -> 프록시 -> Subject 이런식으로 참조 하게 됨

        client.execute(); // 프록시 호출 -> 실제 객체 호출
        client.execute(); // 프록시만 호출
        client.execute(); // 프록시만 호출
    }
}
