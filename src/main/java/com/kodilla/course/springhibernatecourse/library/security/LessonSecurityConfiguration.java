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
        havingValue = "LESSON",
        matchIfMissing = true
)
@EnableWebSecurity
public class LessonSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String ROLE_USER = "USER";
    private final String ROLE_LIBRARIAN = "LIBRARIAN";
    private final String ROLE_ADMIN = "ADMIN";

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("user").roles(ROLE_USER)
                .and()
                .withUser("librarian").password("librarian").roles(ROLE_LIBRARIAN)
                .and()
                .withUser("admin").password("admin").roles(ROLE_ADMIN);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // Wyłącza ochronę przed atakami CSRF

        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/**") //definiujemy wzorzec wywołań, dla którego chcemy założyć ograniczenie. począwszy od gałęzi /books "w głąb" wszystkie operacje GET będą ograniczone.
                .hasAnyRole(ROLE_ADMIN, ROLE_LIBRARIAN, ROLE_USER) //definiujemy, kto może uzyskać dostęp
                .mvcMatchers(HttpMethod.POST, "/**")
                .hasAnyRole(ROLE_ADMIN, ROLE_LIBRARIAN)
                .mvcMatchers(HttpMethod.DELETE, "/**")
                .hasAnyRole(ROLE_ADMIN)
                .anyRequest()
                .fullyAuthenticated() //dostęp do naszej aplikacji mają tylko osoby w pełni zautentykowane
                .and()
                .httpBasic(); //autoryzacja oparta będzie o httpBasic, czyli podstawowe uwierzytelnienie przy pomocy loginu i hasła
    }
}
