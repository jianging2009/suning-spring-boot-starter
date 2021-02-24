package com.holyw.suning.template;

import com.alibaba.fastjson.JSONObject;
import com.holyw.suning.request.builder.PddDdkGoodsUnitQueryRequestBuilder;
import com.holyw.suning.response.GoodsDetailResponse;
import com.holyw.suning.support.DuoduokeSupportRepository;
import com.holyw.suning.utils.DuoduokeGoodsTransUtils;
import com.holyw.suning.vo.GoodsVO;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsDetailRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsDetailResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsUnitQueryResponse;
import org.springframework.util.StringUtils;

import java.util.List;


public class DuoduokeGoodsTemplate {

    private DuoduokeSupportRepository duoduokeSupportRepository;

    public DuoduokeGoodsTemplate(DuoduokeSupportRepository duoduokeSupportRepository) {
        this.duoduokeSupportRepository = duoduokeSupportRepository;
    }
    //private static final Logger LOGGER = LoggerFactory.getLogger(PddGoodsUtilsService.class);

    /**
     * pdd.ddk.goods.detail多多进宝商品详情查询
     * pdd.ddk.goods.unit.query 查询商品的推广计划
     * 详情 查询优惠券信息
     */
    public GoodsVO getGoodsDetail(PddDdkGoodsDetailRequest request) {
        GoodsVO goodsVO = null;
        try {
            GoodsDetailResponse pddDdkGoodsDetailResponse = this.pddDdkGoodsDetail(request);
            if (null != pddDdkGoodsDetailResponse) {
                List<PddDdkGoodsDetailResponse.GoodsDetailResponseGoodsDetailsItem> goodsDetails = pddDdkGoodsDetailResponse.getGoodsDetails();
                if (null != goodsDetails) {
                    PddDdkGoodsDetailResponse.GoodsDetailResponseGoodsDetailsItem item = goodsDetails.get(0);
                    if (null != item) {
                        JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(item));
                        goodsVO = DuoduokeGoodsTransUtils.transform2GoodsVO(result);
                        if (null != goodsVO) {
                            PddDdkGoodsUnitQueryResponse pddDdkGoodsUnitQueryResponse = duoduokeSupportRepository.pddDdkGoodsUnitQuery(new PddDdkGoodsUnitQueryRequestBuilder().addGoodsId(Long.valueOf(goodsVO.getGoodsId())).build());
                            if (null != pddDdkGoodsUnitQueryResponse) {
                                PddDdkGoodsUnitQueryResponse.DdkGoodsUnitQueryResponse ddkGoodsUnitQueryResponse = pddDdkGoodsUnitQueryResponse.getDdkGoodsUnitQueryResponse();
                                if (null != ddkGoodsUnitQueryResponse) {
                                    String couponId = ddkGoodsUnitQueryResponse.getCouponId();
                                    if (!StringUtils.isEmpty(couponId)) {
                                        goodsVO.setCouponLink(couponId);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            //LOGGER.error("getGoodsDetail:" + e.getMessage() + "," + Arrays.toString(e.getStackTrace()));
        }
        return goodsVO;
    }

    public GoodsDetailResponse pddDdkGoodsDetail(PddDdkGoodsDetailRequest request) {
        try {
            Long startTime = System.currentTimeMillis();
            PddDdkGoodsDetailResponse pddDdkGoodsDetailResponse = duoduokeSupportRepository.pddDdkGoodsDetail(request);
            if (null != pddDdkGoodsDetailResponse && null == pddDdkGoodsDetailResponse.getErrorResponse()) {
                PddDdkGoodsDetailResponse.GoodsDetailResponse goodsDetailResponse = pddDdkGoodsDetailResponse.getGoodsDetailResponse();
                if (null != goodsDetailResponse) {
                    List<PddDdkGoodsDetailResponse.GoodsDetailResponseGoodsDetailsItem> goodsDetails = goodsDetailResponse.getGoodsDetails();
                    if (null != goodsDetails) {
                        Long endTime = System.currentTimeMillis();
                        //LOGGER.info("{}-{},耗时:{}", "获取详情接口getGoodsDetail", request, endTime - startTime);
                        return new GoodsDetailResponse(goodsDetails);
                    }
                }
            } else {
                //LOGGER.info("pdd.ddk.goods.detail多多进宝商品详情查询:" + pddDdkGoodsDetailResponse.getErrorResponse().getErrorCode() + "," + pddDdkGoodsDetailResponse.getErrorResponse().getErrorMsg());
            }
        } catch (Exception e) {
            //LOGGER.error(e.getMessage() + "," + Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

}
