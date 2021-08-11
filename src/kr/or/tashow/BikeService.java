package kr.or.tashow;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class BikeService implements Serializable {
    static HashMap<String, Bike> bikeList = new HashMap<>();
    static ArrayList<RentList> rentList = new ArrayList<>();
    Time time;
    FileIO fileIo;
    User user;
    Scanner scan;
    int availableSingleBikes;
    int availableTwinBikes;

    public BikeService() {
        time = new Time();
        fileIo = new FileIO();
        user = new User();
        scan = new Scanner(System.in);
        availableSingleBikes = 0;
        availableTwinBikes = 0;
    }

    void rentalBike(String type) { // 대여
        DateFormat df = new SimpleDateFormat("HH:mm");
        checkAvailable();
        if (bikeList.isEmpty() || (type.equals("S") && availableSingleBikes == 0)
                || (type.equals("T") && availableTwinBikes == 0)) {
            System.out.println("대여 가능한 자전거가 없습니다");
        } else {
            for (String key : bikeList.keySet()) {
                if (key.startsWith(type) && bikeList.get(key).getRentalStatus().equals(RentalStatus.AVAILABLE)) {
                    Bike bike = bikeList.get(key);
                    bike.setRentalStatus(RentalStatus.UNAVAILABLE);
                    System.out.println("====================================================");
                    System.out.println("            고객님의 자전거 번호: " + key);
                    System.out.println("            대여 시작 시각: " + df.format(time.setStartTime().getTime()));
                    System.out.println(" ** 반납 시에 자전거 번호를 입력하셔야 하니, 잘 기억해 주세요! **");
                    System.out.println("====================================================");
                    rentList.add(new RentList(key, time.setStartTime(), time.setDefaultEndTime(), Menu.cur_user_id));
                    // 기본 종료 시각을 강제로 부여 (0시0분)
                    fileIo.writeBikeList();
                    fileIo.writeRentList();
                    break;
                }
            }
        }
    }

    void checkAvailable() { // 대여 가능한 자전거 대수 계산
        availableSingleBikes = 0;
        availableTwinBikes = 0;
        for (Map.Entry<String, Bike> entrySet : bikeList.entrySet()) {
            BikeType type = entrySet.getValue().getType();
            RentalStatus status = entrySet.getValue().getRentalStatus();
            if (type.equals(BikeType.Single) && status.equals(RentalStatus.AVAILABLE)) {
                availableSingleBikes++;
            } else if (type.equals(BikeType.Twin) && status.equals(RentalStatus.AVAILABLE)) {
                availableTwinBikes++;
            }
        }
    }

    ArrayList<String> hasBikes() { // 현재 대여중인 자전거가 있는지 확인
        fileIo.loadRentList();
        System.out.println("====== 대여 중인 자전거 목록 ======");
        String bikeId;
        ArrayList<String> bikes = new ArrayList<>();
        for (RentList list : rentList) {
            if (list.getUserPhoneNum().equals(Menu.cur_user_id)) { // 대여 내역에서 사용자가 대여한 내역 찾음
                bikeId = list.getId(); // 그 자전거 ID를 저장
                for (Map.Entry<String, Bike> entrySet : bikeList.entrySet()) { // 자전거 리스트 검색
                    if (bikeId.contains(entrySet.getKey()) && entrySet.getValue().getRentalStatus().equals(RentalStatus.UNAVAILABLE)) {
                        bikes.add(entrySet.getKey());
                        System.out.println(entrySet.getKey());
                    }
                }
            }
        }
        return bikes;
    }

    void returnBike() { // 반납
        String id;
        if (hasBikes().isEmpty()) { //RentList에 대여한 목록에 없다면
            System.out.println("대여 중인 자전거가 없습니다");
            return;
        }
        while (true) {
            System.out.println("반납할 자전거의 일련번호를 입력해주세요 (예: S-1234) | 0. 이전화면");
            try {
                id = scan.nextLine();
                Bike bikeId = bikeList.get(id);
                if (id.equals("0")) {
                    System.out.println("이전화면으로 돌아갑니다");
                    return;
                } else if (bikeId.getRentalStatus().equals(RentalStatus.UNAVAILABLE)) {
                    for (int i = 0; i < rentList.size(); i++) {
                        if (rentList.get(i).getId().contains(id)) {
                            RentList list = rentList.get(i);
                            time.setEndTime(i);
                            int fee = calculateFee(id, list.getStartTime(), list.getEndTime()); // 시간입력
                            payFee(i, fee, id); // 계산
                            fileIo.writeBikeList();
                            fileIo.writeRentList();
                            return;
                        }
                    }
                } else {
                    System.out.println("일련번호가 일치하지 않습니다.");
                }
            } catch (Exception e) {
                System.out.println("알맞은 값을 입력해 주세요");
            }
        }
    }

    void payFee(int index, int fee, String id) { // try catch
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.println("결제요금: " + df.format(fee) + "원");
        System.out.println("[요금안내] 1시간 당 1인용 : 1,000원, 2인용 : 2,000원");
        System.out.println("* 요금은 시간 단위로 계산되어 1분 초과 시부터 올림 적용됩니다");
        System.out.println("결제하시겠습니까?");
        System.out.println("1. 예 | 2. 아니오 (취소)");
        int input = 0;
        try {
            input = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        if (input == 1) {
            rentList.get(index).setFee(fee);
            bikeList.get(id).setRentalStatus(RentalStatus.AVAILABLE); // 반납처리
            fileIo.writeRentList();
            fileIo.writeBikeList();
            System.out.println("결제가 완료되었습니다.");
            System.out.println("이용해 주셔서 감사합니다 :D");
            return;
        } else if (input == 2) {
            rentList.get(index).setFee(0);
            Calendar defaultTime = Calendar.getInstance(); // time.setDefaultTime 쓰면 오류나서 일단 이렇게 해뒀음
            defaultTime.set(Calendar.HOUR_OF_DAY, 0);
            defaultTime.set(Calendar.MINUTE, 0);
            rentList.get(index).setEndTime(defaultTime);
            fileIo.writeRentList();
            System.out.println("결제가 취소되었습니다");
        }
    }

    int calculateFee(String bikeNum, Calendar startTime, Calendar endTime) {
        int fee = 0;
        if (bikeNum.startsWith("S")) {
            fee = time.getTime(startTime, endTime) * 1000;
        } else if (bikeNum.startsWith("T")) {
            fee = time.getTime(startTime, endTime) * 2000;
        }
        return fee;
    }

    void calculateTotalSales() {
        DecimalFormat df = new DecimalFormat("#,###");
        fileIo.loadRentList();
        int fee;
        int totalSales = 0;
        for (RentList list : rentList) {
            fee = list.getFee();
            totalSales += fee;
        }
        System.out.println("=========== 현재 기준 총 매출액 ============");
        System.out.println(df.format(totalSales) + "원\n");
    }
}
