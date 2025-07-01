package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// MemberService 인터페이스를 구현한 클래스, 실제로 회원을 저장하고 조회하는 기능을 수행
@Component
public class MemberServiceImpl implements MemberService {

    // 회원 저장소 역할을 하는 객체를 선언함
    // 인터페이스 타입(MemberRepository)으로 선언하여 나중에 구현체만 바꿔 끼울 수 있도록 함
    // 지금은 MemoryMemberRepository(메모리 기반 저장소)를 직접 생성해서 사용
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입 기능 구현
    // 매개변수로 받은 member 객체를 memberRepository에 저장함
    @Override
    public void join(Member member) {
        memberRepository.save(member); // 회원 정보 저장
    }

    // 회원 조회 기능 구현
    // 주어진 회원 ID(memberId)에 해당하는 회원을 memberRepository에서 찾아서 반환함
    @Override
    public Member findMember(Long memberId) {
        // 회원 ID로 회원 조회 후 반환
        return memberRepository.findById(memberId);
    }
}
