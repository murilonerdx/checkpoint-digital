package com.fiap.DesafioDigital.util;

import com.fiap.DesafioDigital.model.Seller;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.swing.text.NavigationFilter;
import java.util.Map;

public class AuthorizationListener implements PhaseListener {
    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        String viewId = context.getViewRoot().getViewId();
        if (viewId.equals("/login.xhtml")) return;
        if (viewId.equals("/cadastrar.xhtml")) return;

        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();

        Seller seller = (Seller) sessionMap.get("seller");
        if (seller == null) {
            NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(context, "", "login?faces-redirect=true");
        }

    }

    @Override
    public void beforePhase(PhaseEvent event) {

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
