//package premasterprojecten.springboot.config;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@EnableWebSecurity
//@Slf4j
//public class SecurityConfig extends WebSecurityConfiguration {
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//    }
//
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
////        log.info("Testing encoder password {}",passwordEncoder.encode("test"));
////
////        auth.inMemoryAuthentication()
////                .withUser("daniel")
////                .password(passwordEncoder.encode("admin"))
////                .roles("USER");
////    }
//}