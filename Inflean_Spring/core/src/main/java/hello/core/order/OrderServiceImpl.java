package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService {

    // 변경 전 (DIP, OCP 위반)
    // memberRepository 에서 member 찾기
    // private final MemberRepository memberRepository = new MemoryMemberRepository(); // 멤버 구현체 생성
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 할인정책 구현체 생성(Fix)
    // 구체(구현) 클래스에도 의존 => DIP 위반.
    // DIP 위반하지 않으려면 추상(인터페이스)에만 의존하도록 변경해야 함
    // FixDiscountPolicy를 RateDiscountPolicy로 변경하는 순간 OderServiceImpl의 소스코드도 함께 변경해야함 => OCP 위반
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 할인정책 구현체 생성(Rate)

    // 변경 후 NPE 발생
    // 누군가가 클라이언트인 OrderServiceImpl, DiscountPolicy 의 구현 객체를 대신 생성하고 주입해야 함
    private final DiscountPolicy discountPolicy;
    private final MemberRepository memberRepository;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId); // 먼저 회원정보 조회
        int discountPrice = discountPolicy.discount(member, itemPrice); // 할인정책에 따라 할인값 가져오기

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
