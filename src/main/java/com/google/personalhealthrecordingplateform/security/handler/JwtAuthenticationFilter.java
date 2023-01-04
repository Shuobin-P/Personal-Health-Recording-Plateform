package com.google.personalhealthrecordingplateform.security.handler;

import com.google.personalhealthrecordingplateform.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这个过滤器是用来处理已经登录过的，即请求中包含token的用户请求。
 *
 * @author 31204
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    private TokenUtils tokenUtils;
    private UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(TokenUtils tokenUtils, @Qualifier("sysUserDetailsServiceImp") UserDetailsService userDetailsService) {
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
    }

    //TODO 与后端进行通信使用http协议，而不是使用https，jwt容易被窃取
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("进入JWT过滤器");
        String header = request.getHeader(tokenHeader);
        //不要先检测这个token是否发生了篡改吗？但是https可以防止发送的报文发生篡改啊。
        //不要防止token被别人拿走吗？答：但是https可以防窃听。
        //不要检查这个token是否合法吗？即将header+payload进行计算，再与token中的signature进行比对，检查这个token是否是我们服务器生成的。答：TokenUtil.getTokenBody()有这个功能。
        //问题：但是在使用jwt的时候，没有session，那么是如何保存SecurityContext的呢？
        //要么创建SecurityContext，要么拿之前被存储到个地方的SecurityContext
        if (header != null && header.startsWith(tokenHead)) {
            String token = header.substring(tokenHead.length());
            //FIXME 创建JWT的时候创建了authentication，按道理这里应该可以访问到authentication
            // 但是实际上，并没有啊。SpringSecurity不是会对SecurityContext进行持久化吗？不就意味着其中的authentication也会持久化。
            // 疑问：是不是登录的时候，如果账号密码正确，根本就不要创建authentication，而是在同一个客户端再次进行访问的时候，SecurityContextHolder.getContext().getAuthentication()为空
            // 才创建authentication，才能持久化处理？
            // 官方文档好像没给例子或者说清楚这个事情吧。官方文档如果由案例，我真的想拿它的试一下。
            // 当初就是发现了这个up的代码有点不对劲，但是想着快点写完这个项目，但是到了后面越来越感觉这个up主在乱搞，就想自己改代码，但是
            // 我不知道这一改，前面的实现包括鉴权可能都要改。
            // 最恐怖的是前端vue代码可能也要改。
            // 选择继续自己改代码还是按照这个up主的写。
            log.info("当前线程的SecurityContext是否为空：" + SecurityContextHolder.getContext());
            log.info("当前线程的Authentication是否为空：" + SecurityContextHolder.getContext().getAuthentication());
            if (tokenUtils.isLegal(token)) {
                if (tokenUtils.isExpired(token)) {
                    //因为如果JWT过期了，后端肯定要重新生成JWT，后面发送响应的话，jwt会包含在response
                    request.setAttribute(tokenHeader, tokenHead + tokenUtils.refreshToken(token));
                } else {
                    //JWT没过期的话如何处理？
                    log.info("JWT没有过期");
                }
            }

        }
        logger.info("JWT过滤器完成过滤");
        //若JWT的身份认证失败或者没有包含JWT请求，则把请求往下传
        filterChain.doFilter(request, response);
    }

}
