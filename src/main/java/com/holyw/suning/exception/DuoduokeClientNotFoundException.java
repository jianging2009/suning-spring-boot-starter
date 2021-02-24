package com.holyw.suning.exception;

import com.holyw.suning.config.SuningAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: DuoduokeClientNotFoundException
 * @description: TODO
 * @date: 2021/1/11
 **/
public class DuoduokeClientNotFoundException extends Exception {
    private static final Logger log = LoggerFactory.getLogger(SuningAutoConfiguration.class);

    public DuoduokeClientNotFoundException() {
        super();
        log.info("没有找到Client自定义实现类...");
    }
}
