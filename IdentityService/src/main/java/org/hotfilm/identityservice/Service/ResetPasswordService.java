package org.hotfilm.identityservice.Service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class ResetPasswordService {

    @Getter
    private static class ResetKeyInfo {
        private final String resetKey;
        private final long expirationTime;

        public ResetKeyInfo(String resetKey, long expirationTime) {
            this.resetKey = resetKey;
            this.expirationTime = expirationTime;
        }
    }
    // Map lưu email, reset_key và thời gian hết hạn
    private final ConcurrentHashMap<String, ResetKeyInfo> cache = new ConcurrentHashMap<>();
    private final long TOKEN_EXPIRATION_TIME = TimeUnit.SECONDS.toMillis(60);

    // Map lưu số lần yêu cầu reset cho mỗi email và thời gian yêu cầu
    private final ConcurrentHashMap<String, List<Long>> requestTimestamps = new ConcurrentHashMap<>();
    private final int MAX_REQUESTS = 5;
    private final long REQUEST_WINDOW_TIME = TimeUnit.MINUTES.toMillis(10);

    // Lưu reset_key nếu số lần yêu cầu không vượt quá 5 trong 10 phút
    public void storeResetKey(String email, String reset_key) {
        if (canRequestReset(email)) {
            long expirationTime = System.currentTimeMillis() + TOKEN_EXPIRATION_TIME;
            cache.put(email, new ResetKeyInfo(reset_key, expirationTime));
            logResetRequest(email);
        } else {
            throw new IllegalStateException(
                    "The limit of the number of reset requests in 10 minutes has been exceeded.");
        }
    }

    // Lấy reset_key nếu chưa hết hạn, nếu hết hạn trả về null
    public String getResetKey(String email) {
        ResetKeyInfo resetKeyInfo = cache.get(email);
        if (resetKeyInfo != null) {
            if (System.currentTimeMillis() < resetKeyInfo.getExpirationTime()) {
                return resetKeyInfo.getResetKey();
            } else {
                // Nếu hết hạn, xóa khỏi cache và trả về null
                cache.remove(email);
            }
        }
        return null;
    }
    // Xóa reset_key khỏi cache
    public void removeResetKey(String email) {
        cache.remove(email);
    }
    // Kiểm tra xem người dùng có thể yêu cầu reset nữa không (dưới 5 lần trong 10 phút)
    private boolean canRequestReset(String email) {
        List<Long> timestamps = requestTimestamps.getOrDefault(email, new ArrayList<>());
        long now = System.currentTimeMillis();

        // Xóa các yêu cầu đã quá 10 phút khỏi danh sách
        timestamps.removeIf(timestamp -> now - timestamp > REQUEST_WINDOW_TIME);

        // Cập nhật lại danh sách yêu cầu reset
        requestTimestamps.put(email, timestamps);

        // Kiểm tra xem còn dưới 5 yêu cầu trong 10 phút không
        return timestamps.size() < MAX_REQUESTS;
    }

    // Lưu lại thời điểm yêu cầu reset
    private void logResetRequest(String email) {
        List<Long> timestamps = requestTimestamps.getOrDefault(email, new ArrayList<>());
        timestamps.add(System.currentTimeMillis());
        requestTimestamps.put(email, timestamps);
    }
}
