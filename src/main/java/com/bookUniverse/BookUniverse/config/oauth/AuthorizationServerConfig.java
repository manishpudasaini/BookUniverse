package com.bookUniverse.BookUniverse.config.oauth;

import com.bookUniverse.BookUniverse.config.props.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer
/**
 * enable the automatic configuration of class ie; SecurityProperties
 * class SecurityProperties that contains the configuration properties you want to bind.
 */
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
public class AuthorizationServerConfig implements AuthorizationServerConfigurer {
    /**
     * DataSource represents a database connection source that allows you to connect to a database and execute SQL queries.
     */
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final SecurityProperties securityProperties;
    private final AuthenticationManager authenticationManager;
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    private TokenStore tokenStore;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .passwordEncoder(this.passwordEncoder)  //password encoder bean that is responsible for encoding and decoding passwords securely
                /**
                 *  This configuration allows anyone, even people who haven't logged in, to access a special endpoint where the server's public key is stored
                 *  This public key is used by applications to make sure that the tokens they receive from the server are genuine.
                 */
                .tokenKeyAccess("permitAll()")
                /**
                 * This configuration controls who can check if a token (like a login or access token) is valid.
                 * When you set it to "isAuthenticated," it means only people who have already logged in and have a valid token can use this feature.
                 */
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * @param clients
     * @throws Exception
     * ClientDetailsServiceConfigurer is a class provided by Spring Security that allows you to configure OAuth 2.0 clients.
     * client represents an application or service that wants to access protected resources
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         * This method configures the ClientDetailsServiceConfigurer to use the specified data source to fetch client details.
         * client details, such as client IDs, client secrets, and other client-specific information from database
         */
        clients.jdbc(this.dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .pathMapping("/oauth/token", "/abs/login") //maps the endpoint "/oauth/token" to a different endpoint, "/abs/login."
                .authenticationManager(this.authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .reuseRefreshTokens(false)
//                .userDetailsService(this.userDetailsService)  ToDo
                .tokenStore(tokenStore());
    }

    /**
     * manage and store OAuth 2.0 tokens.
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        if (tokenStore == null) {
            tokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        }
        return new JdbcTokenStore(dataSource);
    }

    /**
     * will create jwt token for authorization server
     * this bean is used to convert OAuth 2.0 access tokens to JWT (JSON Web Tokens) format.
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        if (jwtAccessTokenConverter != null) {
            return jwtAccessTokenConverter;
        }
        SecurityProperties.JwtProperties jwtProperties = securityProperties.getJwt();
        KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));
        JwtAccessTokenConverter customTokenConverter = new JwtAccessTokenConverter();
        customTokenConverter.setKeyPair(keyPair);
        return customTokenConverter;
    }

    private KeyPair keyPair(SecurityProperties.JwtProperties jwtProperties, KeyStoreKeyFactory keyStoreKeyFactory) {
        return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(), jwtProperties.getKeyPairPassword().toCharArray());
    }

    private KeyStoreKeyFactory keyStoreKeyFactory(SecurityProperties.JwtProperties jwtProperties) {
        return new KeyStoreKeyFactory(jwtProperties.getKeyStore(), jwtProperties.getKeyStorePassword().toCharArray());
    }
}
