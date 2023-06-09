package com._8attery.seesaw.service.battery;

import com._8attery.seesaw.domain.battery.BatteryRepository;
import com._8attery.seesaw.dto.api.response.ProjectResponseDto;
import com._8attery.seesaw.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com._8attery.seesaw.exception.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BatteryService {

    private final BatteryRepository batteryRepository;

    // 활동량 목표 설정
    @Transactional
    public Integer setUserActivityGoal(Long userId, Integer req) throws BaseException {
        try {
            batteryRepository.addUserActivityGoal(userId, req);

            return batteryRepository.findUserActivityGoal(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 수면량 목표 설정
    @Transactional
    public Integer setUserSleepGoal(Long userId, Integer req) throws BaseException {
        try {
            batteryRepository.addUserSleepGoal(userId, req);

            return batteryRepository.findUserSleepGoal(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 현재 활동량 설정
    @Transactional
    public Integer setUserCurActivity(Long userId, Integer req) throws BaseException {
        try {
            batteryRepository.addUserCurActivity(userId, req);

            return batteryRepository.findUserCurActivity(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 오늘 수면량 설정
    public Integer setUserCurSleep(Long userId, Integer req) throws BaseException {
        try {
            batteryRepository.addUserCurSleep(userId, req);

            return batteryRepository.findUserCurSleep(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
