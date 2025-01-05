package com.hobbing.reservation_pay.application;


import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;


@FeignClient(name = "CouponServiceClient", url = "http://localhost:19040")
public interface CouponServiceClient {

    @PostMapping("/coupons/restore")
    void restoreCoupon(@RequestBody RestoreCouponReqBody reqBody);


    @Data(staticConstructor = "of")
    class RestoreCouponReqBody {
        private UUID userCouponId;
    }
}
