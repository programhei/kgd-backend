package com.kgd.evaluate.mapper;

import com.kgd.evaluate.domain.SimulationAlgorithm;

import java.util.List;

public interface SimulationAlgorithmMapper {
    SimulationAlgorithm selectSimulationAlgorithmById(Long id);

    List<SimulationAlgorithm> selectSimulationAlgorithmList(SimulationAlgorithm simulationAlgorithm);

    int insertSimulationAlgorithm(SimulationAlgorithm simulationAlgorithm);

    int updateSimulationAlgorithm(SimulationAlgorithm simulationAlgorithm);

    int deleteSimulationAlgorithmById(Long id);

    int deleteSimulationAlgorithmByIds(Long[] ids);
}

