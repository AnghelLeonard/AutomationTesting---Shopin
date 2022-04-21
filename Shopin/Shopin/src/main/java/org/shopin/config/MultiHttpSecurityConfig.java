package org.shopin.config;

import javax.sql.DataSource;
import org.shopin.admin.UserDetailsServiceAdapter;
import org.shopin.service.CurrentUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MultiHttpSecurityConfig {

    @Configuration    
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Value("${number.rememberme.seconds}")
        private int seconds;

        @Autowired
        private DataSource dataSource;

        @Autowired
        private CurrentUserDetailsService userDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            // @formatter:off
            http
                    .formLogin().loginPage("/login").successForwardUrl("/").defaultSuccessUrl("/")
                    .failureUrl("/login?error").usernameParameter("email").passwordParameter("password").permitAll()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/", "/login/**", "/pending/**", "/activate/**", "/cart/**", "/purchase/**", "/done/**", "/thanks/**", "/signup/**",
                            "/register/**", "/reset/**", "/newpassword/**", "/products/**", "/product/**", "/orderguest/**", "/contact/**",
                            "/css/**", "/js/**", "/images/**", "/fonts/**",
                            "/favicon.ico").permitAll()
                    .antMatchers("/ordermember/**").hasAuthority("ROLE_MEMBER")
                    .anyRequest().permitAll()
                    .and()
                    .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll()
                    .and()
                    .exceptionHandling()
                    .and()
                    //.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    //.sessionRegistry(sessionRegistry())
                    .and()
                    //.sessionFixation().none()                                       
                    //.and()
                    .rememberMe().tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(seconds) // 2 minutes
                    .rememberMeParameter("remember-me")
                    .and()
                    .csrf();
            // @formatter:on
        }

        @Autowired
        public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(new BCryptPasswordEncoder());
        }

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
            jdbcTokenRepositoryImpl.setDataSource(dataSource);
            return jdbcTokenRepositoryImpl;
        }

        @Bean
        @Order(2)
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        SessionRegistry sessionRegistry() {
            return new SessionRegistryImpl();
        }
    }

    @Configuration
    @Order(1)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserDetailsServiceAdapter userDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            // @formatter:off         
            http
                    //.antMatcher("/x10qerK0/**") 
                    .antMatcher("/x10qerK0")
                    .authorizeRequests()
                    .antMatchers("/x10qerK0").hasRole("PRE_AUTH_ADMIN")
                    //.antMatchers("/x10qerK0/master").hasRole("FULL_AUTH_ADMIN")  
                    .anyRequest().permitAll()
                    .and()
                    .httpBasic();
            // @formatter:on
        }

        @Autowired
        public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }
    }
}
