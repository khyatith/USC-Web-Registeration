package usc.edu.dbHelper;

/**
 * Storing User Info
 */
public class User {
    int UserId;
    String     UserUSCId;
    String firstName;
    String lastName;
    String LocalAddress;
    String    LocalContact;
    int DenStudent;
    String  StartSemester;
    int schoolId;
    int DeptId;
    String UserName;
    String password;

   public User(){}

    public User(String userUSCId, String firstName, String lastName, String localAddress, String localContact, int denStudent, String startSemester, int schoolId, int deptId,String UserName, String password) {
        super();
        this.UserUSCId = userUSCId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.LocalAddress = localAddress;
        this.LocalContact = localContact;
        this.DenStudent = denStudent;
        this.StartSemester = startSemester;
        this.schoolId = schoolId;
        this.DeptId = deptId;
        this.UserName=UserName;
        this.password=password;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserUSCId() {
        return UserUSCId;
    }

    public void setUserUSCId(String userUSCId) {
        UserUSCId = userUSCId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocalAddress() {
        return LocalAddress;
    }

    public void setLocalAddress(String localAddress) {
        LocalAddress = localAddress;
    }

    public String getLocalContact() {
        return LocalContact;
    }

    public void setLocalContact(String localContact) {
        LocalContact = localContact;
    }

    public int getDenStudent() {return DenStudent;
    }

    public void setDenStudent(int denStudent) {
        DenStudent = denStudent;
    }

    public String getStartSemester() {
        return StartSemester;
    }

    public void setStartSemester(String startSemester) {
        StartSemester = startSemester;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getDeptId() {
        return DeptId;
    }

    public void setDeptId(int deptId) {
        DeptId = deptId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
