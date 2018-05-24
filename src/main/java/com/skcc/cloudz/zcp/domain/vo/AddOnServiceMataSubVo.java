package com.skcc.cloudz.zcp.domain.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AddOnServiceMataSubVo {
    private String name;
    private int order;
    private String url;
    private String target;
}
