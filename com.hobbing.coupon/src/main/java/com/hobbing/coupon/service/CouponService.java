package com.hobbing.coupon.service;

import com.hobbing.coupon.dto.CreateCouponRequest;
import com.hobbing.coupon.dto.CouponResponse;
import com.hobbing.coupon.dto.PageResponse;
import com.hobbing.coupon.dto.UpdateCouponRequest;
import com.hobbing.coupon.model.Coupon;
import com.hobbing.coupon.repository.CouponRepository;
import com.hobbing.coupon.common.CustomException;
import com.hobbing.coupon.common.CommonErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    // 쿠폰 생성
    @Transactional
    public CouponResponse createCoupon(CreateCouponRequest request) {
        if (couponRepository.existsByCouponName(request.getCouponName())) {
            throw new CustomException(CommonErrorCode.COUPON_NOT_FOUND);
        }

        Coupon coupon = new Coupon();
        coupon.setCouponName(request.getCouponName());
        coupon.setDiscountType(request.getDiscountType());
        coupon.setDiscountAmount(request.getDiscountAmount());
        coupon.setDiscountRate(request.getDiscountRate());
        coupon.setMinOrder(request.getMinOrder());
        coupon.setIssueStart(request.getIssueStart());
        coupon.setIssueDeadline(request.getIssueDeadline());
        coupon.setExpirationDate(request.getExpirationDate());
        coupon.setMaxIssue(request.getMaxIssue());
        coupon.setIssuedCount(0);

        Coupon savedCoupon = couponRepository.save(coupon);
        return new CouponResponse(savedCoupon);
    }

    // 쿠폰 수정
    @Transactional
    public CouponResponse updateCoupon(UUID couponId, UpdateCouponRequest request) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CustomException(CommonErrorCode.COUPON_NOT_FOUND));

        coupon.setDiscountAmount(request.getDiscountAmount());
        coupon.setDiscountRate(request.getDiscountRate());
        coupon.setMinOrder(request.getMinOrder());
        coupon.setIssueDeadline(request.getIssueDeadline());
        coupon.setExpirationDate(request.getExpirationDate());
        coupon.setMaxIssue(request.getMaxIssue());
        coupon.setUpdatedAt(LocalDateTime.now());

        Coupon updatedCoupon = couponRepository.save(coupon);
        return new CouponResponse(updatedCoupon);
    }


    // 쿠폰 삭제
    @Transactional
    public void deleteCoupon(UUID couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CustomException(CommonErrorCode.COUPON_NOT_FOUND));

        coupon.setIsDeleted(true);
        coupon.setDeletedAt(LocalDateTime.now());
        couponRepository.save(coupon);
    }

    // 쿠폰 목록 조회 (관리자 전용)
    @Transactional(readOnly = true)
    public PageResponse<CouponResponse> getCoupons(String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Coupon> couponPage;

        if (status != null) {
            couponPage = couponRepository.findByStatus(status, pageable);
        } else {
            couponPage = couponRepository.findAll(pageable);
        }

        return new PageResponse<>(
                couponPage.getContent().stream().map(CouponResponse::new).collect(Collectors.toList()),
                couponPage.getNumber(),
                couponPage.getSize(),
                couponPage.getTotalElements()
        );
    }
}
