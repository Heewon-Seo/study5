package kr.or.tashow;

import java.util.Scanner;

public class Menu {
    static String cur_user_id;
    Scanner input;
    User user;
    Admin admin;
    FileIO fileIo;
    BikeService bikeService;
    UserSystem userSystem;
    AdminSystem adminSystem;

    public Menu() {
        cur_user_id = "";
        input = new Scanner(System.in);
        user = new User();
        admin = new Admin();
        fileIo = new FileIO();
        bikeService = new BikeService();
        userSystem = new UserSystem();
        adminSystem = new AdminSystem();
        fileIo.startSystem();
    }

    public void displayDefaultMenu() { // 첫 메뉴 번호입력
        // 회원가입, 사용자 인증, 관리자 인증, 프로그램 종료
        while(true) {
            int menu = ViewUtil.displayMenuList("자전거 대여 시스템", new String[]{
                    "회원가입",
                    "사용자 인증",
                    "관리자 인증",
                    "프로그램 종료"
            });

            switch (menu) {
                case 1:
                    userSystem.signUp();
                    break;
                case 2:
                    if (!(userSystem.userLogin() == 1)) {
                        displayUserMenu();
                    }
                    break;
                case 3:
                    if ((adminSystem.adminLogin())) {
                        displayAdminMenu();
                    }
                    break;
                case 4:
                    System.out.println("이용해주셔서 감사합니다!");
                    return;
            }

        }
    }

    private void displayAdminMenu() { // 관리자 메뉴
        // 매출조회, 회원목록조회, 자전거관리, 이전 메뉴

        while(true) {
            int menu = ViewUtil.displayMenuList("관리자 메뉴", new String[]{
                    "매출 조회",
                    "회원 목록 조회",
                    "자전거 관리",
                    "이전 메뉴"
            });
            switch (menu) {
                case 1:
                    bikeService.calculateTotalSales();
                    break;
                case 2:
                    fileIo.readUserList();
                    break;
                case 3:
                    displayBikeMenu();
                    break;
                case 4:
                    return;
            }
        }
    }

    private void displayUserMenu() { // 사용자 메뉴
        // 대여하기 (> 자전거 목록조회), 반납하기 (> 결제 및 반납), 프로그램 종료
        while(true) {
            int menu = ViewUtil.displayMenuList("사용자 메뉴", new String[]{
                "대여하기 (1인용)",
                "대여하기 (2인용)",
                "결제 및 반납하기",
                "이전메뉴"
        });

        switch (menu) {
            case 1:
                bikeService.rentalBike("S");
                break;
            case 2:
                bikeService.rentalBike("T");
                break;
            case 3:
                bikeService.returnBike();
                break;
            case 4:
                return;
        }
        }
    }

    private void displayBikeMenu() { // 자전거 관리 메뉴 번호입력

        while(true) {
            int menu = ViewUtil.displayMenuList("자전거 관리 메뉴", new String[]{
                    "자전거 등록",
                    "자전거 삭제",
                    "자전거 목록 조회",
                    "자전거 대여 내역 조회",
                    "이전 메뉴"
            });

            switch (menu) {
                case 1:
                    adminSystem.addBike();
                    break;
                case 2:
                    adminSystem.removeBike();
                    break;
                case 3:
                    fileIo.readBikeList();
                    break;
                case 4:
                    fileIo.readRentList();
                    break;
                case 5:
                    return;
            }
        }
    }
}