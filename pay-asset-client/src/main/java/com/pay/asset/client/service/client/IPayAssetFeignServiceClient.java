package com.pay.asset.client.service.client;

import com.pay.asset.client.service.IPayAssetFeignService;
import com.pay.asset.client.service.hystric.PayAssetFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author chenwei
 * @date 2019/5/20 10:28
 */
@FeignClient(value = "pay-asset", fallback = PayAssetFeignServiceFallback.class)
public interface IPayAssetFeignServiceClient extends IPayAssetFeignService {
}
