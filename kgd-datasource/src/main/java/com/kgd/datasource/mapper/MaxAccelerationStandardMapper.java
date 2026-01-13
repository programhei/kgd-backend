package com.kgd.datasource.mapper;

import com.kgd.datasource.domain.vo.MaxAccelerationStandardVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 最大加速度（标准/测试）Mapper
 */
public interface MaxAccelerationStandardMapper {

    MaxAccelerationStandardVo selectMaxAccelerationStandardById(Long id);

    List<MaxAccelerationStandardVo> selectMaxAccelerationStandardAll();

    List<MaxAccelerationStandardVo> selectMaxAccelerationStandardList(MaxAccelerationStandardVo query);

    int insertMaxAccelerationStandard(MaxAccelerationStandardVo vo);

    int insertMaxAccelerationStandardBatch(@Param("list") List<MaxAccelerationStandardVo> list);

    int updateMaxAccelerationStandard(MaxAccelerationStandardVo vo);

    int deleteMaxAccelerationStandardById(Long id);

    int deleteMaxAccelerationStandardByIds(Long[] ids);

    /**
     * 按算法类型/标准或测试删除
     * @param evaluateType 0最大速度 1最大加速度
     * @param speedOrTest 0标准 1测试
     */
    int deleteMaxAccelerationStandardByType(@Param("evaluateType") Integer evaluateType,
                                            @Param("speedOrTest") Integer speedOrTest);
}

