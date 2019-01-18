package com.ubisys.drone.config.security.jwt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ubisys.drone.common.constant.SecurityConstant;
import com.ubisys.drone.common.utils.ResponseUtil;
import com.ubisys.drone.common.vo.TokenUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * JWT拦截器
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private Boolean tokenRedis;

    private Integer tokenExpireTime;

    private StringRedisTemplate redisTemplate;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, Boolean tokenRedis, Integer tokenExpireTime, StringRedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenRedis = tokenRedis;
        this.tokenExpireTime = tokenExpireTime;
        this.redisTemplate = redisTemplate;
    }

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(SecurityConstant.HEADER);
        if (StringUtils.isBlank(header)) {
            header = request.getParameter(SecurityConstant.HEADER);
        }
        Boolean notValid = StringUtils.isBlank(header) || (!tokenRedis && !header.startsWith(SecurityConstant.TOKEN_SPLIT));
        if (notValid) {
            chain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(header, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            e.toString();
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header, HttpServletResponse response) {

        // 用户名
        String username = null;
        // 权限
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (tokenRedis) {
            // redis
            String v = redisTemplate.opsForValue().get(SecurityConstant.TOKEN_PRE + header);
            if (StringUtils.isBlank(v)) {
                ResponseUtil.out(response, ResponseUtil.resultMap(false, 401, "登录已失效，请重新登录"));
                return null;
            }
            TokenUser user = new Gson().fromJson(v, TokenUser.class);
            username = user.getUsername();
            for (String ga : user.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(ga));
            }
            if (tokenRedis && !user.getSaveLogin()) {
                // 若未保存登录状态重新设置失效时间
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, v, tokenExpireTime, TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE, v, tokenExpireTime, TimeUnit.MINUTES);
            }
        } else {
            // JWT
            try {
                // 解析token
                Claims claims = Jwts.parser()
                        .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                        .parseClaimsJws(header.replace(SecurityConstant.TOKEN_SPLIT, ""))
                        .getBody();

                //获取用户名
                username = claims.getSubject();
                //获取权限
                String authority = claims.get(SecurityConstant.AUTHORITIES).toString();
                log.info(" 用户的权限为  ");
                if (StringUtils.isNotBlank(authority)) {
                    List<String> list = new Gson().fromJson(authority, new TypeToken<List<String>>() {
                    }.getType());
                    for (String ga : list) {
                        //权限信息
                        authorities.add(new SimpleGrantedAuthority(ga));
                    }
                }
            } catch (ExpiredJwtException e) {
                ResponseUtil.out(response, ResponseUtil.resultMap(false, 401, "登录已失效，请重新登录"));
            } catch (Exception e) {
                log.error(e.toString());
                ResponseUtil.out(response, ResponseUtil.resultMap(false, 500, "解析token错误"));
            }
        }

        if (StringUtils.isNotBlank(username)) {
            // 此处password不能为null
            User principal = new User(username, "", authorities);
            log.info(" 用户的权限集合为 " + authorities);
            return new UsernamePasswordAuthenticationToken(principal, null, authorities);
        }
        return null;
    }
}
