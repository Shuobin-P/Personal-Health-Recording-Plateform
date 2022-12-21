package com.google.personalhealthrecordingplateform.mapper;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.dto.GoodsDTO;
import com.google.personalhealthrecordingplateform.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/17 11:51
 */
@Mapper
public interface GoodsMapper {
    void update(Goods goods);

    /**
     * 删除商品信息
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 商品数据查询
     *
     * @param queryString 根据字符串查询
     * @return
     */
    Page<GoodsDTO> findPage(String queryString);

    /**
     * 查询所有商品
     *
     * @return 商品列表
     */
    List<Goods> findAll();

    /**
     * @param name
     * @return
     */
    Long findGoodsIdByName(String name);

    /**
     * 批量导入商品信息
     *
     * @param goods
     */
    void batchInsert(List<Goods> goods);

}
