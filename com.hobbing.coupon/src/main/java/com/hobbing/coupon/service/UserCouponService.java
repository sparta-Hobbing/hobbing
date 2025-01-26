package com.hobbing.coupon.service;

import com.hobbing.coupon.dto.IssueCouponRequest;
import com.hobbing.coupon.dto.UseCouponRequest;
import com.hobbing.coupon.dto.UserCouponResponse;
import com.hobbing.coupon.model.Coupon;
import com.hobbing.coupon.model.CouponStatus;
import com.hobbing.coupon.model.UserCoupon;
import com.hobbing.coupon.repository.CouponRepository;
import com.hobbing.coupon.repository.UserCouponRepository;
import com.hobbing.coupon.common.CustomException;
import com.hobbing.coupon.common.CommonErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final ReentrantLock lock = new ReentrantLock();  // 락 추가

    public UserCouponService(UserCouponRepository userCouponRepository, CouponRepository couponRepository) {
        this.userCouponRepository = userCouponRepository;
        this.couponRepository = couponRepository;
    }

    // 사용자에게 쿠폰 발급
    @Transactional
    public UserCouponResponse issueCouponToUser(IssueCouponRequest request) {
        Coupon coupon = couponRepository.findById(request.getCouponId())
                .orElseThrow(() -> new CustomException(CommonErrorCode.COUPON_NOT_FOUND));

        // 발급 수 증가에 락 적용
        lock.lock();
        try {
            // 발급 수 증가
            if (coupon.getIssuedCount() >= coupon.getMaxIssue()) {
                throw new CustomException(CommonErrorCode.COUPON_ISSUE_LIMIT_REACHED);
            }
            coupon.incrementIssuedCount();  // 발급 수 증가
        } finally {
            lock.unlock();  // 락 해제
        }

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(request.getUserId());
        userCoupon.setCoupon(coupon);
        userCoupon.setStatus(CouponStatus.ACTIVE);
        userCoupon.setExpirationDate(coupon.getExpirationDate());

        userCouponRepository.save(userCoupon);

        return new UserCouponResponse(userCoupon);
    }

    // 쿠폰 사용
    @Transactional
    public void useCoupon(UseCouponRequest request) {
        UserCoupon userCoupon = userCouponRepository.findById(request.getUserCouponId())
                .orElseThrow(() -> new CustomException(CommonErrorCode.COUPON_NOT_FOUND));

        if (userCoupon.getStatus() != CouponStatus.ACTIVE) {
            throw new CustomException(CommonErrorCode.INVALID_COUPON_STATE);
        }

        userCoupon.use();

        userCouponRepository.save(userCoupon);
    }

    // 쿠폰 복원
    @Transactional
    public void restoreCoupon(UUID userId, UUID couponId) {
        Coupon coupon = couponRepository.findById(couponId) // Coupon 객체 조회
                .orElseThrow(() -> new CustomException(CommonErrorCode.COUPON_NOT_FOUND));

        UserCoupon userCoupon = userCouponRepository.findByUserIdAndCouponId(userId, couponId)
                .orElseThrow(() -> new CustomException(CommonErrorCode.COUPON_NOT_FOUND));

        if (userCoupon.getStatus() != CouponStatus.USED) {
            throw new CustomException(CommonErrorCode.INVALID_COUPON_STATE);
        }

        userCoupon.restore();

        userCouponRepository.save(userCoupon);
    }

    // 만료된 쿠폰 비활성화
    @Transactional
    public void expireCoupon(UUID userCouponId) {
        UserCoupon userCoupon = userCouponRepository.findById(userCouponId)
                .orElseThrow(() -> new CustomException(CommonErrorCode.COUPON_NOT_FOUND));

        userCoupon.expire();

        userCouponRepository.save(userCoupon);
    }

    // 특정 사용자의 쿠폰 목록을 가져오는 메서드
    public List<UserCoupon> getUserCoupons(UUID userId) {
        return userCouponRepository.findByUserId(userId);
    }

    // 만료된 쿠폰을 가져오는 메서드
    public List<UserCoupon> getExpiredCoupons() {
        return userCouponRepository.findExpiredCoupons();
    }

    // 쿠폰 사용 여부 확인
    public boolean checkCouponUsed(UUID userCouponId) {
        return userCouponRepository.isCouponUsed(userCouponId);
    }

}
