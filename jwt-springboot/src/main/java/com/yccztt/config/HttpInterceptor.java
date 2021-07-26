package com.yccztt.config;

import com.alibaba.fastjson.JSON;
import com.yccztt.util.ResultUtil;
import com.yccztt.util.UserSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤器
 * @Author hyz
 * @Date 2021/7/26
 */
@Configuration
public class HttpInterceptor implements HandlerInterceptor {

    @Autowired
    private UserSecurityUtil userSecurityUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //为了处理跨域请求,如果发送的是 OPTIONS 直接正常返回
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        //设置请求头和响应头的编码格式
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        //校验请求头的 Token 是否合法
        boolean isOk = userSecurityUtil.verifyWebToken(request, response);

        if (!isOk) {
            ResultUtil<String> resultUtil = new ResultUtil<>();
            resultUtil.setErrMsg("请重新登录");
            resultUtil.setStatus(false);
            response.getWriter().write(JSON.toJSONString(resultUtil));
            return false;
        }
        return true;
    }

}
