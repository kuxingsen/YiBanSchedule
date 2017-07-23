package org.zqu.yiban.schedule.interceptor;



import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zqu.yiban.schedule.util.FrameUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zqh on 2017/7/17.
 */
public class AuthorizeInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = Logger.getLogger(AuthorizeInterceptor.class);

    /**
     *
     * @param httpServletRequest 请求内容
     * @param httpServletResponse 返回内容
     * @param o 表示被拦截请求目标的对象如Controller
     * @return 是否要将当前请求拦截，false请求将被终止
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        FrameUtil frameUtil = new FrameUtil(httpServletRequest,httpServletResponse,"3dc8b9e950c61e14","54d426426892253dd6703d56eff7bef2","http://f.yiban.cn/iapp131407");
        boolean perform = frameUtil.perform();
        LOGGER.debug(perform);
        return perform;
    }

    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView 可改变现实的视图或修改发往视图的方法
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
