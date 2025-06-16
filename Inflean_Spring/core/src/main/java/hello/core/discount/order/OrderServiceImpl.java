package hello.core.discount.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    // memberRepository 에서 member 찾기
    private final MemberRepository memberRepository= new MemoryMemberRepository(); // 멤버 구현체 생성
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 할인정책 구현체 생성

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId); // 먼저 회원정보 조회
        int discountPrice = discountPolicy.discount(member, itemPrice); // 할인정책에 따라 할인값 가져오기

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
