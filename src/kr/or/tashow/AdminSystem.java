package kr.or.tashow;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static kr.or.tashow.BikeService.bikeList;

public class AdminSystem {
    Bike bike;
    BikeService bikeService;
    FileIO fileIo;
    Scanner scan;
    Admin admin;
    HashMap<String, String> adminMap = new HashMap<>();

    public AdminSystem() {
        bike = new Bike();
        bikeService = new BikeService();
        fileIo = new FileIO();
        scan = new Scanner(System.in);
        admin = new Admin();
        adminMap.put(admin.getId(),admin.getPw());
    }

    void addBike() {
        System.out.println("등록하고자 하는 자전거의 종류를 입력하세요");
        System.out.println("1. 1인용 자전거 | 2. 2인용 자전거 | 0. 돌아가기");
        while (true) {
            int input;
            try {
                input = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("1인용은 1번, 2인용은 2번, 이전 메뉴로 돌아가시려면 0번을 입력해 주세요");
                continue;
            }
            if (!(input == 1 || input == 2 || input == 0)) {
                System.out.println("1인용 혹은 2인용만 등록 가능합니다");
                System.out.println("1인용은 1번, 2인용은 2번, 이전 메뉴로 돌아가시려면 0번을 입력해 주세요");
            } else if (input == 0) {
                System.out.println("이전메뉴으로 돌아갑니다"); // 추가
                return;
            } else {
                System.out.println("등록할 자전거 대수를 입력하세요 | 취소: 숫자 제외 아무키");
                int amount;
                try {
                    amount = Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("자전거 등록이 취소되었습니다");
                    System.out.println("이전 메뉴로 돌아갑니다");
                    return;
                }
                String bikeId;
                if (((bikeList.size()) + (amount)) > 100) {
                    System.out.println("자전거는 최대 100대까지만 등록할 수 있습니다");
                    System.out.println("현재 등록된 자전거: " + bikeList.size() + "대");
                } else if (input == 1) {
                    for (int i = 0; i < amount; i++) {
                        bikeId = String.format("S-%04d", bikeList.size());
                        bikeList.put(bikeId, new Bike(BikeType.Single, 1000));
                        fileIo.writeBikeList();
                        System.out.println("[" + bikeId + "가 등록되었습니다]");
                    }
                    return;
                } else {
                    for (int i = 0; i < amount; i++) {
                        bikeId = String.format("T-%04d", bikeList.size());
                        bikeList.put(bikeId, new Bike(BikeType.Twin, 2000));
                        fileIo.writeBikeList();
                        System.out.println("[" + bikeId + "가 등록되었습니다]");
                    }
                    return;
                }
            }
        }
    }

    void removeBike() {
        fileIo.loadRentList();
        int menu = 0;
        try {
            scan = new Scanner(System.in);
            System.out.println("원하는 삭제 방법을 선택해 주세요");
            System.out.println("1. 특정 자전거 삭제 | 2. 일괄 삭제 | 0. 돌아가기");
            menu = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력 가능합니다");
        }
        if (!(menu == 1 || menu == 2 || menu == 0)) {
            System.out.println("잘못 입력하셨습니다");
        } else if (menu == 0) {
            System.out.println("이전메뉴로 돌아갑니다"); // 추가
        } else if (bikeList.isEmpty()) {
            System.out.println("등록된 자전거가 없습니다."); // 수정
        } else if (menu == 2) {
            for (Map.Entry<String, Bike> entrySet : bikeList.entrySet()) {
                if (entrySet.getValue().getRentalStatus().equals(RentalStatus.UNAVAILABLE)) {
                    System.out.println("대여중인 자전거가 있습니다");
                    System.out.println("자전거를 모두 반납 후 시도해 주세요");
                    break;
                } else {
                    bikeList.clear();
                    System.out.println("리스트가 초기화 되었습니다");
                    fileIo.writeBikeList();
                    break;
                }
            }
        } else removeById();
    }

    void removeById() {
        while (true) {
            String id = null;
            try {
                System.out.println("삭제 하고자 하는 자전거의 일련번호를 입력해 주세요");
                id = scan.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (id.equals("0")) {
                System.out.println("삭제가 취소되었습니다");
                break;
            } else if (!bikeList.containsKey(id)) {
                System.out.println("일치하는 자전거가 없습니다 | 0. 취소");
            } else if (bikeList.get(id).getRentalStatus().equals(RentalStatus.UNAVAILABLE)) {
                System.out.println("해당 자전거는 대여중 입니다 | 0. 취소");
            } else {
                bikeList.remove(id);
                System.out.println(id + " 자전거가 삭제되었습니다");
                fileIo.writeBikeList();
                break;
            }
        }
    }

    boolean adminLogin() {
        String id;
        String pwd;
        while (true) {
            System.out.println("관리자 ID | 0. 초기화면");
            id = scan.nextLine().trim().toLowerCase();
            if (id.equals("0")) {
                System.out.println("초기화면으로 돌아갑니다"); // 돌아가기 추가
                return false;
            } else if (!adminMap.containsKey(id)) {
                System.out.println("ID가 맞지 않습니다. 재입력 해주세요");
                continue;
            } else {
                System.out.println("관리자 비밀번호를 입력해주세요 | 0. 초기화면");
                pwd = scan.nextLine().trim();
            }
            if (pwd.equals("0")) {
                System.out.println("초기화면으로 돌아갑니다");
                return false;
            } else if (adminMap.get(id).equals(pwd)) {
                System.out.println("관리자 인증이 완료되었습니다!");
                return true;
            } else {
                System.out.println("비밀번호가 맞지 않습니다. 재입력 해주세요");
            }
        }
    }
}
