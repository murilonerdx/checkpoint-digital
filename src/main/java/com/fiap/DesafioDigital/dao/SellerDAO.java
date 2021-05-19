package com.fiap.DesafioDigital.dao;

import com.fiap.DesafioDigital.EntityManagerFacade;
import com.fiap.DesafioDigital.model.Seller;
import com.fiap.DesafioDigital.model.Setup;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

@Named
@ApplicationScoped
public class SellerDAO {

    private EntityManager manager = EntityManagerFacade.getEntityManager();

    public void save(Seller seller) {
        manager.getTransaction().begin();
        manager.persist(seller);
        manager.getTransaction().commit();
        manager.clear();
    }

    public List<Seller> getAll() {
        String jpql = "SELECT s FROM Seller s";
        TypedQuery<Seller> query = manager.createQuery(jpql, Seller.class);
        return query.getResultList();

    }

    public Seller login(Seller seller) {
        try {
            TypedQuery<Seller> query = manager.createQuery("SELECT u FROM Seller u WHERE " +
                    "u.email = :email AND " +
                    "u.senha = :password", Seller.class)
                    .setParameter("email", seller.getEmail())
                    .setParameter("password", seller.getSenha());
            return query.getSingleResult();
        } catch (NoResultException e) {
           return null;
        }

    }

    public List<Setup> getAllSetup(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();

        Seller seller = (Seller) sessionMap.get("seller");
        String emailAtual = seller.getEmail();

        TypedQuery<Setup> query = manager.createQuery("SELECT u FROM Setup u WHERE u.email = :email", Setup.class)
                .setParameter("email",emailAtual);
        return query.getResultList();
    }

    public Seller findSeller(String email){
        try {
            TypedQuery<Seller> query = manager.createQuery("SELECT u FROM Seller u WHERE u.email like :email", Seller.class)
                    .setParameter("email",email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}