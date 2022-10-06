package com.juansanta.disney.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
@Builder
public class EmailToSend {

    private String from;
    private String subject;
    private String to;
    private String content;

}
