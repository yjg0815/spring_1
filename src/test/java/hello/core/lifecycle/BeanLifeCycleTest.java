package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
        // close()를 호출해주기 위해 ConfigurableApplicationContext를 사용
    }

    @Configuration
    static class LifeCycleConfig{

        @Bean(initMethod = "init", destroyMethod = "close")
        //메서드 이름을 자유롭게 설정 가능하고, 스프링에 의존 x => 코드 못고치는 외부 라이브러리에서도 적용 가능
        // destroyMethod => 기본값이 inferred로 설정 => close나 shutdown을 자동으로 찾아서 호출해줌
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}

/*
스프링 빈은 보통 객체 생성 후 의존관계를 주입한다.
(생성자 주입만 별도 => 생성자 주입은 객체 생성과 동시에 의존관계가 주입된다)
의존관계 주입까지 끝난 후에야 데이터 활용이 가능하기 때문에 그 시점이 중요함
==> 개발자가 이 시점을 어떻게 알 수 있을까?

스프링이 의존관계 주입이 완료되면 >콜백 메서드<를 통해서 초기화 시점을 알려줌

스프링 빈의 이벤트 라이프사이클
스프링 컨테이너 생성 => 스프링 빈 생성 => 의존관계 주입 => 초기화 콜백 => 사용 => 소멸전 콜백 => 스프링 종료
 */

/*
객체의 생성과 초기화는 분리하는게 좋다.
 */
