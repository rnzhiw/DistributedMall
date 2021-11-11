package com.aiguigu.gulimall.search.service;

import com.aiguigu.gulimall.search.vo.SearchParam;
import com.aiguigu.gulimall.search.vo.SearchResult;
import org.springframework.stereotype.Service;

@Service
public interface MallSearchService {

    /**
     *
     * @param searchParam 检索的所有参数
     * @return 返回检索的结果
     */
    SearchResult search(SearchParam searchParam);


}
