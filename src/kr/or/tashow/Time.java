package kr.or.tashow;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static kr.or.tashow.BikeService.rentList;

public class Time {
    private final SimpleDateFormat dateFormat;
    private Calendar endTime;

    public Time() {
        dateFormat = new SimpleDateFormat("HH:mm");
    }

    Calendar setStartTime() { // 대여 시작 시간 입력
        return Calendar.getInstance(); // 리턴해줌
    }

    Calendar setDefaultEndTime() { // 대여 시작 시간 입력 시, 디폴트로 들어가는 종료시간 초기값
        endTime = Calendar.getInstance(); // 캘린더 인스턴스 받아와서
        endTime.set(Calendar.HOUR_OF_DAY, 0); // 24시간 형식 시간을 '0'으로 세팅
        endTime.set(Calendar.MINUTE, 0); // 분을 '0'으로 세팅
        return endTime; // 리턴해줌
    }

    void setEndTime(int index) { // 진짜 종료 시간 입력
        endTime = Calendar.getInstance(); // 인스턴스 받아와서
        System.out.println("종료 시각: " + dateFormat.format(endTime.getTime())); // 종료시각을 출력해준 후,
        rentList.get(index).setEndTime(endTime); // 파라미터로 받은 index 값 번째에 있는 RentList 객체의 endTime 필드에 입력
    }

    int getTime(Calendar startTime, Calendar endTime) { // 대여시간 계산
        int diffHour = (int) (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000 / (60 * 60);
        // 1000분의 1초 > 나누기 1000 > 60분 * 60초로 나눔 > 시간 계산 완료
        int diffMin = (int) ((endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000 / 60 - diffHour * 60);
        // 1000분의 1초 > 나누기 1000 > 60초로 나눔 > 시간*60초 빼기 > 분 계산 완료
        System.out.println("이용시간: " + diffHour + "시간 " + diffMin + "분"); // 출력해줌
        if (!(diffMin == 0)) { // 분 값이 0이 아니면
            return ++diffHour; // 1시간 더해줌
        } else {
            return diffHour; // 아니면 그대로
        }
    }
}