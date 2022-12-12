package com.google.personalhealthrecordingplateform.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.google.personalhealthrecordingplateform.entity.Food;
import com.google.personalhealthrecordingplateform.service.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/11 15:18
 */
@Slf4j
public class FoodListener implements ReadListener<Food> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    private FoodService foodService;
    private List<Food> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @Autowired
    public FoodListener(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void invoke(Food food, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(food));
        // TODO 把excel解析成为Food对象之前，那些图片还没有上传到七牛云。
        //  读图片的时候，字节流，但是把它变成图片，上传到云
        // 得到food.typeTitle 查数据库 对typeId赋值
        food.setTypeId(foodService.findTypeIDByTypeTitle(food.getTypeTitle()));
        cachedDataList.add(food);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        // FIXME 这里有点奇怪
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        foodService.batchInsert(cachedDataList);
        log.info("存储数据库成功！");
    }
}
