package com.kartik.ecommerce_youtube.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //        similar kind of stuff in node but simple in node

//        token fetch

        String jwt=request.getHeader(JwtConstant.JWT_HEADER);

        //        if this has header then proceed

        if(jwt!=null){
//            Bearer jkjfhjf to exrtract bearer need only token

            jwt=jwt.substring(7);

            try{
                SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

                Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
//                fetching claims but where we are setting it so for tht jwtprovider c;lass build

                String email=String.valueOf(claims.get("email"));

                String authorities=String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> auths= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication authentication=new UsernamePasswordAuthenticationToken(email,null,auths);

                //if token not null above then set authetication token which we have passed chk
        // if token validate
        // then dofilter next then throiw error

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch(Exception e){
throw new BadCredentialsException("invalid token from jwt validator");
            }
        }
filterChain.doFilter(request,response);
    }
}
