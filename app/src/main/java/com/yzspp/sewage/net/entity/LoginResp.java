package com.yzspp.sewage.net.entity;

import com.yzspp.sewage.net.base.ResponseBean;

import java.io.Serializable;

/**
 * Created by 鼠夏目 on 2019/4/25.
 *
 * @See
 * @Description
 */
public class LoginResp extends ResponseBean {

    /**
     * result : true
     * currentUser : {"userinfoId":0,"userName":"张三","userCode":"345601","userPassword":"","updataTime":"Apr 12, 2019 11:06:15 AM","userRight0":1,"userRight1":0,"userRight2":0,"userRight3":0,"userinfo0":"管理员","userinfo1":"处长","userinfo2":"","userinfo3":"","userinfo4":"","userinfo5":"","userinfo6":"","userinfo7":""}
     */

    private boolean result;
    private CurrentUserBean currentUser;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public CurrentUserBean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUserBean currentUser) {
        this.currentUser = currentUser;
    }

    public static class CurrentUserBean implements Serializable{
        /**
         * userinfoId : 0
         * userName : 张三
         * userCode : 345601
         * userPassword :
         * updataTime : Apr 12, 2019 11:06:15 AM
         * userRight0 : 1
         * userRight1 : 0
         * userRight2 : 0
         * userRight3 : 0
         * userinfo0 : 管理员
         * userinfo1 : 处长
         * userinfo2 :
         * userinfo3 :
         * userinfo4 :
         * userinfo5 :
         * userinfo6 :
         * userinfo7 :
         */

        private int userinfoId;
        private String userName;
        private String userCode;
        private String userPassword;
        private String updataTime;
        private int userRight0;
        private int userRight1;
        private int userRight2;
        private int userRight3;
        private String userinfo0;
        private String userinfo1;
        private String userinfo2;
        private String userinfo3;
        private String userinfo4;
        private String userinfo5;
        private String userinfo6;
        private String userinfo7;

        public int getUserinfoId() {
            return userinfoId;
        }

        public void setUserinfoId(int userinfoId) {
            this.userinfoId = userinfoId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUpdataTime() {
            return updataTime;
        }

        public void setUpdataTime(String updataTime) {
            this.updataTime = updataTime;
        }

        public int getUserRight0() {
            return userRight0;
        }

        public void setUserRight0(int userRight0) {
            this.userRight0 = userRight0;
        }

        public int getUserRight1() {
            return userRight1;
        }

        public void setUserRight1(int userRight1) {
            this.userRight1 = userRight1;
        }

        public int getUserRight2() {
            return userRight2;
        }

        public void setUserRight2(int userRight2) {
            this.userRight2 = userRight2;
        }

        public int getUserRight3() {
            return userRight3;
        }

        public void setUserRight3(int userRight3) {
            this.userRight3 = userRight3;
        }

        public String getUserinfo0() {
            return userinfo0;
        }

        public void setUserinfo0(String userinfo0) {
            this.userinfo0 = userinfo0;
        }

        public String getUserinfo1() {
            return userinfo1;
        }

        public void setUserinfo1(String userinfo1) {
            this.userinfo1 = userinfo1;
        }

        public String getUserinfo2() {
            return userinfo2;
        }

        public void setUserinfo2(String userinfo2) {
            this.userinfo2 = userinfo2;
        }

        public String getUserinfo3() {
            return userinfo3;
        }

        public void setUserinfo3(String userinfo3) {
            this.userinfo3 = userinfo3;
        }

        public String getUserinfo4() {
            return userinfo4;
        }

        public void setUserinfo4(String userinfo4) {
            this.userinfo4 = userinfo4;
        }

        public String getUserinfo5() {
            return userinfo5;
        }

        public void setUserinfo5(String userinfo5) {
            this.userinfo5 = userinfo5;
        }

        public String getUserinfo6() {
            return userinfo6;
        }

        public void setUserinfo6(String userinfo6) {
            this.userinfo6 = userinfo6;
        }

        public String getUserinfo7() {
            return userinfo7;
        }

        public void setUserinfo7(String userinfo7) {
            this.userinfo7 = userinfo7;
        }
    }
}
