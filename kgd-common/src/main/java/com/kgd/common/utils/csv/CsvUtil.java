package com.kgd.common.utils.csv;

import com.kgd.common.annotation.Excel;
import com.kgd.common.core.domain.AjaxResult;
import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.StringUtils;
import com.kgd.common.utils.reflect.ReflectUtils;
import org.apache.commons.csv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CSV 导入导出工具类（Ruoyi 风格）
 *
 * @author kgd
 */
public class CsvUtil<T> {

    private static final Logger log = LoggerFactory.getLogger(CsvUtil.class);

    /* ---------- 成员变量 ---------- */
    private final Class<T> clazz;
    private List<T> list;               // 导出源数据
    private String[] includeFields;
    private String[] excludeFields;
    private final Map<String, String> headerMap; // CSV 列头 → 字段名

    private String defaultValue(String fieldName) {
        switch (fieldName) {
            case "hitCount":
            case "remainingAmmo":
            case "totalAmmo":
            case "missCount":
            case "evadeCount":
            case "destroyStatus":
                return "0";
            case "remainingFuel":
            case "totalFuel":
            case "score":
                return "0.0";
            case "platformId":
            case "role":
            default:
                return "";   // 字符串空
        }
    }

    /* ---------- 构造 ---------- */
    public CsvUtil(Class<T> clazz) {
        this.clazz = clazz;
        this.headerMap = new LinkedHashMap<>();
        createHeaderMap();
    }

    /* ==================== 1. 导入 ==================== */

    /**
     * 导入 CSV（默认 UTF-8，第一行是表头）
     */
    public List<T> importCsv(InputStream is,int titleNum) throws Exception {
        return importCsv(is, StandardCharsets.UTF_8,titleNum);
    }

    public List<T> importCsv(InputStream is, Charset charset, int titleNum) throws Exception {
        /* ---------- 1. 手工丢弃前 titleNum-1 行 ---------- */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
        for (int i = 0; i < titleNum; i++) {
            reader.readLine();          // 跳过任意行（含表头之前的空行、说明文字等）
        }

        /* ---------- 2. 从剩余流里正规解析 ---------- */
        CSVFormat format = CSVFormat.DEFAULT
                .builder()
                .setHeader()             // 把“当前第一行”当表头
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .setAllowMissingColumnNames(true)   // 允许缺失列
                .build();

        try (CSVParser parser = new CSVParser(reader, format)) {
            List<T> result = new ArrayList<>();
            Map<String, String> headerToField = new HashMap<>(headerMap);

            for (CSVRecord record : parser) {
                T bean = clazz.getDeclaredConstructor().newInstance();
                for (Map.Entry<String, String> entry : headerToField.entrySet()) {
                    String csvHeader = entry.getKey();
                    String fieldName  = entry.getValue();
                    String cellVal   = record.get(csvHeader);
                    Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object val = convertValue(field, cellVal);
                    ReflectUtils.invokeSetter(bean, fieldName, val);
                }
                result.add(bean);
            }
            return result;
        }
    }


    /**
     * 读取 csv 文本文件，返回 List<String[]>，不依赖任何第三方包
     *
     * @param is  文件流
     * @param charset   编码，例如 Charset.forName("GBK")
     * @param skipLines 跳过前几行（表头、说明文字等）
     * @return 每行是一个 String[]，空列用 null 占位
     */
    public List<String[]> textCsvReader(InputStream is, Charset charset, int skipLines) throws IOException {
        List<String[]> result = new ArrayList<>();
        // 自动关闭流
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, charset))) {
            // 1. 丢弃前 skipLines 行
            for (int i = 0; i < skipLines; i++) {
                br.readLine();
            }
            String line;
            while ((line = br.readLine()) != null) {
                // 2. 手动 split，-1 是为了保留末尾空串
                String[] cells = line.split(",", -1);
                for (int i = 0; i < cells.length; i++) {
                    cells[i] = cells[i].trim();          // 去空格
                    if (cells[i].isEmpty()) {
                        cells[i] = null;                 // 空列补 null
                    }
                    // 3. 去引号（可选）
                    if (cells[i] != null && cells[i].length() >= 2
                            && cells[i].charAt(0) == '"' && cells[i].charAt(cells[i].length() - 1) == '"') {
                        cells[i] = cells[i].substring(1, cells[i].length() - 1);
                    }
                }
                result.add(cells);
            }
        }
        return result;
    }




    /* ==================== 2. 导出 ==================== */

    /**
     * 导出 CSV 到浏览器
     */
    public void exportCsv(HttpServletResponse response, List<T> list, String fileName) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(fileName + ".csv", "UTF-8"));

        // 1. 新的 builder 风格
        CSVFormat format = CSVFormat.DEFAULT
                .builder()
                .setHeader(headerMap.keySet().toArray(new String[0]))
                .build();

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
             CSVPrinter printer = new CSVPrinter(writer, format)) {

            for (T bean : list) {
                List<Object> row = new ArrayList<>();
                for (String header : headerMap.keySet()) {
                    String fieldName = headerMap.get(header);
                    Object val = ReflectUtils.invokeGetter(bean, fieldName);
                    row.add(formatValue(val));
                }
                printer.printRecord(row);
            }
            printer.flush();
        }
    }


    /* ==================== 3. 私有方法 ==================== */

    /**
     * 建立 表头→字段名 映射（按 @Excel name 属性）
     */
    private void createHeaderMap() {
        List<Field> fields = getFields();
        for (Field f : fields) {
            Excel excel = f.getAnnotation(Excel.class);
            if (excel != null) {
                headerMap.put(excel.name(), f.getName());
            }
        }
    }

    /**
     * 取需要处理的字段（含 include/exclude 逻辑）
     */
    private List<Field> getFields() {
        List<Field> all = new ArrayList<>();
        all.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        all.addAll(Arrays.asList(clazz.getDeclaredFields()));

        if (includeFields != null && includeFields.length > 0) {
            return all.stream()
                    .filter(f -> Arrays.asList(includeFields).contains(f.getName()))
                    .collect(Collectors.toList());
        }
        if (excludeFields != null && excludeFields.length > 0) {
            return all.stream()
                    .filter(f -> !Arrays.asList(excludeFields).contains(f.getName()))
                    .collect(Collectors.toList());
        }
        return all;
    }

    /**
     * 字符串→目标类型（仅常用类型）
     */
    private Object convertValue(Field field, String cellVal) {
        Class<?> type = field.getType();
        if (String.class == type) return cellVal;
        if (Integer.TYPE == type || Integer.class == type) return Integer.parseInt(cellVal);
        if (Long.TYPE == type || Long.class == type) return Long.parseLong(cellVal);
        if (Double.TYPE == type || Double.class == type) return Double.parseDouble(cellVal);
        if (BigDecimal.class == type) return new BigDecimal(cellVal);
        if (Date.class == type) return DateUtils.parseDate(cellVal);
        if (LocalDate.class == type) return LocalDate.parse(cellVal);
        if (LocalDateTime.class == type) return LocalDateTime.parse(cellVal);
        return cellVal;
    }

    /**
     * 任意值→字符串（导出）
     */
    private String formatValue(Object val) {
        return val == null ? ""
                : val instanceof Date ? DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", (Date) val)
                : val.toString();
    }


    /* ---------- 链式筛选字段 ---------- */
    public CsvUtil<T> showColumn(String... fields) {
        this.includeFields = fields;
        return this;
    }

    public CsvUtil<T> hideColumn(String... fields) {
        this.excludeFields = fields;
        return this;
    }
}
