package com.kgd.evaluate.service;


import com.kgd.evaluate.domain.WeightMangeData;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface IWeightManageService {


    /**
     * 查询权值
     *
     * @param weightMangeData 权值数据
     * @return 权值数据结果
     */
    public Map<String, Object> selectWeightTree(WeightMangeData weightMangeData);
    public Map<String, Object> selectWeightTree(Integer type, Integer groupId);

    /**
     *  保存权值
     *
     * @param weightMangeData 请求参数
     */
    public void saveWeight(WeightMangeData weightMangeData) throws Exception;

    /**
     * 查询权值
     *
     * @param weightMangeData 权值数据
     * @return 权值数据结果
     */
    public Map<String, Object> selectTotalScore(WeightMangeData weightMangeData);

    List<WeightMangeData>countAvgWeightWithType();

    List<Long> selectUserIds();
}
