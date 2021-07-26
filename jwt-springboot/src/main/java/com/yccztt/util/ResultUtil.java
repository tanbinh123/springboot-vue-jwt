package com.yccztt.util;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hyz
 * @Date 2021/7/26
 */
@Data
@NoArgsConstructor
public class ResultUtil<T> {

    private T data;
    private boolean status = true;
    private String errMsg = "";

    public ResultUtil(T data) {
        this.data = data;
    }

}
