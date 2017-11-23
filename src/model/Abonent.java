package model;

@TableName(tablename="Abonente")
public class Abonent {
    private int id;
    private int uid;
    private String phoneNum;
    private float balance;
    private int status;

    public Abonent(int id, int uid, String phoneNum, float balance, int status) {
        this.id = id;
        this.uid = uid;
        this.phoneNum = phoneNum;
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
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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
}
