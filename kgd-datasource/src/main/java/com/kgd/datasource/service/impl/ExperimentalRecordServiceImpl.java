package com.kgd.datasource.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import com.kgd.common.utils.csv.CsvUtil;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.datasource.domain.ExperimentalIdCorrespondence;
import com.kgd.datasource.domain.WeightRecord;
import com.kgd.datasource.domain.vo.CsvVo;
import com.kgd.datasource.mapper.WeightMapper;
import com.kgd.datasource.service.IExperimentalIdCorrespondenceService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.kgd.datasource.mapper.ExperimentalRecordMapper;
import com.kgd.datasource.domain.ExperimentalRecord;
import com.kgd.datasource.service.IExperimentalRecordService;

import javax.annotation.Resource;

/**
 * 实验记录Service业务层处理
 *
 * @author kgd
 * @date 2025-11-06
 */
@Service
public class ExperimentalRecordServiceImpl implements IExperimentalRecordService {
    @Resource
    private ExperimentalRecordMapper experimentalRecordMapper;

    @Resource
    private IExperimentalIdCorrespondenceService experimentalIdCorrespondenceService;

    @Resource
    private WeightMapper weightMapper;

    /**
     * 查询实验记录
     *
     * @param id 实验记录主键
     * @return 实验记录
     */
    @Override
    public ExperimentalRecord selectExperimentalRecordById(Long id) {
        return experimentalRecordMapper.selectExperimentalRecordById(id);
    }

    /**
     * 查询实验记录列表
     *
     * @param experimentalRecord 实验记录
     * @return 实验记录
     */
    @Override
    public List<ExperimentalRecord> selectExperimentalRecordList(ExperimentalRecord experimentalRecord) {
        return experimentalRecordMapper.selectExperimentalRecordList(experimentalRecord);
    }

    /**
     * 新增实验记录
     *
     * @param experimentalRecord 实验记录
     * @return 结果
     */
    @Override
    public int insertExperimentalRecord(ExperimentalRecord experimentalRecord) {
        experimentalRecord.setCreateTime(DateUtils.getNowDate());
        experimentalRecord.setCreateBy(SecurityUtils.getUsername());
        return experimentalRecordMapper.insertExperimentalRecord(experimentalRecord);
    }

    @Override
    public void importData(String filePath) throws Exception {
        // 1. 读 Excel
        // 处理中文路径问题：使用File对象代替Paths.get()以更好地支持中文路径
        File file = new File(filePath);
        // 获取维度数据
        ExperimentalRecord experimentalRecord = new ExperimentalRecord();
        experimentalRecord.setExperimentalId(filePath);
        // 0-第一维度 1-第二维度 2-变量值 3-实验次数
        String[] variables = getVariables(experimentalRecord);
        String dim1 = variables[0], dim2 = variables[1], val = variables[2], count = variables[3];
        ExcelUtil<ExperimentalRecord> excelUtil = new ExcelUtil<>(ExperimentalRecord.class);
        List<ExperimentalRecord> srcList = excelUtil.importExcel(new FileInputStream(file), 0);
        if (CollectionUtils.isEmpty(srcList)) {
            throw new Exception("上传的实验记录Excel文件为空");
        }
        // 当前登陆用户名
        String user = SecurityUtils.getUsername();
        // 创建时间
        Date now = DateUtils.getNowDate();

        // 2. 缓存当前最大实验次数
        Map<String, Integer> maxCountCache = new HashMap<>();

        List<ExperimentalRecord> toInsert = new ArrayList<>(srcList.size());

        for (ExperimentalRecord raw : srcList) {
            // 2.1 查维度信息
            ExperimentalIdCorrespondence dim = getDim(dim1);
            ExperimentalIdCorrespondence var = getVar(dim1, dim2);
            String dimension = dim.getDimension();
            String variable = var.getDimension();

            // 2.2 当前组合的最大序号
            String key = dimension + "#" + variable + "#" + val;

            /* 2. 判断库里是否已存在该组合 */
            int existCount = experimentalRecordMapper.countByDimensionVariableValue(dimension, variable, val);
            if (existCount == 0) {
                // 2.1 不存在，保存 Excel 原样，直接入库
                raw.setDimension(dimension);
                raw.setVariable(variable);
                raw.setVariableValue(val);
                raw.setCreateTime(now);
                raw.setCreateBy(user);
                toInsert.add(raw);
                continue;
            }
            /* 3. 存在 → 才做累加 */
            int maxCount = maxCountCache.computeIfAbsent(key, k ->
                    experimentalRecordMapper.selectMaxExpSerial(dimension, variable, val)
            );

            /* 4. 累加 */
            int nextCount = !ObjectUtils.isEmpty(maxCountCache.get(key + "#count")) ? maxCountCache.get(key + "#count") + 1 : maxCount + 1;
            // 2.3 构造新记录
            ExperimentalRecord e = new ExperimentalRecord();
            e.setExperimentalId(buildExpId(dim1, dim2, val, nextCount));
            e.setVariable(variable);
            e.setVariableValue(val);
            e.setDimension(dimension);
            // 从最大+1开始
            e.setExperimentalCount(String.valueOf(nextCount));
            e.setScore(raw.getScore());
            e.setCreateTime(now);
            e.setCreateBy(user);
            // 其余字段若有需要继续 copy
            toInsert.add(e);
            /* 6. 更新缓存，保证同批次不重复 */
            maxCountCache.put(key + "#count", nextCount);
        }

        // 3. 批量插入
        if (!toInsert.isEmpty()) {
            experimentalRecordMapper.insertExperimentalRecordBatch(toInsert);
        }
    }

    @Override
    public void importAfSimData(String filePath) throws Exception {
        // 1. 读 Excel
        // 处理中文路径问题：使用File对象代替Paths.get()以更好地支持中文路径
        File file = new File(filePath);
        // 获取维度数据
        ExperimentalRecord experimentalRecord = new ExperimentalRecord();
        experimentalRecord.setExperimentalId(filePath);
        CsvUtil<CsvVo> csvUtil = new CsvUtil<CsvVo>(CsvVo.class);

        ExcelUtil<WeightRecord> excelUtil = new ExcelUtil<>(WeightRecord.class);
        List<WeightRecord> srcList = excelUtil.importExcel(new FileInputStream(file), 0);

        if (CollectionUtils.isEmpty(srcList)) {
            throw new Exception("上传的实验记录csv文件为空");
        }
        String userId=null;
        int type=0;
        String typestr = getType(file);
        if (typestr.contains("客观")){
            userId = typestr.replaceAll("\\D+", "");
            type=1;
        }else {
            userId=typestr;
        }
//        Long userId = Long.valueOf(getUserId(file));
//        for (WeightRecord record : srcList) {
//            record.setGroupId(userId);
//        }

        for (WeightRecord  weightRecord : srcList) {
            weightRecord.setType(type);
            weightRecord.setGroupId(Long.valueOf(userId));
//            System.out.println(WeightRecord);
           List<WeightRecord>  list =  weightMapper.selectWeightRecordList(weightRecord);
           if(list.size() >0){
               weightRecord.setCreateTime(DateUtils.getNowDate());
               weightRecord.setCreateBy(SecurityUtils.getUsername());
               weightMapper.updateWeightRecord(weightRecord);
           }else{
               weightRecord.setUpdateTime(DateUtils.getNowDate());
               weightRecord.setUpdateBy(SecurityUtils.getUsername());
               weightMapper.insertWeightRecord(weightRecord);
           }
        }

    }

    private String getUserId(File file) throws Exception {
        String fileName = file.getName();
        // 分割并提取数字
        String nameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
        String[] parts = nameWithoutExt.split("_");
        if (parts.length > 1) {
           return parts[1];
        } else {
            throw new Exception("上传的实验记录文件名空");
        }
    }

    private String getType(File file) {
        String fileName = file.getName();
        // 分割并提取数字
        String nameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
        // 提取下划线后的部分
       return nameWithoutExt.substring(nameWithoutExt.indexOf('_') + 1);
    }

    /**
     * 修改实验记录
     *
     * @param experimentalRecord 实验记录
     * @return 结果
     */
    @Override
    public int updateExperimentalRecord(ExperimentalRecord experimentalRecord) {
        experimentalRecord.setUpdateTime(DateUtils.getNowDate());
        experimentalRecord.setUpdateBy(SecurityUtils.getUsername());
        return experimentalRecordMapper.updateExperimentalRecord(experimentalRecord);
    }

    /**
     * 批量删除实验记录
     *
     * @param ids 需要删除的实验记录主键
     * @return 结果
     */
    @Override
    public int deleteExperimentalRecordByIds(Long[] ids) {
        return experimentalRecordMapper.deleteExperimentalRecordByIds(ids);
    }

    /**
     * 删除实验记录信息
     *
     * @param id 实验记录主键
     * @return 结果
     */
    @Override
    public int deleteExperimentalRecordById(Long id) {
        return experimentalRecordMapper.deleteExperimentalRecordById(id);
    }


    /**
     * 清空实验记录
     *
     * @return 结果
     */

    @Override
    public int deleteExperimentalRecord() {
        return experimentalRecordMapper.deleteExperimentalRecord();
    }

    /* ---------- 私有工具 ---------- */
    private ExperimentalIdCorrespondence getDim(String dim1) throws Exception {
        ExperimentalIdCorrespondence e = new ExperimentalIdCorrespondence();
        e.setDigitalId(dim1);
        ExperimentalIdCorrespondence res = experimentalIdCorrespondenceService.selectExperimentalIdCorrespondence(e);
        if (res == null) throw new Exception("未找到实验编号 " + dim1 + " 对应的维度信息");
        return res;
    }

    private ExperimentalIdCorrespondence getVar(String dim1, String dim2) throws Exception {
        ExperimentalIdCorrespondence e = new ExperimentalIdCorrespondence();
        e.setDigitalId(dim2);
        e.setParentId(dim1);
        ExperimentalIdCorrespondence res = experimentalIdCorrespondenceService.selectExperimentalIdCorrespondence(e);
        if (res == null) throw new Exception("未找到实验编号 " + dim1 + "-" + dim2 + " 对应的变量信息");
        return res;
    }

    private static String[] getVariables(ExperimentalRecord item) {
        String[] firstArr = item.getExperimentalId().split("_");
        if (firstArr.length > 0 && !firstArr[1].isEmpty()) {
            String[] vs = item.getExperimentalId().split("_")[1].split("-");
//            if (vs.length < 4) {
//                throw new IllegalArgumentException(
//                        "实验ID格式必须为：{第一维度}-{第二维度}-{变量值}-{实验次数}，例如 1-11-1-01");
//            }
            return vs;
        }
        return firstArr;
    }

    /**
     * 从 experimental_id 里取出最后一段序号
     */
    private static int extractNo(String expId) {
        // 1-11-1-06  ->  6
        return Integer.parseInt(expId.substring(expId.lastIndexOf("-") + 2));
    }

    /**
     * 构造新的 experimental_id
     */
    private static String buildExpId(String dim1, String dim2, String val, int no) {
        // 不足两位补 0，保持 01、02...11 格式
        return String.format("%s-%s-%s-%02d", dim1, dim2, val, no);
    }

}
