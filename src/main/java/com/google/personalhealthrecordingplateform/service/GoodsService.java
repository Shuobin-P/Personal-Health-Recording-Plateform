package com.google.personalhealthrecordingplateform.service;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.dto.GoodsDTO;
import com.google.personalhealthrecordingplateform.entity.Goods;
import javassist.tools.web.BadHttpRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/16 22:25
 */
public interface GoodsService {
    /**
     * 批量上传商品信息
     *
     * @param multipartFile
     */
    void importGoodsExcel(MultipartFile multipartFile) throws IOException, IllegalAccessException, InstantiationException, BadHttpRequest;

    /**
     * @param response
     */
    void exportGoodsExcel(HttpServletResponse response) throws IOException;

    /**
     * 删除商品信息
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 更新商品信息
     *
     * @param goods
     */
    void update(Goods goods);

    /**
     * 商品查询
     *
     * @param queryString
     * @return
     */
    Page<GoodsDTO> findPage(String queryString);
}
