package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
//DiscountPolicy 하위의 두개의 클래스가 다 빈으로 등록 돼서 에러가 터짐
/*
이 문제를 해결하는데 3가지 방법
1. @Autowired 필드 명 매칭
2. @Qualifier -> @Qualifier끼리 매칭 => 빈 이름 매칭
3. @Primary 사용
 */

@Component
//@Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount = 1000; // 1000원 할인

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        }
        else {
            return 0;
        }
    }
}
