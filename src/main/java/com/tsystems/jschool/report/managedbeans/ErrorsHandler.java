package com.tsystems.jschool.report.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class ErrorsHandler {

    public String getStatusCode(){
        return String.valueOf(FacesContext.getCurrentInstance().getExternalContext().
                getRequestMap().get("javax.servlet.error.status_code"));
    }

    public String getMessage(){
        return (String)FacesContext.getCurrentInstance().getExternalContext().
                getRequestMap().get("javax.servlet.error.message");
    }

    public String getExceptionType(){
        return FacesContext.getCurrentInstance().getExternalContext().
                getRequestMap().get("javax.servlet.error.exception_type").toString();
    }

    public String getException(){
        return (FacesContext.getCurrentInstance().getExternalContext().
                getRequestMap().get("javax.servlet.error.exception")).toString();
    }

    public String getRequestURI(){
        return (String)FacesContext.getCurrentInstance().getExternalContext().
                getRequestMap().get("javax.servlet.error.request_uri");
    }

}
