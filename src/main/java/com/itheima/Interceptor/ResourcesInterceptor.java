package com.itheima.Interceptor;

import com.itheima.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class ResourcesInterceptor extends HandlerInterceptorAdapter {
    @Value("#{'${ignoreUrl}'.split(',')}")
    private List<String> ignoreUrl;

    public ResourcesInterceptor() {
    }

    public ResourcesInterceptor(List<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }

    public List<String> getIgnoreUrl() {
        return ignoreUrl;
    }

    public void setIgnoreUrl(List<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        //获取请求路径
        String uri = request.getRequestURI();
        //用户登录的相关请求，放行
        if (uri.contains("login")) {
            return true;
        }
        //如果用户是已经登录的状态，判断访问资源的权限
        if (user != null) {
            //如果是管理员，放行
            if ("ADMIN".equals(user.getRole())) {
                return true;
            } else {
                for (String url : ignoreUrl) {
                    //访问的资源不是管理员权限的资源，放行
                    if (uri.contains(url)) {
                        return true;
                    }
                }
            }
        }
        //其他情况都跳转到登录页面
        request.setAttribute("msg", "您还没有登录，请先登录!");
        request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
        return false;
    }

}
