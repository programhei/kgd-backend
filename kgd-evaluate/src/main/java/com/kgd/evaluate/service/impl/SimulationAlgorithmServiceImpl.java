package com.kgd.evaluate.service.impl;

import com.kgd.common.utils.DateUtils;
import com.kgd.evaluate.domain.SimulationAlgorithm;
import com.kgd.evaluate.mapper.SimulationAlgorithmMapper;
import com.kgd.evaluate.service.ISimulationAlgorithmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SimulationAlgorithmServiceImpl implements ISimulationAlgorithmService {

    @Resource
    private SimulationAlgorithmMapper simulationAlgorithmMapper;

    @Override
    public SimulationAlgorithm selectSimulationAlgorithmById(Long id) {
        return simulationAlgorithmMapper.selectSimulationAlgorithmById(id);
    }

    @Override
    public List<SimulationAlgorithm> selectSimulationAlgorithmList(SimulationAlgorithm simulationAlgorithm) {
        return simulationAlgorithmMapper.selectSimulationAlgorithmList(simulationAlgorithm);
    }

    @Override
    public int insertSimulationAlgorithm(SimulationAlgorithm simulationAlgorithm) {
        simulationAlgorithm.setCreateTime(DateUtils.getNowDate());
        return simulationAlgorithmMapper.insertSimulationAlgorithm(simulationAlgorithm);
    }

    @Override
    public int updateSimulationAlgorithm(SimulationAlgorithm simulationAlgorithm) {
        simulationAlgorithm.setUpdateTime(DateUtils.getNowDate());
        return simulationAlgorithmMapper.updateSimulationAlgorithm(simulationAlgorithm);
    }

    @Override
    public int deleteSimulationAlgorithmByIds(Long[] ids) {
        return simulationAlgorithmMapper.deleteSimulationAlgorithmByIds(ids);
    }
}

