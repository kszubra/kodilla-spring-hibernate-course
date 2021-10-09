package com.kodilla.course.springhibernatecourse.library.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ConditionalOnProperty(
        value = "security.config.profile",
        havingValue = "TASK",
        matchIfMissing = true
)
@EnableWebSecurity
public class TaskSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String R1 = "R1";
    private final String R2 = "R2";
    private final String R3 = "R3";

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("r1").password("r1").roles(R1)
                .and()
                .withUser("r2").password("r2").roles(R2)
                .and()
                .withUser("r3").password("r3").roles(R3);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // Wyłącza ochronę przed atakami CSRF

        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/**") //definiujemy wzorzec wywołań, dla którego chcemy założyć ograniczenie. począwszy od gałęzi /books "w głąb" wszystkie operacje GET będą ograniczone.
                .hasAnyRole(R1, R2, R3) //definiujemy, kto może uzyskać dostęp
                .mvcMatchers(HttpMethod.POST, "/**")
                .hasAnyRole(R2, R2)
                .mvcMatchers(HttpMethod.DELETE, "/**")
                .hasAnyRole(R1)
                .anyRequest()
                .fullyAuthenticated() //dostęp do naszej aplikacji mają tylko osoby w pełni zautentykowane
                .and()
                .httpBasic(); //autoryzacja oparta będzie o httpBasic, czyli podstawowe uwierzytelnienie przy pomocy loginu i hasła
    }
}
