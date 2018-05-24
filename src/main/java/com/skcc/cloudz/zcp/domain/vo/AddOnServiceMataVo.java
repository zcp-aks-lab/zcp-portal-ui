package com.skcc.cloudz.zcp.domain.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AddOnServiceMataVo {
    private String name;
    private int order;
    private String url;
    private String target;
    private String accessRole;
    private List<AddOnServiceMataSubVo> sub;
}
