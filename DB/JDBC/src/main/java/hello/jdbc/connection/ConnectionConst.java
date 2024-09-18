package hello.jdbc.connection;

// 객체 생성을 하면 안되므로 abstract class 로 설정
public abstract class ConnectionConst {
    public static final String URL = "jdbc:h2:tcp://localhost/~/jdbc_test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";

}
