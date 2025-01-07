package com.hobbing.reservation_pay.presentation;


import com.hobbing.reservation_pay.application.SettlementService;
import com.hobbing.reservation_pay.application.dto.CreateSettlementDto;
import com.hobbing.reservation_pay.domain.model.Settlement;
import com.hobbing.reservation_pay.presentation.dto.PostSettlementReqBody;
import com.hobbing.reservation_pay.presentation.dto.PutSettleReqBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping
    public ApiResponse<List<UUID>> postSettlements(@Valid @RequestBody
                                                   List<PostSettlementReqBody> reqBody) {

        List<CreateSettlementDto> dtoList
                = reqBody.stream()
                .map(PostSettlementReqBody::toDto)
                .toList();

        List<UUID> createdIdList
                = settlementService.createSettlements(dtoList)
                .stream()
                .map(Settlement::getId)
                .toList();

        return ApiResponse.ofSuccess(
                HttpStatus.CREATED, "정산 생성 성공", createdIdList
        );
    }
}
