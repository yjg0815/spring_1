package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //할인 정책을 변경하려면 이 코드를 고쳐야 함 ==> OCP 위반 (클라이언트 코드를 고쳐야 함)
    //orderservieimple이 실제 구체 클래스에서도 의존하고 있음 ==> DIP 위반
    
    // 이 문제를 어떻게 해결할까 => DIP를 위반하지 않도록 인터페이스만 의존하도록 설계
    
    private final DiscountPolicy discountPolicy;
    // 근데 이렇게 하면 nullpointexception이 생김 => 당연함 실제 구체가 없으니까
    // 어떻게 해결? 누군가 구체를 대신 생성해서 집어넣어줘야함
    // ==> AppConfig : 구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스


    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
