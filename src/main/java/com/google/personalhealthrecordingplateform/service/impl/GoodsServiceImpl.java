package com.google.personalhealthrecordingplateform.service.impl;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.dto.GoodsDTO;
import com.google.personalhealthrecordingplateform.dto.GoodsExportExcelDTO;
import com.google.personalhealthrecordingplateform.dto.GoodsImportExcelDTO;
import com.google.personalhealthrecordingplateform.entity.Goods;
import com.google.personalhealthrecordingplateform.enumeration.ExcelTypeEnum;
import com.google.personalhealthrecordingplateform.mapper.GoodsMapper;
import com.google.personalhealthrecordingplateform.service.GoodsService;
import com.google.personalhealthrecordingplateform.service.PoiExcelService;
import com.google.personalhealthrecordingplateform.util.DateUtils;
import com.google.personalhealthrecordingplateform.util.SecurityUtils;
import javassist.tools.web.BadHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/17 11:49
 */
@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {
    private final DateUtils dateUtils;
    private final GoodsMapper goodsMapper;
    private final PoiExcelService poiExcelService;

    @Autowired
    public GoodsServiceImpl(DateUtils dateUtils, GoodsMapper goodsMapper, PoiExcelService poiExcelService) {
        this.dateUtils = dateUtils;
        this.goodsMapper = goodsMapper;
        this.poiExcelService = poiExcelService;
    }

    @Override
    public void importGoodsExcel(MultipartFile file) throws IOException, BadHttpRequest, IllegalAccessException, InstantiationException {
        try {
            InputStream inputStream = file.getInputStream();
            ExcelTypeEnum excelTypeEnum = file.getOriginalFilename().endsWith(".xls") ? ExcelTypeEnum.XLS : ExcelTypeEnum.XLSX;
            List<GoodsImportExcelDTO> goodsImportExcelDTOS = poiExcelService.read(inputStream, GoodsImportExcelDTO.class, excelTypeEnum);

            // ---- 转化数据 -----
            List<Goods> goodsList = new ArrayList<>();
            log.info("FoodExcelDTOS的大小为：" + goodsImportExcelDTOS.size());
            for (GoodsImportExcelDTO goodsImportExcelDTO : goodsImportExcelDTOS) {
                Goods goods = new Goods();
                BeanUtils.copyProperties(goodsImportExcelDTO, goods);
                //默认只会导入数据库里面没有的产品
                String name = goodsImportExcelDTO.getName();
                log.info("" + goodsImportExcelDTO);
                if (null == name) {
                    throw new BadHttpRequest(new Exception("导入失败，Excel文件中存在商品名称为空的记录"));
                }
                //说明数据库中不存在这种商品信息
                if (null == goodsMapper.findGoodsIdByName(goodsImportExcelDTO.getName())) {
                    goods.setCreateTime(new Date());
                    goods.setCreateUserId(SecurityUtils.getUserId());
                    goods.setAnnotation("管理员：" + SecurityUtils.getUsername() + "于" + dateUtils.getCurrentDate() + "将商品：" + goodsImportExcelDTO.getName() + "加入库存，数量：" + goodsImportExcelDTO.getNumber());
                    goodsList.add(goods);
                }
            }
            // ----- 持久化数据 -----
            goodsMapper.batchInsert(goodsList);
        } catch (IllegalAccessException | InstantiationException | IOException | BadHttpRequest e) {
            throw e;
        } finally {
            try {
                file.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void exportGoodsExcel(HttpServletResponse response) throws IOException {
        List<Goods> goodsList = goodsMapper.findAll();
        List<GoodsExportExcelDTO> goodsExportExcelDTOList = new ArrayList<>();
        for (Goods goods : goodsList) {
            GoodsExportExcelDTO excelDto = new GoodsExportExcelDTO();
            BeanUtils.copyProperties(goods, excelDto);
            goodsExportExcelDTOList.add(excelDto);
        }
        try (
                // ----- 获取工作簿 -----
                XSSFWorkbook workbook = poiExcelService.writeXSSFWorkbook("商品信息", goodsExportExcelDTOList, GoodsExportExcelDTO.class);
                // ----- 响应到客户端 -----
                OutputStream os = response.getOutputStream()
        ) {
            String exportFileName = "goodsInfo.xlsx";
            setExcelResponseHeader(response, exportFileName);
            workbook.write(os);
            os.flush();
        } catch (Exception e) {
            throw e;
        }
    }

    public void setExcelResponseHeader(HttpServletResponse response, String fileName) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            //避免中文乱码
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void delete(Long id) {
        goodsMapper.delete(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(Goods goods) {
        goodsMapper.update(goods);
    }

    @Override
    public Page<GoodsDTO> findPage(String queryString) {
        return goodsMapper.findPage(queryString);
    }
}
