package cn.com.axel.demo.common;

import cn.com.axel.common.core.utils.http.OkHttpUtils;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.demo.entity.Completion;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description: openai操作类
 * @author: axel
 * @date: 2023/2/8
 */
@Slf4j
public class AiOperator {

    /**
     * 请求数据
     *
     * @param ask_string 数据
     * @return 返回
     */
    public static Result<String> answerMyQuestion(String url, String token, String ask_string) throws IOException {
        Completion openAi = new Completion();
        //添加我们需要输入的内容
        openAi.setModel("text-davinci-003");
        openAi.setPrompt(ask_string);
        openAi.setTemperature(0.7);
        openAi.setMax_tokens(2048);
        openAi.setTop_p(1);
        openAi.setFrequency_penalty(0);
        openAi.setPresence_penalty(0);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        return OkHttpUtils.postJson(url, JSON.toJSONString(openAi), map, new OkHttpUtils.TimeOut().setTimeUnit(TimeUnit.MINUTES));
    }

}
