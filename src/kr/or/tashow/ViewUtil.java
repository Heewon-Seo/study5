package kr.or.tashow;

import java.util.Scanner;

public class ViewUtil {
    static int displayMenuList(String menusName, String[] menus) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("****** 비트를 타쇼 " + menusName + " ******");
            System.out.println("     원하는 메뉴의 번호를 입력 하세요");
            for (int i = 0; i < menus.length; i++) {
                System.out.printf("%d. %s%n", i + 1, menus[i]);
            }
            System.out.println("************************************");
            System.out.println();
            try {
                int menu = Integer.parseInt(scanner.nextLine());
                if (menu >= 1 && menu <= menus.length) {
                    return menu;
                } else {
                    throw new Exception("메뉴 번호 오류");
                }
            } catch (Exception e) {
                System.out.println("오류발생: " + e.getMessage());
                System.out.println("1~" + menus.length + " 중 다시 입력해 주세요");
            }
        } while (true);
    }

}
