package ru.utelksp.upo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.utelksp.upo.service.security.UserDetailsServiceImpl;

/**
 * Конфигуратор авторизации.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] SECURED_PAGE_USER = {"/program", "/certificate", "/computer", "/employee", "/order", "/organization", "/typeUsing"};
    private static final String[] SECURED_PAGE_ADMIN = {"/journalEvent", "/user"};
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Конфигурирует глобальные настройки сервиса управления пользователяим и энкодер.
     *
     * @param auth менеджер аутентификации.
     * @throws Exception Исключения.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder())
                .and()
                .authenticationEventPublisher(authenticationEventPublisher());
    }

    /**
     * Конфигурирует доступ к http.
     *
     * @param http - HTTP запрос.
     * @throws Exception Исключения.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .authorizeRequests()
                .antMatchers("/VAADIN/**", "/manifest.json", "/sw.js", "/offline-page.html").permitAll()
                .anyRequest().authenticated()
                .antMatchers(SECURED_PAGE_USER).hasAnyRole("USER")
                .antMatchers(SECURED_PAGE_ADMIN).hasAnyRole("ADMIN");

        http.logout().permitAll()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .and()
                .formLogin().permitAll();
    }

    /**
     * Возвращает энкодер пароля.
     *
     * @return шифровшик пароля
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(9);
    }

    @Bean
    public DefaultAuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }
}