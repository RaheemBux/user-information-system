package com.sapphire.testsapphire.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
class AbstractDTO {
    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;
    private String status;
}

