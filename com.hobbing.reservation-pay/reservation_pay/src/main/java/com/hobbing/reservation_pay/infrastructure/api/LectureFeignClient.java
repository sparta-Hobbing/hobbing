package com.hobbing.reservation_pay.infrastructure.api;


import com.hobbing.reservation_pay.domain.client.CancelReservedLectureDto;
import com.hobbing.reservation_pay.domain.client.LectureServiceClient;
import com.hobbing.reservation_pay.domain.client.ReserveLectureDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

//todo 지용님 api 구현완료 시 수정
@FeignClient(name = "LectureServiceClient", url = "http://localhost:19040")
public interface LectureFeignClient extends LectureServiceClient {

    @Override
    @PostMapping
    void cancelReservation(CancelReservedLectureDto dto);

    @Override
    @PostMapping
    void reserveLecture(ReserveLectureDto dto);
}
