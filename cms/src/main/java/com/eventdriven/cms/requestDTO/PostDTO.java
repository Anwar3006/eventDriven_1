package com.eventdriven.cms.requestDTO;

import com.eventdriven.cms.domain.POST_STATUS;

import lombok.Data;

@Data
public class PostDTO {
    private String title;
    private String content;
    private POST_STATUS status;
    private Long author;
}
