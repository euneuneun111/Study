package day05;

interface DataAccessObject {
    void select();
    void insert();
    void update();
    void delete();
}

class OracleDao implements DataAccessObject {
    @Override
    public void select() {
        System.out.println("Oracle DB에서 검색");
    }

    @Override
    public void insert() {
        System.out.println("Oracle DB에서 삽입");
    }

    @Override
    public void update() {
        System.out.println("Oracle DB에서 수정");
    }

    @Override
    public void delete() {
        System.out.println("Oracle DB에서 삭제");
    }
}

class MysqlDao implements DataAccessObject {
    @Override
    public void select() {
        System.out.println("MySQL DB에서 검색");
    }

    @Override
    public void insert() {
        System.out.println("MySQL DB에서 삽입");
    }

    @Override
    public void update() {
        System.out.println("MySQL DB에서 수정");
    }

    @Override
    public void delete() {
        System.out.println("MySQL DB에서 삭제");
    }
}

public class DaoExample {
    static void dbWork(DataAccessObject dao) {
        dao.select();
        dao.insert();
        dao.update();
        dao.delete();
    }

    public static void main(String[] args) {
        DataAccessObject oracleDao = new OracleDao();
        DataAccessObject mysqlDao = new MysqlDao();

        System.out.println("Oracle DB 작업:");
        dbWork(oracleDao);

        System.out.println("\nMySQL DB 작업:");
        dbWork(mysqlDao);
    }
}

