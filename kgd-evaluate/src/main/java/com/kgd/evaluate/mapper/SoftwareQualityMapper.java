package com.kgd.evaluate.mapper;

import java.util.Date;
import java.util.List;

import com.kgd.evaluate.domain.ScenarioConfiguration;
import com.kgd.evaluate.domain.SoftwareQuality;
import org.apache.ibatis.annotations.Param;

/**
 * 软件质量Mapper接口
 *
 * @author kgd
 * @date 2025-11-05
 */
public interface SoftwareQualityMapper {
    /**
     * 查询软件质量
     *
     * @param id 软件质量主键
     * @return 软件质量
     */
    public SoftwareQuality selectSoftwareQualityById(Long id);

    /**
     * 查询软件质量列表
     *
     * @param softwareQuality 软件质量
     * @return 软件质量集合
     */
    public List<SoftwareQuality> selectSoftwareQualityList(SoftwareQuality softwareQuality);

    /**
     * 新增软件质量
     *
     * @param softwareQuality 软件质量
     * @return 结果
     */
    public int insertSoftwareQuality(SoftwareQuality softwareQuality);

    /**
     * 新增软件质量
     *
     * @param list 软件质量
     */
    public void insertSoftwareQualityBatch(@Param("list") List<SoftwareQuality> list);


    /**
     * 修改软件质量
     *
     * @param softwareQuality 软件质量
     * @return 结果
     */
    public int updateSoftwareQuality(SoftwareQuality softwareQuality);

    /**
     * 删除软件质量
     *
     * @param id 软件质量主键
     * @return 结果
     */
    public int deleteSoftwareQualityById(Long id);

    /**
     * 批量删除软件质量
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSoftwareQualityByIds(Long[] ids);

    /**
     * 批量删除软件质量
     *
     * @param dataId 需要删除的数据ID
     */
    public void deleteSoftwareQualityByDataId(Date dataId);

    /**
     * 删除所有软件质量
     */
    public void deleteAllSoftwareQuality();
}
