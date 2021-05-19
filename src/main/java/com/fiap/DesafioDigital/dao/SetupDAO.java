package com.fiap.DesafioDigital.dao;

import com.fiap.DesafioDigital.EntityManagerFacade;
import com.fiap.DesafioDigital.model.Setup;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class SetupDAO {

	private EntityManager manager = EntityManagerFacade.getEntityManager();

	public void save(Setup setup) {
		manager.getTransaction().begin();
		manager.persist(setup);
		manager.flush();
		manager.getTransaction().commit();
		manager.clear();
	}

	public List<Setup> getAll() {
		String jpql = "SELECT s FROM Setup s";
		TypedQuery<Setup> query = manager.createQuery(jpql, Setup.class);
		return query.getResultList();
	}

	public Setup findByEmail(String email){
		String jpql = "SELECT s FROM Setup s where s.email =:email";
		TypedQuery<Setup> query = manager.createQuery(jpql, Setup.class);
		query.setParameter("email",email);
		return query.getSingleResult();
	}

	public Setup findById(Long id){
		return manager.find(Setup.class,id);
	}

	public void update(Setup obj){
		manager.getTransaction().begin();
		manager.merge(obj);
		manager.flush();
		manager.getTransaction().commit();
		manager.clear();
	}

	public void delete(Long id){
		Setup obj = findById(id);
		manager.getTransaction().begin();
		manager.remove(obj);
		manager.getTransaction().commit();
		manager.clear();
	}
}
