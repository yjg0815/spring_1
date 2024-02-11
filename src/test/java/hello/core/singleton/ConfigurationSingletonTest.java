package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);


        /*
        AppConfig에서 new로 여러번 호출한 것 같은데, 출력해보면 모두 같은 인스턴스이다.
        @Configuration 에서 해준다/
         */
    }

    @Test
    void configurationDepp() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());

        /*
        순수한 클래스라면 class hello.core.AppConfig 까지만 출력되어야 함.
        but 출력 값 : bean = class hello.core.AppConfig$$SpringCGLIB$$0
        ==> AppConfig를 상속 받은 다른 클래스를 등록 한 것 (바이트 코드를 조작 해서..)
        이걸 @Configuration에서 해주는 것.
        @Bean만 사용해도 스프링 빈으로 등록해주지만, 이것만으로는 싱글톤 보장 x
         */
    }
}
