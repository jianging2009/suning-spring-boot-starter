package com.holyw.suning.config;

import com.holyw.suning.client.DefaultDuoduokeClient;
import com.holyw.suning.client.IClient;
import com.holyw.suning.template.DuoduokeGoodsTemplate;
import com.holyw.suning.template.DuoduokeOrderTemplate;
import com.holyw.suning.exception.DuoduokeClientNotFoundException;
import com.holyw.suning.proxy.PddPrimaryProxy;
import com.holyw.suning.support.DuoduokeSupportRepository;
import com.pdd.pop.sdk.http.PopClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@ConditionalOnClass(PopClient.class)
@EnableConfigurationProperties({SuningPropeties.class})
@Configuration
public class SuningAutoConfiguration {

    @Bean
    public IClient duoduokeClient(SuningPropeties suningPropeties) throws DuoduokeClientNotFoundException {
        IClient client;
        if (StringUtils.isEmpty(suningPropeties.getClientClassName())) {
            client = new DefaultDuoduokeClient(suningPropeties);
        } else {
            try {
                Class<IClient> clazz = (Class<IClient>) Class.forName(suningPropeties.getClientClassName());
                client = clazz.newInstance();
            } catch (IllegalAccessException e) {
                throw new DuoduokeClientNotFoundException();
            } catch (InstantiationException e) {
                throw new DuoduokeClientNotFoundException();
            } catch (ClassNotFoundException e) {
                throw new DuoduokeClientNotFoundException();
            }
        }
        return client;

    }

    @Bean
    public DuoduokeSupportRepository duoduokeRepository(IClient client) {
        DuoduokeSupportRepository duoduokeSupportRepository = new PddPrimaryProxy(client).getProxy(DuoduokeSupportRepository.class);
        return duoduokeSupportRepository;
    }

    @Bean
    public DuoduokeGoodsTemplate pddGoodsTemplate(DuoduokeSupportRepository duoduokeSupportRepository) {
        return new DuoduokeGoodsTemplate(duoduokeSupportRepository);
    }

    @Bean
    public DuoduokeOrderTemplate pddOrderTemplate(DuoduokeSupportRepository duoduokeSupportRepository) {
        return new DuoduokeOrderTemplate(duoduokeSupportRepository);
    }

}
