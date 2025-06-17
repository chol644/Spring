package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.order.OrderService;
import hello.core.discount.order.OrderServiceImpl;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;

public class AppConfig {
    // 생성자 주입
    public MemberService memberService(){
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(
                new MemoryMemberRepository(),
                new FixDiscountPolicy());
    }
}
