package accounts;

public class Account {

    private String userName;
    private String passWord;
    private  boolean admin;

    public Account(String userName, String passWord, boolean admin) {
        this.userName = userName;
        this.passWord = passWord;
        this.admin = admin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
