package br.com.letscode.interview.answer.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class LocalOAuthAuthorizationServerConfig
        extends AuthorizationServerConfigurerAdapter {

    @Value("${security.oauth2.resource.jwt.signing-key-value}")
    private String jwtSigningKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(
        AuthorizationServerSecurityConfigurer oauthServer)
        throws Exception {
        oauthServer.tokenKeyAccess("permitAll");
        oauthServer.checkTokenAccess("permitAll");
        oauthServer.passwordEncoder(clientPasswordEncoder());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clientDetails)
        throws Exception {
            clientDetails.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
            .accessTokenConverter(jwtAccessTokenConverter())
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService);
    }

    @Bean
    PasswordEncoder clientPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtSigningKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
}