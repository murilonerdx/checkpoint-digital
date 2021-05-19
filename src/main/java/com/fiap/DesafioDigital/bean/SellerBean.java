package com.fiap.DesafioDigital.bean;

import com.fiap.DesafioDigital.dao.SellerDAO;
import com.fiap.DesafioDigital.dao.SetupDAO;
import com.fiap.DesafioDigital.model.Seller;
import com.fiap.DesafioDigital.model.Setup;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class SellerBean {
    String username;
    String email;

    private Seller seller = new Seller();

    public void save() {
        SellerDAO dao = new SellerDAO();
        Seller sellerBanco = dao.findSeller(this.seller.getEmail());
        System.out.println(sellerBanco);
        if (sellerBanco == null) {
            new SellerDAO().save(this.seller);
            getSellers().add(seller);
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Registered seller"));
        } else {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error when registering, email already exists", "Error when registering"));
        }


    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        Seller logged = new SellerDAO().login(seller);
        if (logged != null) {
            context.getExternalContext().getSessionMap().put("seller", logged);
            return "index?faces-redirect=true";
        }
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login invalido", "Erro"));
        return "login?faces-redirect=true";
    }

    public List<Seller> getSellers() {
        return new SellerDAO().getAll();
    }

    public Seller getSeller() {
        return seller;
    }

    public String logout() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.remove("seller");
        return "login?faces-redirect=true";
    }


    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}