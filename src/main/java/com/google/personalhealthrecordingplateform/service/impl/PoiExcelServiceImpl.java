package com.google.personalhealthrecordingplateform.service.impl;

import com.google.personalhealthrecordingplateform.annotation.ExcelImageData;
import com.google.personalhealthrecordingplateform.annotation.ExcelProperty;
import com.google.personalhealthrecordingplateform.enumeration.ExcelTypeEnum;
import com.google.personalhealthrecordingplateform.service.PoiExcelService;
import com.google.personalhealthrecordingplateform.util.QiniuUtils;
import com.google.personalhealthrecordingplateform.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/13 15:46
 */
@Slf4j
@Service
public class PoiExcelServiceImpl implements PoiExcelService {
    private QiniuUtils qiniuUtils;

    @Autowired
    public PoiExcelServiceImpl(QiniuUtils qiniuUtils) {
        this.qiniuUtils = qiniuUtils;
    }

    @Override
    public <T> List<T> read(InputStream excelIn, Class<T> dataClazz, ExcelTypeEnum excelTypeEnum) throws IOException, IllegalAccessException, InstantiationException {
        Workbook workbook = null;
        try {
            // ----- 获取工作簿，默认读取第一个sheet -----
            if (excelTypeEnum.equals(ExcelTypeEnum.XLS)) {
                workbook = new HSSFWorkbook(excelIn);
            } else if (excelTypeEnum.equals(ExcelTypeEnum.XLSX)) {
                workbook = new XSSFWorkbook(excelIn);
            } else {
                throw new RuntimeException("不支持的Excel类型");
            }
            Sheet sheet = workbook.getSheetAt(0);

            // ----- 上传图片，获取图片链接 -----
            Map<String, PictureData> pictureDataMap = this.getExcelPictures(sheet);
            Map<String, String> imageInfoMap = new HashMap<>(pictureDataMap.size());
            for (Map.Entry<String, PictureData> pictureDataEntry : pictureDataMap.entrySet()) {
                String key = pictureDataEntry.getKey();
                PictureData pictureData = pictureDataEntry.getValue();
                String imageUrl = qiniuUtils.upload(pictureData.getData(), StringUtils.getRandomString(10) + "." + pictureData.suggestFileExtension());
                imageInfoMap.put(key, imageUrl);
            }
            log.info("图片上传成功,上传数量：" + pictureDataMap.size());
            // ----- 获取Excel数据 -----
            List<T> dataList = new ArrayList<>();
            //dataClazz 这里是指FoodExcelDTO
            Field[] declaredFields = dataClazz.getDeclaredFields();
            //有效数据总行数，不包括第一行的表头
            int totalRowNum = sheet.getLastRowNum();
            log.info("Sheet有效数据总行数：" + totalRowNum);
            if (totalRowNum <= 0) {
                return null;
            }
            //第一行默认视为标题行，从第二行开始读取
            for (int i = 1; i <= totalRowNum; i++) {
                T t = null;
                try {
                    t = dataClazz.getDeclaredConstructor().newInstance();
                } catch (InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }

                //当前行数据
                Row row = sheet.getRow(i);
                //封装对应列的数据
                //dataClazz 是 FoodExcelDTO.class
                //declaredFields 是指 FoodExcelDTO的字段
                for (Field declaredField : declaredFields) {
                    ExcelProperty excelProperty = declaredField.getAnnotation(ExcelProperty.class);
                    if (excelProperty == null) {
                        continue;
                    }
                    //当前字段对应列索引
                    int index = excelProperty.index() - 1;
                    //自定义图片数据
                    if (declaredField.isAnnotationPresent(ExcelImageData.class)) {
                        String indexKey = i + "-" + index;
                        declaredField.setAccessible(true);
                        declaredField.set(t, imageInfoMap.get(indexKey));
                    }
                    //普通数据
                    else {
                        //Here t is a FoodExcelDTO instance
                        declaredField.setAccessible(true);
                        declaredField.set(t, getCellValue(row.getCell(index)));
                    }
                }
                dataList.add(t);
            }

            return dataList;
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }

    }

    /**
     * 获取sheet中图片及其图片位置信息
     *
     * @param sheet
     * @return 图片及其图片位置信息
     */
    @Override
    public Map<String, PictureData> getExcelPictures(Sheet sheet) {
        //key格式：行号-列号
        Map<String, PictureData> map = new HashMap<>(sheet.getLastRowNum());
        // xls
        if (sheet instanceof HSSFSheet) {
            HSSFSheet hssfSheet = (HSSFSheet) sheet;
            List<HSSFShape> hssfShapes = hssfSheet.getDrawingPatriarch().getChildren();
            for (HSSFShape shape : hssfShapes) {
                if (!(shape instanceof HSSFPicture)) {
                    continue;
                }
                HSSFPicture picture = (HSSFPicture) shape;
                HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
                PictureData pictureData = picture.getPictureData();
                String key = cAnchor.getRow1() + "-" + cAnchor.getCol1();
                map.put(key, pictureData);
            }
        }
        //xlsx
        else if (sheet instanceof XSSFSheet) {
            XSSFSheet xssfSheet = (XSSFSheet) sheet;
            List<POIXMLDocumentPart> relations = xssfSheet.getRelations();
            for (POIXMLDocumentPart part : relations) {
                if (!(part instanceof XSSFDrawing)) {
                    continue;
                }
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    CTMarker marker = anchor.getFrom();
                    String key = marker.getRow() + "-" + marker.getCol();
                    map.put(key, picture.getPictureData());
                }
            }
        }

        return map;
    }

    /**
     * 获取单元格值
     *
     * @param cell 单元格
     * @return 单元格值
     */
    public static Object getCellValue(Cell cell) {
        if (cell.getCellType() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            double numericCellValue = cell.getNumericCellValue();
            return Double.valueOf(numericCellValue).floatValue();
        } else if (cell.getCellType() == CellType.BLANK) {
            return Double.valueOf(0).floatValue();
        } else {
            return cell.getStringCellValue();
        }
    }

}
