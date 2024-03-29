package com.google.personalhealthrecordingplateform;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/14 11:16
 */
public class PoiTest {

    @Test
    public void test() throws FileNotFoundException {
        String filePath = "D:\\food.xlsx";
        String picStorePath = "D:\\";

        File file = new File(filePath);
        importExcelWithImage(new FileInputStream(file), file.getName(), picStorePath);
    }

    public static void importExcelWithImage(InputStream in, String fileName, String picStorePath) {
        Workbook workbook;
        Sheet sheet;
        //图片数据
        Map<String, PictureData> pictureDataMap;

        //得到工作簿
        try {
            if (fileName.endsWith(".xls")) {
                //2003版本的excel，用.xls结尾
                workbook = new HSSFWorkbook(in);
            } else if (fileName.endsWith(".xlsx")) {
                //2007版本的excel，用.xlsx结尾
                workbook = new XSSFWorkbook(in);
            } else {
                System.err.println("不支持的Excel格式！");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // ----- 获取图片数据 -----
        sheet = workbook.getSheetAt(0);
        // 判断用07还是03的方法获取图片
        if (fileName.endsWith(".xls")) {
            pictureDataMap = getXlsPictures((HSSFSheet) sheet);
        } else {
            pictureDataMap = getXlsxPictures((XSSFSheet) sheet);
        }

        // ----- 获得标题 -----
        Row rowHead = sheet.getRow(0);
        int rowHeadPhysicalNumberOfCells = rowHead.getPhysicalNumberOfCells();
        for (int i = 0; i < rowHeadPhysicalNumberOfCells; i++) {
            Cell cell = rowHead.getCell(i);
            System.out.println(cell.getStringCellValue());
        }

        // ----- 获取其他数据 -----
        int totalRowNum = sheet.getLastRowNum();
        for (int i = 1; i <= totalRowNum; i++) {
            Row row = sheet.getRow(i);
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            for (int j = 0; j < physicalNumberOfCells; j++) {
                Cell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }
        }

        // ----- 输出图片 -----
        try {
            printImg(pictureDataMap, picStorePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片和位置 (xls)
     *
     * @param sheet /
     * @return /
     */
    public static Map<String, PictureData> getXlsPictures(HSSFSheet sheet) {
        Map<String, PictureData> map = new HashMap<>();
        List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
        for (HSSFShape shape : list) {
            if (shape instanceof HSSFPicture) {
                HSSFPicture picture = (HSSFPicture) shape;
                HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
                // 行号-列号
                String key = cAnchor.getRow1() + "-" + cAnchor.getCol1();
                map.put(key, picture.getPictureData());
            }
        }
        return map;
    }

    /**
     * 获取图片和位置 (xlsx)
     *
     * @param sheet /
     * @return /
     */
    public static Map<String, PictureData> getXlsxPictures(XSSFSheet sheet) {
        Map<String, PictureData> map = new HashMap<>(4);
        List<POIXMLDocumentPart> list = sheet.getRelations();
        for (POIXMLDocumentPart part : list) {
            if (part instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    // 行号-列号
                    CTMarker marker = anchor.getFrom();
                    String key = marker.getRow() + "-" + marker.getCol();
                    map.put(key, picture.getPictureData());
                    System.out.println(picture.getPictureData().suggestFileExtension());
                }
            }
        }
        return map;
    }

    /**
     * 输出图片
     *
     * @param pictureDataMap 图片信息
     * @param path           图片保存路径
     * @throws IOException /
     */
    public static void printImg(Map<String, PictureData> pictureDataMap, String path) throws IOException {
        Object[] key = pictureDataMap.keySet().toArray();
        for (int i = 0; i < pictureDataMap.size(); i++) {
            // 获取图片索引
            String picIndex = key[i].toString();
            // 获取图片流
            PictureData pic = pictureDataMap.get(picIndex);
            // 获取图片格式
            String ext = pic.suggestFileExtension();

            byte[] data = pic.getData();

            //图片保存路径
            String imgPath = path + picIndex + "." + ext;
            FileOutputStream out = new FileOutputStream(imgPath);
            out.write(data);
            out.close();

            System.out.println("图片路径：" + imgPath);
        }
    }

}
