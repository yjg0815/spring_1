package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}

/**
 * 비즈니스 요구사항 및 설계
 * 회원
 * - 가입 / 조회 가능
 * - 등급 : 일반 / VIP
 * - 회원데이터는 자체 DB 구축 + 외부 시스템 연동 가능(미확정)
 *
 * 주문과 할인 정책
 * - 상품 주문 가능
 * - 등급에 따른 할인 정책 적용
 * - VIP는 고정 금액 할인 적용 (변경 가능)
 * 할인 정책은 변경 가능성이 높음.
 */
