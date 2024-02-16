package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;


import static org.assertj.core.api.Assertions.assertThat;


//사실 프로토타입 빈을 쓰는 일은 굉장히 드물다고 한다...

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);


    }

    @Test
    void singletonClientUesPrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {
        //private final PrototypeBean prototypeBean;
        /*
        싱글톤 빈에서 필요한 프로토타입 빈이, 싱글톤 빈이 생성되는 시점에 이미 주입이 되었기 때문에,
        서로 다른 클라이언트가 싱글톤 빈인 클라이언트 빈을 요청했을 때,
        각기 다른 프로토 타입이 생성되어야하는 목적으로 만들었는데, 이미 한번 주입이 끝나버려서
        프로토타입빈임에도 공유하게 되어버림 ==> 이걸 의도한게 아님.

        사용할 때마다 새로 생성하게 만들고 싶은데 어떻게 할까..
         */

        //@Autowired
        //private ObjectProvider<PrototypeBean> prototypeBeanProvider;
        // 스프링 컨테이너에서 Dependency lookup (DL)을 도와주는 역할
        // 스프링 빈으로 등록 안해도 스프링이 자동으로 등록해줌

        //스프링에 의존적인 방식 ==> 스프링에 의존하지 않는 방식 존재 : javax.provider

        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;
        // 자바 표준, 스프링에 의존적이지 않음
        // 심플하다 but 별도의 라이브러리 필요
        // 딱 DL 기능만 제공


        public int logic() {
           // PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            //get을 하면서 항상 새로운 프로토타입 빈을 생성

            PrototypeBean prototypeBean = prototypeBeanProvider.get();

            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
