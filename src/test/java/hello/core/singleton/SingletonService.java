package hello.core.singleton;



/*
싱글톤 패턴 => 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인
==> 객체 인스턴스 2개 이상 만들지 못하도록함
==> private 생성자를 이용하여 new 사용 금지 시킴
 */
public class SingletonService {

    //1. static 영역에 객체를 딱 1개만 생성해준다.
    private static final SingletonService instance = new SingletonService();
    //private static 으로 자기 자신의 인스턴스 하나만 생성 => static이라 클래스 레벨로 공유 됨


    //2. public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용
    public static SingletonService getInstance() {
        return instance;
    }


    //3. 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
    private SingletonService() {
        // private 생성자를 사용하면, 외부에서 객체를 생성시키지 못함
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
