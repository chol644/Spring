package org.scoula.ex03.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TodoDTO {
    private String title;

    // 날짜 형식을 지정하여 문자열을 Date 객체로 자동 변환
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date dueDate;

    // 다양한 날짜 형식 예시
    // @DateTimeFormat(pattern="yyyy-MM-dd")      // 2023-01-01
    // @DateTimeFormat(pattern="yyyy.MM.dd")      // 2023.01.01
    // @DateTimeFormat(pattern="yyyy/MM/dd HH:mm") // 2023/01/01 14:30
}