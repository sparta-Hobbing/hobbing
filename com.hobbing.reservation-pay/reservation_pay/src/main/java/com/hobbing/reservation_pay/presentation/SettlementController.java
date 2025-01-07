package com.hobbing.reservation_pay.presentation;


import com.hobbing.reservation_pay.application.SettlementService;
import com.hobbing.reservation_pay.domain.model.Settlement;
import com.hobbing.reservation_pay.presentation.dto.PutSettleReqBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;


    @GetMapping("/{id}")
    public ApiResponse<GetSettlementResBody> getSettlement(@PathVariable UUID id) {

        Settlement settlement = settlementService.readSettlement(id);

        return ApiResponse.ofSuccess(
                HttpStatus.OK, "정산 조회 성공", GetSettlementResBody.from(settlement)
        );
    }

    @PutMapping("/{id}/pay-info")
    public ApiResponse<Void> putSettlement(@PathVariable UUID id,
                                           @RequestBody PutSettleReqBody reqBody) {

        settlementService.updateSettlementPayInfo(id, reqBody.toDto());

        return ApiResponse.ofSuccess(
                HttpStatus.OK, "정산 결제정보 업데이트 성공", null
        );
    }
}
