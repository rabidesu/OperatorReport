package com.tsystems.jschool.report.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.tsystems.jschool.report.dao.api.TariffDao;
import com.tsystems.jschool.report.entities.Tariff;
import com.tsystems.jschool.report.exceptions.ReportDaoException;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TariffDaoImpl implements TariffDao {

    private final static Logger logger = Logger.getLogger(TariffDaoImpl.class);

    private Client client = Client.create();

    public List<Tariff> getAllTariffs(){

        WebResource webResource = client.resource("http://localhost:8180/pages/report/tariffs");
        ObjectMapper mapper = new ObjectMapper();
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new ReportDaoException("Error! HTTP error code: " + response.getStatus());
        }
        String result = response.getEntity(String.class);
        List<Tariff> tariffs = new ArrayList<>();
        try {
            tariffs = mapper.readValue(result, new TypeReference<List<Tariff>>(){});
        } catch (Exception e) {
            throw new ReportDaoException("Failed to convert object");
        }
        return tariffs;
    }

}
