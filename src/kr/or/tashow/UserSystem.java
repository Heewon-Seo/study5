package kr.or.tashow;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSystem {

    static HashMap<String, User> userList = new HashMap<>();
    private final Pattern userPhonNumPattern;
    private final Pattern userNamePattern;
    private final Pattern userPwdPattern;
    User user;
    Scanner input;
    FileIO fileIo;
    String userPhoneNum;
    String userPwd;
    String userName;

    public UserSystem() {
        user = new User();
        input = new Scanner(System.in);
        fileIo = new FileIO();
        userPhoneNum = "";
        userPwd = "";
        userName = "";
        userPhonNumPattern = Pattern.compile("^01(0|1|6|7|8|9)-\\d{3,4}-\\d{4}$"); // 핸드폰 번호 형식
        userNamePattern = Pattern.compile("^[가-힣]*$"); // 이름 형식 (한글만)
        userPwdPattern = Pattern.compile("^(?=.*[A-Za-z])[A-Za-z!@#$%^&?_~\\d]{6,8}$"); // 비밀번호 형식
    }

    void signUp() { // 휴대폰 번호 입력 반복문
        System.out.println("휴대폰 번호(ID) 입력 | 0. 초기화면");
        Matcher phone;
        this.userPhoneNum = input.nextLine().trim(); // 휴대폰번호를 받아서 공백 제거
        phone = userPhonNumPattern.matcher(this.userPhoneNum); // 패턴에 맞는지 확인
        if (this.userPhoneNum.equals("0")) {
            System.out.println("초기화면으로 돌아갑니다");
        } else if (!phone.find()) { // 형식에 맞지 않으면
            System.out.println("아래 형식에 맞게 입력해 주세요"); // 재입력 문구 출력
            System.out.println("ex) 010-1234-5678");
            signUp();
        } else if (userList.containsKey(userPhoneNum)) { // userList의 key에 이미 같은 번호가 있으면
            System.out.println("이미 등록된 아이디입니다"); // 등록된 아이디라고 출력
            signUp();
        } else { // 둘 다 아니면
            signUpPwd();
        }
    }

    void signUpPwd() {// 비밀번호 입력 반복문
        System.out.println("비밀번호를 입력해주세요 | 0. 초기화면");
        System.out.println("------------------------");
        System.out.println("비밀번호 형식 : \n1. 최소 영문 하나 이상 포함\n2. 특수 문자(!@#$%^&*?_~),숫자,영문(대소문자)만 가능\n3. 6글자 ~ 8글자");
        System.out.println("------------------------");
        Matcher pwd;
        this.userPwd = input.nextLine().trim();
        pwd = userPwdPattern.matcher(this.userPwd); // 비밀번호 입력 받아서 확인
        if (this.userPwd.equals("0")) {
            System.out.println("초기화면으로 돌아갑니다");
        } else if (!pwd.find()) { // 패턴에 맞지 않으면
            System.out.println("비밀번호 형식을 확인하고 재입력 해주세요"); // 재입력
            signUpPwd();
        } else { // 아니면
            signUpName();
        }
    }

    void signUpName() {// 이름 입력 반복문
        System.out.println("이름을 입력해주세요 | 0. 초기화면");
        Matcher name;
        userName = input.nextLine();
        name = userNamePattern.matcher(this.userName); // 이름 입력 받은 후 형식 확인

        if (userName.equals("0")) {
            System.out.println("초기화면으로 돌아갑니다");
        } else if (!name.find()) { // 아니면
            System.out.println("다시 한 번 이름을 입력해 주세요");// 오류 출력
            System.out.println("ex) 홍길동 ");
            signUpName();
        } else {
            userList.put(userPhoneNum, new User(userPhoneNum, userName, userPwd));
            fileIo.writeUserList();
            showResult();
        }
    }


    void showResult() {   // 입력받은 값을 보여주기
        System.out.println("회원가입이 완료되었습니다");
        System.out.println("*****************************************************************");
        System.out.println("\t이름 : " + userName + ", \tID : " + userPhoneNum + ",\t 비밀번호 : " + userPwd);
        System.out.println("*****************************************************************");
        System.out.println();
    }

    int userLogin() {
        int cnt = 0;

        while (cnt == 0) {
            System.out.println("ID를 입력해주세요 | 0. 초기화면");
            System.out.println("ID는 휴대폰 번호입니다. (010-0000-0000)");
            userPhoneNum = input.nextLine().trim();
            Matcher phone = userPhonNumPattern.matcher(userPhoneNum);
            if (userPhoneNum.equals("0")) {
                System.out.println("초기화면으로 돌아갑니다");
                break;
            } else if (!phone.find()) {
                System.out.println("휴대폰 번호 양식에 맞춰서 다시 입력해주세요");
                System.out.println("ex) 010-1234-5678");
                continue;
            } else if (!userList.containsKey(userPhoneNum)) {
                System.out.println("ID를 찾을 수 없습니다");
                System.out.println("다시 한 번 정확히 입력해 주세요");
                continue;
            }
            for (cnt = 3; cnt > 0; cnt--) {
                System.out.println("비밀번호를 입력해주세요");
                userPwd = input.nextLine();

                if (userList.get(userPhoneNum).getUserPwd().equals(userPwd)) {
                    System.out.println();
                    System.out.println("***************");
                    System.out.println(userList.get(userPhoneNum).getUserName() + "님, 환영합니다!");
                    System.out.println("***************");
                    System.out.println();
                    Menu.cur_user_id = userPhoneNum;
                    break;

                } else {
                    if (cnt > 1) {
                        System.out.println("입력하신 비밀번호가 잘못되었습니다");
                        System.out.print((cnt - 1) + "번의 기회가 남았습니다. 올바른 ");
                    } else {
                        System.out.println("입력하신 비밀번호가 잘못되었습니다");
                        System.out.println("처음으로 돌아갑니다");
                        return cnt;
                    }
                }
            }
        }
        return cnt + 1;
    }
}
