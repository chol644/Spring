package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// MemberRepository 인터페이스를 구현한 클래스
// 메모리에 데이터를 저장하는 방식으로 동작하는 저장소 역할
@Component
public class MemoryMemberRepository implements MemberRepository {

    // 회원 정보를 저장할 Map 생성
    // key: 회원 ID (Long 타입), value: 회원 객체(Member)
    // HashMap은 데이터를 key-value 쌍으로 저장함
    private static Map<Long, Member> store = new HashMap<>();

    // 회원 정보를 저장하는 기능 구현
    // 인터페이스에서 정의한 save() 메서드를 실제로 구현함
    @Override
    public void save(Member member) {
        // 회원의 id를 key로 사용해서 store에 저장
        store.put(member.getId(), member);
    }

    // 회원 ID로 회원 정보를 조회하는 기능 구현
    @Override
    public Member findById(Long memberId) {
        // 주어진 ID(memberId)를 key로 사용해 store에서 해당 회원을 찾아 반환
        return store.get(memberId);
    }
}
