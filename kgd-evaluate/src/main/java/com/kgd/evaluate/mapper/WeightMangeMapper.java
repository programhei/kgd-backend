package com.kgd.evaluate.mapper;


import com.kgd.evaluate.domain.WeightMangeData;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 权值记录Mapper接口
 *
 * @author kgd
 * @date 2025-12-02
 */
public interface WeightMangeMapper {

    /**
     * 查询权值
     *
     * @param weightMangeData 权值
     * @return 权值集合
     */
    public List<WeightMangeData> selectWeightTree(WeightMangeData weightMangeData);
    /**
     * 查询权值全部
     *
     * @param weightMangeData 权值
     * @return 权值集合
     */
    public List<WeightMangeData> selectWeightTreeAll(WeightMangeData weightMangeData);

    /**
     * 更新权值
     *
     * @param weightMangeData 权值
     * @return 结果
     */
    public int updateWeightData(WeightMangeData weightMangeData);


    /**
     * 更新权值
     *
     * @param weightMangeData 权值
     * @return 结果
     */
    public int updateWeightScore(WeightMangeData weightMangeData);

    public List<WeightMangeData> tree(@Param("parentId") Long parentId,
                                      @Param("groupId") Long groupId,
                                      @Param("type") Long type);

    /**
     *  主观权值
     * @return
     */
    public BigDecimal countAvgWeightWithType( @Param("type") Integer type);


    List<Long>  selectUserIds();
}
