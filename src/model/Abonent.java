package model;

@TableName(tablename="Abonente")
public class Abonent {
    @PrimaryKey
    private int id;
    private int uid;
    private String phone_Num;
    private float balance;
    private int status;

    public Abonent() {
    }

    public Abonent(int id, int uid, String phoneNum, float balance, int status) {
        this.id = id;
        this.uid = uid;
        this.phone_Num = phoneNum;
        this.balance = balance;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPhoneNum() {
        return phone_Num;
    }

    public void setPhoneNum(String phoneNum) {
        this.phone_Num = phoneNum;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Abonent{" +
                "id=" + id +
                ", uid=" + uid +
                ", phone_Num='" + phone_Num + '\'' +
                ", balance=" + balance +
                ", status=" + status +
                '}';
    }
}
