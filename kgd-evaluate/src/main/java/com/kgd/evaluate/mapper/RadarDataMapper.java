package com.kgd.evaluate.mapper;

import java.util.List;

import com.kgd.evaluate.domain.RadarData;
import org.apache.ibatis.annotations.Param;

/**
 * 雷达数据Mapper接口
 *
 * @author ruoyi
 * @date 2025-10-31
 */
public interface RadarDataMapper {
    /**
     * 查询雷达数据
     *
     * @param id 雷达数据主键
     * @return 雷达数据
     */
    public RadarData selectRadarDataById(Long id);

    /**
     * 查询雷达数据列表
     *
     * @param radarData 雷达数据
     * @return 雷达数据集合
     */
    public List<RadarData> selectRadarDataList(RadarData radarData);

    /**
     * 新增雷达数据
     *
     * @param radarData 雷达数据
     * @return 结果
     */
    public int insertRadarData(RadarData radarData);

    /**
     * 新增雷达数据
     *
     * @param list 雷达数据
     */
    void insertRadarDataBatch(@Param("list") List<RadarData> list);


    /**
     * 修改雷达数据
     *
     * @param radarData 雷达数据
     * @return 结果
     */
    public int updateRadarData(RadarData radarData);

    /**
     * 删除雷达数据
     *
     * @param id 雷达数据主键
     * @return 结果
     */
    public int deleteRadarDataById(Long id);

    /**
     * 批量删除雷达数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRadarDataByIds(Long[] ids);

    /**
     * 删除雷达数据
     *
     * @param dataId 雷达数据ID
     */
    public void deleteRadarDataByDataId(String dataId);

    /**
     * 删除雷达数据
     *
     * @param radarData 雷达数据ID
     */
    public void deleteRadarData(RadarData radarData);
}
