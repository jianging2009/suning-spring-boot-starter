package com.holyw.suning.request.builder;

import com.pdd.pop.sdk.http.api.pop.request.PddDdkOrderListIncrementGetRequest;

/**
 * @className: PddDdkOrderListIncrementGetRequestBuilder
 * @description: TODO
 * @date: 2021/1/27
 **/
public class PddDdkOrderListIncrementGetRequestBuilder {
    private PddDdkOrderListIncrementGetRequest request = new PddDdkOrderListIncrementGetRequest();

    public PddDdkOrderListIncrementGetRequest build() {
        return request;
    }

    public PddDdkOrderListIncrementGetRequestBuilder addStartUpdateTime(Long startUpdateTime) {
        request.setStartUpdateTime(startUpdateTime);
        return this;
    }

    public PddDdkOrderListIncrementGetRequestBuilder addEndUpdateTime(Long endUpdateTime) {
        request.setEndUpdateTime(endUpdateTime);
        return this;
    }

    public PddDdkOrderListIncrementGetRequestBuilder addPageSize(Integer pageSize) {
        request.setPageSize(pageSize);
        return this;
    }

    public PddDdkOrderListIncrementGetRequestBuilder addPage(Integer page) {
        request.setPage(page);
        return this;
    }

    public PddDdkOrderListIncrementGetRequestBuilder addReturnCount(Boolean returnCount) {
        request.setReturnCount(returnCount);
        return this;
    }
}
