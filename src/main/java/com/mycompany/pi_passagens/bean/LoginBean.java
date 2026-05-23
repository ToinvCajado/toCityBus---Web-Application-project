package com.mycompany.pi_passagens.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("loginBean" )
@SessionScoped
public class LoginBean implements Serializable {

    private String login;
    private String senha;

    public String autenticar() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
                    .getExternalContext().getRequest();
            
            // Realiza o login e cria a sessão no Spring Security
            request.login(login, senha);
            
            return "/home.xhtml?faces-redirect=true";
        } catch (ServletException e) {
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
        return "/login.xhtml?faces-redirect=true";
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
