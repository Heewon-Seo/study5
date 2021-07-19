/*
조별과제 >> 카페에 올리기
1. 1 ~ 45 까지의 난수를 발생 시켜서 6개의 배열 방에 담으세요
   (int)(Math.random()*45)+1
   lotto[0] ... lotto[5]
2. 배열에 담긴 6개의 배열값은 중복값이 나오면 안됨 (중복값 검증)
   ex) lotto[0] = 45 > 난수 추출 > lotto[1]에 45 중복 발생 > 방에 못들어가게
3. 배열에 담긴 6개의 값을 낮은 순으로 정렬
4. 위 결과가 담긴 배열 6개를 출력
----------------------
1. 기능 >> method >> 함수 하나당 기능 하나
2. public >> 참조변수
3. private >> 내부 사용 (공통)
4. 실번호 추출해 주세요
5. 중복값 나오면 안돼요
6. 낮은 순으로 정렬해 주세요
7. 화면에 출력해 주세요
8. 메뉴 기능을 만들어 주세요
9. 5의 배수가 나오면 재추출
 */

import java.util.Scanner;

class Lotto1 {

    private int[] lotto = new int[6];
    int i, j, a;

    // 랜덤 숫자 생성 및 중복값 제거
    private void LottoRandom1() {
        for(i = 0 ; i < lotto.length; i++) {
            lotto[i] = (int)((Math.random()*45)+1);
            for (j = 0; j < i; j++) {
                if (lotto[i] == lotto[j]) {
                    i--;
                    break;
                }
            }
        }
    }

    // 5의 배수 빼고 추출
    private void LottoRandom2() {
        for(i = 0 ; i < lotto.length; i++) {
            lotto[i] = (int)((Math.random()*45)+1);
            if(lotto[i]%5 == 0 ) {
                i--;
                continue;
            } for (j = 0; j < i; j++) {
                if (lotto[i] == lotto[j]) {
                    i--;
                    break;
                }
            }
        }
    }


    // 오름차순 정렬
    private void LottoSort(){
        for(i = 0 ; i < lotto.length; i++) {
            for (j = i+1 ; j< lotto.length ; j++) {
                if (lotto[i] > lotto[j]) {
                    a = lotto[i];
                    lotto[i] = lotto[j];
                    lotto[j] = a;
                }
            }
        }
    }

    // 출력
    private void LottoOutput () {
        for(i = 0; i< lotto.length ; i++) {
            System.out.printf("[%d] ",lotto[i]);
        }
        System.out.println();
    }

    // 1번 메뉴 실행 함수
    private void LottoStart1() {
        LottoRandom1();
        LottoSort();
        LottoOutput();
    }

    // 2번 메뉴 실행 함수
    private void LottoStart2() {
        LottoRandom2();
        LottoSort();
        LottoOutput();
    }

    // 메뉴 출력
    private int displayMenu() {
        System.out.println("************************");
        System.out.println("--- 로또 번호 자동 추출 ---");
        System.out.println("   1. 추출 시작");
        System.out.println();
        System.out.println("   2. 5의 배수 빼고 추출");
        System.out.println();
        System.out.println("   3. 프로그램 종료");
        System.out.println("************************");

        int menu = 0;
        do { // 메뉴 번호 오류 확인
            try {
                Scanner sc = new Scanner(System.in);
                menu = Integer.parseInt(sc.nextLine());
                if (menu >= 1 && menu <= 3) {
                    break;
                } else {
                    throw new Exception("메뉴 번호 오류");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("메뉴 1~3번 중 다시 입력해 주세요");
            }

        } while (true);
        return menu;
    }

    // 실행 함수
    public void menu () {
        while (true) {
            switch (displayMenu()) {
                case 1:
                    System.out.println("------------------------");
                    LottoStart1();
                    System.out.println("------------------------");
                    break;
                case 2:
                    System.out.println("------------------------");
                    LottoStart2();
                    System.out.println("------------------------");
                    break;
                case 3:
                    System.out.println("프로그램을 종료합니다.");
                    return;
            }
        }
    }

}


public class Lotto_Menu {
    public static void main(String[] args) {

        Lotto1 lotto = new Lotto1(); // 객체 생성
        lotto.menu(); // 실행
    }
}
