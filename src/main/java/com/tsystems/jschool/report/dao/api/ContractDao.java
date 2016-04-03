package com.tsystems.jschool.report.dao.api;

import com.tsystems.jschool.report.entities.Contract;

import java.util.List;

public interface ContractDao {

    List<Contract> getUserListByTariffId(int id);
}
