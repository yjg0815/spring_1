package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core.member",
        // 어디서부터 하위로 내려들어가서 찾을건지 지정 가능, 여러개 지정 가능
        // 지정 안하면 얘가 붙은 패키지 부터 그 하위에 있는 것들을 뒤짐
        //웬만하면 프로젝트 최상단에 설정정보가 있는 appconfig를 두자.

        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // 수동으로 등록한 AppConfig는 자동 등록한 대상에서 뺴주기 위해 필터 설정
        // 보통 이렇게 안하는데, 이건 앞에서 한 예제 코드들을 유지하기 위해서 그냥 필터 달아줌
)
/*
컴포넌트 스캔 => 설정정보가 없어도 자동으로 스프링 빈을 등록하는 기능, @Component가 달린 것을 찾아서 빈으로 등록해줌
@Autowired를 사용하면 DI도 자동 주입 가능
 */

public class AutoAppConfig {
}
