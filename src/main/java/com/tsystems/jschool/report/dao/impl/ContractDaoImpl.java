package com.tsystems.jschool.report.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.tsystems.jschool.report.dao.api.ContractDao;
import com.tsystems.jschool.report.entities.Contract;
import com.tsystems.jschool.report.exceptions.ReportDaoException;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
public class ContractDaoImpl implements ContractDao {

    private final static Logger logger = Logger.getLogger(ContractDaoImpl.class);

    private Client client = Client.create();

    public List<Contract> getUserListByTariffId(int id){

        String url = String.format("http://localhost:8180/pages/report/contracts/%d", id);

        WebResource webResource = client.resource(url);
        ObjectMapper mapper = new ObjectMapper();
        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new ReportDaoException("Error! HTTP error code: " + response.getStatus());
        }

        String output = response.getEntity(String.class);
        logger.debug(output);

        List<Contract> contracts;
        try {
            contracts = mapper.readValue(output, new TypeReference<List<Contract>>(){});
        } catch (Exception e) {
            throw new ReportDaoException("Failed to convert object!");
        }

        return contracts;

    }
}
