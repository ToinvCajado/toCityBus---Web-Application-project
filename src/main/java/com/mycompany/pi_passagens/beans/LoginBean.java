package com.mycompany.pi_passagens.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String login;
    private String senha;

    private AuthenticationManager getAuthManager() {
        return (AuthenticationManager) WebApplicationContextUtils
            .getRequiredWebApplicationContext(
                (jakarta.servlet.ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext()
            ).getBean(AuthenticationManager.class);
    }

    public String autenticar() {
        try {
            Authentication auth = getAuthManager().authenticate(
                new UsernamePasswordAuthenticationToken(login, senha)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "/home?faces-redirect=true";
        } catch (AuthenticationException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login inválido", "Usuário ou senha incorretos."));
            return null;
        }
    }

    public String logout() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
        if (session != null) session.invalidate();
        SecurityContextHolder.clearContext();
        return "/login?faces-redirect=true";
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
