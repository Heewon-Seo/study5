package kr.or.tashow;

/*
enum 을 사용한 이유.. class를 여러개 두지 않고 간결하게 코드구성
HushMap보다는 단순한 리스트 이기 때문에 ArrayList를 사용
이 클래스는 데이터를 담을 수 있는 클래스
 */


import java.io.Serializable;

enum BikeType {
    Single,
    Twin
}

enum RentalStatus {
    // 대여가능
    AVAILABLE,
    // 대여중,
    UNAVAILABLE
}

public class Bike implements Serializable {

    private BikeType type; // 1인용, 2인용
    private RentalStatus rentalStatus; //대여가능, 대여중
    private int price; // 가격

    Bike() {

    }

    Bike(BikeType type, int price) {
        this.type = type;
        this.price = price;
        this.rentalStatus = RentalStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return " 종류: " + type +
                " | 가격: " + price +
                " | 상태: " + rentalStatus;
    }


    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public BikeType getType() {
        return type;
    }

    public void setType(BikeType type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
