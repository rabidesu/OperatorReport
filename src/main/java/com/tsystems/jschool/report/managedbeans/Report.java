package com.tsystems.jschool.report.managedbeans;

import com.tsystems.jschool.report.entities.Tariff;
import com.tsystems.jschool.report.services.api.TariffService;
import com.tsystems.jschool.report.services.api.ContractService;
import org.apache.log4j.Logger;
import org.omnifaces.util.Faces;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.io.File;
import java.io.IOException;
import java.util.List;


@ManagedBean(name = "report")
public class Report {

    private final static Logger logger = Logger.getLogger(Report.class);

    private int selectedTariffId;

    @EJB
    private TariffService tariffService;

    @EJB
    private ContractService contractService;

    public List<Tariff> getAllTariffs(){
        return tariffService.getAllTariffs();
    }


    public int getSelectedTariffId() {
        return selectedTariffId;
    }

    public void setSelectedTariffId(int selectedTariffId) {
        this.selectedTariffId = selectedTariffId;
    }

    public void generateReport() throws IOException {
        String pathToFile = contractService.generateReportByTariffId(selectedTariffId);
        File file = new File(pathToFile);
        Faces.sendFile(file, true);
    }
}
