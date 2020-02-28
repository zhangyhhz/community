package com.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private String notifier;
    private String notifierName;
    private Long outerId;
    private String outerTitle;
    private String typeName;
    private int type;
}
