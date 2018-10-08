package com.skcc.cloudz.zcp.common.domain.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CommonVo {
    private int current;
    private String keyword;
    
    public CommonVo() {
        if (current == 0) this.current = 0; 
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
    
}
