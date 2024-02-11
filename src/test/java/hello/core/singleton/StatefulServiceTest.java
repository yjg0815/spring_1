package hello.core.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA : A 사용자 10000원 주문
        statefulService1.order("userA", 10000);
        //ThreadB : B 사용자 20000원 주문
        statefulService2.order("userA", 20000);

        //ThreadA : 사용자 A 주문 금액 조회
        int price = statefulService1.getPrice();
        // 사용자 A가 주문을 넣고 주문 금액을 빼기 전에, 사용자 B가 주문을 넣으면
        // 어차피 인스턴스를 공유하기 때문에 price 값이 바뀌어서, 20000이 튀어나오게 됨

        // ==> 스프링을 항상 무상태로 설계하자 (공유 필드 조심) => 지역변수 활용

        System.out.println("price = " + price);

        org.assertj.core.api.Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }


}