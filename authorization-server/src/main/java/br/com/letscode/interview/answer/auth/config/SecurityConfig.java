package br.com.letscode.interview.answer.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    @Bean(BeanIds.USER_DETAILS_SERVICE)
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(4);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
        throws Exception{

        JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> authenticationManagerBuilder =
            auth.jdbcAuthentication()
                .passwordEncoder(encoder())
                .dataSource(dataSource);

        authenticationManagerBuilder.getUserDetailsService().setEnableGroups(true);
        authenticationManagerBuilder.getUserDetailsService().setEnableAuthorities(false);
    }
}
