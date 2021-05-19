package com.fiap.DesafioDigital.bean;


import com.fiap.DesafioDigital.dao.SetupDAO;
import com.fiap.DesafioDigital.model.Seller;
import com.fiap.DesafioDigital.model.Setup;

import javax.faces.application.FacesMessage;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class SetupBean {
	private String name;
	private String description;
	private String seller;
	private Double price;

	private Setup setup = new Setup();

	public void save() {
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();

			Seller seller = (Seller) sessionMap.get("seller");
			setup.setSeller(seller);
			setup.setEmail(seller.getEmail());

			new SetupDAO().save(setup);

			FacesContext.getCurrentInstance()
					.addMessage(null, new FacesMessage("Setup cadastrado com sucesso"));
		}catch(Exception e){
			FacesContext.getCurrentInstance()
					.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","Erro ao cadastrar Setup"));
		}

	}
	
	public List<Setup> getSetups() {
		return new SetupDAO().getAll();
	}

	public Setup getSetup() {
		return setup;
	}

	public void setSetup(Setup setup) {
		this.setup = setup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
