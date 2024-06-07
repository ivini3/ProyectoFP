package com.example.myapp.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.headers(
                                headersConfigurer -> headersConfigurer
                                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
                /*
                 * o Las páginas bajo /public serán accesibles a cualquier visitante.
                 * o La vista general de productos también será accesible a cualquier visitante.
                 * o El rol USER permitirá añadir valoraciones de productos, pero no borrarlas
                 * ni editarlas.
                 * o El rol MANAGER que podrá acceder al CRUD de productos, categorías y
                 * valoraciones de productos,
                 * pero no al CRUD de Usuarios.
                 * o El rol ADMIN al que tendrá a todas las operaciones de la aplicación.

                 */
                http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() //ACORDARME DE ESTO
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll() // para rutas: /css, /js images
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/productos/**").permitAll()
                                .requestMatchers("/valoracion/new/**", "/valoracion/pro/**")
                                .hasAnyRole("ADMIN", "MANAGER", "USER")
                                .requestMatchers("/borrarProducto/**", "/editarProducto/**", "/producto/editar/**",
                                                "/nuevoProducto", "/productoagregrado", "/valoracion/**"
                                                 )
                                .hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers("/usuarios", "/nuevoUsuario/**", "/editarUsuarios/**",
                                                "/borrarUsuario/**")
                                .hasRole("ADMIN")
                                
                               
                                .anyRequest().authenticated())
                                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                                                        .loginPage("/signin") // mapping par mostrar formulario de login

                                                        .loginProcessingUrl("/login") // ruta post de /signin

                                                        .failureUrl("/signin")
                                                        .defaultSuccessUrl("/", true).permitAll())
                                        .logout((logout) -> logout
                                                        .logoutSuccessUrl("/").permitAll())
                                .csrf(csrf -> csrf.disable())
                                .rememberMe(Customizer.withDefaults())
                                .httpBasic(Customizer.withDefaults());
                http.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/accessError"));
                return http.build();
        }
}