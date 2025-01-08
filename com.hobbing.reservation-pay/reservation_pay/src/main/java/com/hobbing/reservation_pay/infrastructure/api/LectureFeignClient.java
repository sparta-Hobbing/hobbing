package com.hobbing.reservation_pay.infrastructure.api;


import com.hobbing.reservation_pay.domain.client.LectureServiceClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "LectureServiceClient", url = "http://localhost:19040")
public interface LectureFeignClient extends LectureServiceClient {

    @Override
    @PostMapping//todo 지용님 api 구현완료 시 수정
    void cancelLectureReservation(CancelReservedLectureDto dto);
}
