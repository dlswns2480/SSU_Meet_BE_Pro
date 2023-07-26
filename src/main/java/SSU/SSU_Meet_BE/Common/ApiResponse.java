package SSU.SSU_Meet_BE.Common;

public record ApiResponse(
        ApiStatus status,
        String message,
        Object data,

        Long id
) {

    // Object data 없이 String message만 받는 생성자 추가
    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(ApiStatus.SUCCESS, message, data, null);
    }
    public static ApiResponse success(String message, Object data, Long id) {
        return new ApiResponse(ApiStatus.SUCCESS, message, data, id);
    }

    public static ApiResponse success(String message) {
        return new ApiResponse(ApiStatus.SUCCESS, message, null, null);
    }


    public static ApiResponse error(String message) {
        return new ApiResponse(ApiStatus.ERROR, message, null, null);
    }
}
