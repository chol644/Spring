package org.scoula.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    /**
     * 메시지 발신자 이름
     * - 클라이언트에서 입력한 사용자 이름
     * - 메시지 출력 시 "홍길동: 안녕하세요" 형태로 표시
     */
    private String name;

    /**
     * 메시지 내용
     * - 실제 채팅 텍스트
     */
    private String content;


}