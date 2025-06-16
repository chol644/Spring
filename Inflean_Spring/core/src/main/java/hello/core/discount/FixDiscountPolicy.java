package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000; //1000원 할인

    @Override
    public int discount(Member member, int price){
        if (member.getGrade() == Grade.VIP){ // VIP인경우
            return discountFixAmount;
        } else { //VIP 아닌경우
            return 0;
        }
    }
}
