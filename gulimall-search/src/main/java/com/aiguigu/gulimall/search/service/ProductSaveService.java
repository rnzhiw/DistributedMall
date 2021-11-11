package com.aiguigu.gulimall.search.service;


import com.atguigu.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ProductSaveService {

    public boolean productStatusUp(List<SkuEsModel> esModels) throws IOException;

}
