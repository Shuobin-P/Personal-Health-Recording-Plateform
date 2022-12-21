package com.google.personalhealthrecordingplateform.service;

import com.google.personalhealthrecordingplateform.enumeration.ExcelTypeEnum;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/13 15:30
 */
public interface PoiExcelService {
    /**
     * 读excel文件
     *
     * @param excelIn
     * @param dataClazz
     * @param excelTypeEnum
     * @param <T>
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    <T> List<T> read(InputStream excelIn, Class<T> dataClazz, ExcelTypeEnum excelTypeEnum) throws IOException, IllegalAccessException, InstantiationException;

    Map<String, PictureData> getExcelPictures(Sheet sheet);

    XSSFWorkbook writeXSSFWorkbook(String sheetName, List<?> dataList, Class<?> clazz);
}
