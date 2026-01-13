package com.kgd.evaluate.service.impl;

import java.util.Date;
import java.util.List;

import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import com.kgd.evaluate.domain.ScenarioConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.evaluate.mapper.SoftwareQualityMapper;
import com.kgd.evaluate.domain.SoftwareQuality;
import com.kgd.evaluate.service.ISoftwareQualityService;

/**
 * 软件质量Service业务层处理
 *
 * @author kgd
 * @date 2025-11-05
 */
@Service
public class SoftwareQualityServiceImpl implements ISoftwareQualityService {
    @Autowired
    private SoftwareQualityMapper softwareQualityMapper;

    /**
     * 查询软件质量
     *
     * @param id 软件质量主键
     * @return 软件质量
     */
    @Override
    public SoftwareQuality selectSoftwareQualityById(Long id) {
        return softwareQualityMapper.selectSoftwareQualityById(id);
    }

    /**
     * 查询软件质量列表
     *
     * @param softwareQuality 软件质量
     * @return 软件质量
     */
    @Override
    public List<SoftwareQuality> selectSoftwareQualityList(SoftwareQuality softwareQuality) {
        return softwareQualityMapper.selectSoftwareQualityList(softwareQuality);
    }

    /**
     * 新增软件质量
     *
     * @param softwareQuality 软件质量
     * @return 结果
     */
    @Override
    public int insertSoftwareQuality(SoftwareQuality softwareQuality) {
        softwareQuality.setCreateTime(DateUtils.getNowDate());
        softwareQuality.setCreateBy(SecurityUtils.getUsername());
        return softwareQualityMapper.insertSoftwareQuality(softwareQuality);
    }

    /**
     * 新增软件质量
     *
     * @param list 软件质量
     */
    @Override
    public void insertSoftwareQualityBatch(List<SoftwareQuality> list) {
        softwareQualityMapper.insertSoftwareQualityBatch(list);
    }

    /**
     * 修改软件质量
     *
     * @param softwareQuality 软件质量
     * @return 结果
     */
    @Override
    public int updateSoftwareQuality(SoftwareQuality softwareQuality) {
        softwareQuality.setUpdateTime(DateUtils.getNowDate());
        softwareQuality.setUpdateBy(SecurityUtils.getUsername());
        return softwareQualityMapper.updateSoftwareQuality(softwareQuality);
    }

    /**
     * 批量删除软件质量
     *
     * @param ids 需要删除的软件质量主键
     * @return 结果
     */
    @Override
    public int deleteSoftwareQualityByIds(Long[] ids) {
        return softwareQualityMapper.deleteSoftwareQualityByIds(ids);
    }

    /**
     * 删除软件质量信息
     *
     * @param id 软件质量主键
     * @return 结果
     */
    @Override
    public int deleteSoftwareQualityById(Long id) {
        return softwareQualityMapper.deleteSoftwareQualityById(id);
    }


    /**
     * 批量删除软件质量
     *
     * @param dataId 需要删除的数据ID
     */
    @Override
    public void deleteSoftwareQualityByDataId(String dataId) {
        Date date = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, dataId.replace("T", " ").substring(0, 19));
        softwareQualityMapper.deleteSoftwareQualityByDataId(date);
    }

    /**
     * 删除所有软件质量
     */
    @Override
    public void deleteAllSoftwareQuality() {
        softwareQualityMapper.deleteAllSoftwareQuality();
    }
}
