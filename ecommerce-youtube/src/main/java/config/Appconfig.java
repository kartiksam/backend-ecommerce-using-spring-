package config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class Appconfig {

//first type then method name psvm
    @Bean

    public SecurityFilterChain securityFlterChain(HttpSecurity http) throws Exception{
//      by default we login then we get login foprm by defaiult so we want
//      to stateless that and want our login
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                any reqyuest starting with api need to autheticate it using jwt and other method
//                but with wehat bydef or ourself and other requests we done permit all
                .authorizeHttpRequests(Authorize->Authorize.requestMatchers("/api/**").
                        authenticated().anyRequest().permitAll())
                .addFilterBefore(null,null).csrf().disable()
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration cfg=new CorsConfiguration();
//                        give all apis whwerre we run our front end to access this backedn
//                        in node direcvtly or by coers aklso
                        cfg.setAllowedOrigins(Arrays.asList(

                                "http://localhost:3000/",
                                "http://localhost:4200/"
                        ));
//                        all methods from frontend all allow
                        cfg.setAllowedMethods(Collections.singletonList("*"));
                        cfg.setAllowCredentials(true);
                        cfg.setAllowedHeaders(Collections.singletonList("*"));
//                        same in node we fetch token and split using authrrization
                        cfg.setExposedHeaders(Arrays.asList("Authorization"));
                        cfg.setMaxAge(3600L);
                        return cfg;
                    }
                })
                .and().httpBasic().and().formLogin();



        return http.build();

    }
//    password hashing before saving to database make @bean to use it as a service
@Bean
public PasswordEncoder passwordEncoder(){

}

}
