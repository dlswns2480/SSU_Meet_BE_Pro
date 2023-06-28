package SSU.SSU_Meet_BE.Entity;

import java.util.ArrayList;
import java.util.List;

public class Member {

    private Long id;

    private Long studentId;

    private boolean firstRegisterCheck;

    private Gender sex; // MALE, FEMALE;

    private Integer age;

    private Long height;

    private String instaId;

    private String kakaoId;

    private String phoneNumber;

    private Integer todayPurchaseCnt; // 오늘 구매한 횟수 (스케쥴러로 자정 체크)

    private Long totalChoicedCnt; // 총 선택받은 횟수

    private Long totalPurchaseCnt; // 총 구매한 횟수

    private List<StickyNote> stickyNotes = new ArrayList<>(); //일대 다


}
