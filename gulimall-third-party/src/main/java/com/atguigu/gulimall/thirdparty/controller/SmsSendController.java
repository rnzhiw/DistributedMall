package com.atguigu.gulimall.thirdparty.controller;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.thirdparty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class SmsSendController {

    @Autowired
    SmsComponent smsComponent;

    /**
     * 提供给别的服务调用
     * @param phone
     * @param code
     * @return
     */
    @RequestMapping("/send_sms")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {

        smsComponent.sendSmsCode(phone,code);

        return R.ok();
    }

}
