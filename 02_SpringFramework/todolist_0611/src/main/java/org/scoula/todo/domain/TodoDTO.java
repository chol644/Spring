package org.scoula.todo.domain;

import lombok.Data;

@Data
public class TodoDTO {
    private long id;
    private String title;
    private String description;
    private boolean done;

}
