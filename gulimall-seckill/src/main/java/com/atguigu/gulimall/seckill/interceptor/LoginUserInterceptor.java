package com.atguigu.gulimall.seckill.interceptor;

import com.atguigu.common.constant.AuthServerConstant;
import com.atguigu.common.vo.MemberResponseVo;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Description: 登录拦截器
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-07-02 18:37
 **/

@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberResponseVo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 给订单请求放行
        String uri = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match("/kill", uri);

        // 如果是/kill请求，要做下面的判断
        if(match) {
            //获取登录的用户信息
            MemberResponseVo attribute = (MemberResponseVo) request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);

            if (attribute != null) {
                //把登录后用户的信息放在ThreadLocal里面进行保存
                loginUser.set(attribute);

                return true;
            } else {
                //未登录，返回登录页面
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('请先进行登录，再进行后续操作！');location.href='http://auth.gulimall.com/login.html'</script>");
                // session.setAttribute("msg", "请先进行登录");
                // response.sendRedirect("http://auth.gulimall.com/login.html");
                return false;
            }
        }

        // 否则放行
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
