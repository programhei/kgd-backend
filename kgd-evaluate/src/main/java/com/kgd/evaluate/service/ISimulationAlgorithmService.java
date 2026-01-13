package com.kgd.evaluate.service;

import com.kgd.evaluate.domain.SimulationAlgorithm;

import java.util.List;

public interface ISimulationAlgorithmService {
    SimulationAlgorithm selectSimulationAlgorithmById(Long id);

    List<SimulationAlgorithm> selectSimulationAlgorithmList(SimulationAlgorithm simulationAlgorithm);

    int insertSimulationAlgorithm(SimulationAlgorithm simulationAlgorithm);

    int updateSimulationAlgorithm(SimulationAlgorithm simulationAlgorithm);

    int deleteSimulationAlgorithmByIds(Long[] ids);
}

