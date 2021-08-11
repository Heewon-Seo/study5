package kr.or.tashow;

import java.io.Serializable;
import java.util.Calendar;

public class RentList implements Serializable {
    private String id;
    private String userPhoneNum;
    private Calendar startTime;
    private Calendar endTime;
    private int fee;

    RentList(String id, Calendar startTime, Calendar endTime, String userPhoneNum) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userPhoneNum = userPhoneNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "====================================\n" +
                "일련번호: " + id +
                " | 대여자ID: " + userPhoneNum +
                "\n대여시작: " + startTime.get(Calendar.HOUR_OF_DAY) + "시" + startTime.get(Calendar.MINUTE) + "분" +
                " | 대여종료: " + endTime.get(Calendar.HOUR_OF_DAY) + "시" + endTime.get(Calendar.MINUTE) + "분" +
                " | 결제요금: " + fee +
                "\n====================================\n";
    }
}
