package com.fiap.DesafioDigital.util;

import com.fiap.DesafioDigital.dao.SellerDAO;
import com.fiap.DesafioDigital.model.Seller;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class SelectOneMenuView {

    private String selectedOption;
    private String rtl;
    private String hideNoSelectOption;
    private String longItemLabel;
    private String labeled;

    private String sellerGroup;
    private List<SelectItem> sellersGroup;
    private List<Seller> sellers ;

    @Inject
    private SellerDAO dao;

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getRtl() {
        return rtl;
    }

    public void setRtl(String rtl) {
        this.rtl = rtl;
    }

    public String getHideNoSelectOption() {
        return hideNoSelectOption;
    }

    public void setHideNoSelectOption(String hideNoSelectOption) {
        this.hideNoSelectOption = hideNoSelectOption;
    }

    public String getSellerGroup() {
        return sellerGroup;
    }

    public void setSellerGroup(String sellerGroup) {
        this.sellerGroup = sellerGroup;
    }

    public List<SelectItem> getSellersGroup() {
        return sellersGroup;
    }

    public void setSellersGroup(List<SelectItem> sellersGroup) {
        this.sellersGroup = sellersGroup;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(List<Seller> sellers) {
        this.sellers = sellers;
    }

    public SellerDAO getDao() {
        return dao;
    }

    public void setDao(SellerDAO dao) {
        this.dao = dao;
    }

    public String getLongItemLabel() {
        return longItemLabel;
    }

    public void setLongItemLabel(String longItemLabel) {
        this.longItemLabel = longItemLabel;
    }

    public String getLabeled() {
        return labeled;
    }

    public void setLabeled(String labeled) {
        this.labeled = labeled;
    }

    @PostConstruct
    public void init() {
        sellers = dao.getAll();
        SelectItemGroup selling = new SelectItemGroup("Vendedores");
        for (Seller seller : sellers) {
            selling.setSelectItems(new SelectItem[]{
                    new SelectItem(seller.getUsername())
            });
        }
    }

}
