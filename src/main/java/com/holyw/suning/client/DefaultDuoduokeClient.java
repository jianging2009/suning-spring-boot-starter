package com.holyw.suning.client;

import com.holyw.suning.config.SuningPropeties;
import com.pdd.pop.sdk.http.HttpClientConfig;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;

/**
 * DenchyChiang
 */
public class DefaultDuoduokeClient implements IDuoduokeClient {

    private SuningPropeties suningPropeties;

    public DefaultDuoduokeClient(SuningPropeties suningPropeties) {
        this.suningPropeties = suningPropeties;
    }

    @Override
    public PopClient getDefaultClient() {
        HttpClientConfig httpClientConfig = new HttpClientConfig();
        httpClientConfig.setConnectionTimeoutMillis(suningPropeties.getConnectionTimeoutMillis());
        httpClientConfig.setConnectionRequestTimeout(suningPropeties.getConnectionRequestTimeout());
        return new PopHttpClient(suningPropeties.getClientId().replaceAll("\"", ""), suningPropeties.getClientSecret().replaceAll("\"", ""), httpClientConfig);
    }
}
