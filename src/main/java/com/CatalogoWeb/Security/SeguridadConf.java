
package com.CatalogoWeb.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.CatalogoWeb.Servicios.UsuarioServicio;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadConf extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UsuarioServicio usuarioservicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(usuarioservicio)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.headers().frameOptions().sameOrigin().and()
                .authorizeRequests()
                .antMatchers("/static/**")
                .permitAll()
        .and().formLogin()
                .loginPage("/usuario")
                    .loginProcessingUrl("/logincheck")//acá no hace falta y en la vista NO va la ruta completa
                    .usernameParameter("usuario")
                    .passwordParameter("contrasenia")
                    .defaultSuccessUrl("/usuario")//la ruta es la completa
                    .permitAll()
                .and().logout()
                    .logoutUrl("/usuario/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll();
                }
    
}
