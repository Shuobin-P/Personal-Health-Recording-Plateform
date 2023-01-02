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
            String username = tokenUtils.getUsernameByToken(token);
            log.info("Authentication是否为空：" + SecurityContextHolder.getContext().getAuthentication());
            //FIXME 为啥小程序登录找不到之前创建的Authentication，难道是因为自己创建的那个authentication有问题，SpringSecurity管理不了？
            // 感觉跟SpringSecurity的认真流程有关系。
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (!tokenUtils.isExpired(token)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    //就算是后台管理系统，一个用户登录之后，会创建一个authentication，存储到SecurityContext中，
                    //后面继续发送请求应该不会创建新的authentication，而是找到之前创建的SecurityContext
                    log.info("根据jwt创建新的authentication");
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    //UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null);

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        logger.info("JWT过滤器完成过滤");
        filterChain.doFilter(request, response);
    }

}
