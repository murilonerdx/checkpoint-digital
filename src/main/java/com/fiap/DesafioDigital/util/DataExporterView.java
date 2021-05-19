package com.fiap.DesafioDigital.util;

import com.fiap.DesafioDigital.dao.SellerDAO;
import com.fiap.DesafioDigital.model.Seller;
import com.fiap.DesafioDigital.model.Setup;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class DataExporterView implements Serializable {

    private List<Setup> setups;

    private Exporter<DataTable> textExporter;

    @Inject
    private SellerDAO service;

    @PostConstruct
    public void init() {
        setups = service.getAllSetup();
        textExporter = new TextExporter();
    }

    public List<Setup> getSetups() {
        return setups;
    }

    public void setSetups(List<Setup> setups) {
        this.setups = setups;
    }

    public SellerDAO getService() {
        return service;
    }

    public void setService(SellerDAO service) {
        this.service = service;
    }

    public Exporter<DataTable> getTextExporter() {
        return textExporter;
    }

    public void setTextExporter(Exporter<DataTable> textExporter) {
        this.textExporter = textExporter;
    }


}