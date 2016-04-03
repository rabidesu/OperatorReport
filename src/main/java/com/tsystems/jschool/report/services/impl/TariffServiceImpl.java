package com.tsystems.jschool.report.services.impl;

import com.tsystems.jschool.report.dao.api.TariffDao;
import com.tsystems.jschool.report.entities.Tariff;
import com.tsystems.jschool.report.exceptions.ReportDaoException;
import com.tsystems.jschool.report.exceptions.ReportServiceException;
import com.tsystems.jschool.report.services.api.TariffService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class TariffServiceImpl implements TariffService {

    @EJB
    private TariffDao tariffDao;

    public List<Tariff> getAllTariffs() {
        try {
            return tariffDao.getAllTariffs();
        } catch (ReportDaoException e){
            throw new ReportServiceException("Error downloading tariffs", e);
        }
    }

}
