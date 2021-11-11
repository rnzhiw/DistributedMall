package com.aiguigu.gulimall.search.controller;

import com.aiguigu.gulimall.search.service.MallSearchService;
import com.aiguigu.gulimall.search.vo.SearchParam;
import com.aiguigu.gulimall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {

    @Autowired
    MallSearchService mallSearchService;

    @RequestMapping("/list.html")
    public String list(SearchParam searchParam, Model model) {

        SearchResult result = mallSearchService.search(searchParam);
        model.addAttribute("result",result);

        return "list";
    }

}
