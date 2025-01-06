package com.hobbing.reservation_pay.presentation;


import com.hobbing.reservation_pay.application.SettlementService;
import com.hobbing.reservation_pay.domain.model.Settlement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
