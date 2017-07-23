package org.zqu.yiban.schedule.model.json;

/**
 * Created by zqh on 2017/7/18.
 */
public class AuthorizeSuccResponse extends AuthorizeBaseResponse{


    /**
     * visit_user : {"userid":"易班用户ID","username":"易班用户名","usernick":"易班用户昵称","usersex":"易班用户性别"}
     * visit_oauth : {"access_token":"授权凭证","token_expires":"有效unix时间戳"}
     */

    private VisitUserBean visit_user;
    private VisitOauthBean visit_oauth;

    public VisitUserBean getVisit_user() {
        return visit_user;
    }

    public void setVisit_user(VisitUserBean visit_user) {
        this.visit_user = visit_user;
    }

    public VisitOauthBean getVisit_oauth() {
        return visit_oauth;
    }

    public void setVisit_oauth(VisitOauthBean visit_oauth) {
        this.visit_oauth = visit_oauth;
    }

    public static class VisitUserBean {
        /**
         * userid : 易班用户ID
         * username : 易班用户名
         * usernick : 易班用户昵称
         * usersex : 易班用户性别
         */

        private String userid;
        private String username;
        private String usernick;
        private String usersex;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsernick() {
            return usernick;
        }

        public void setUsernick(String usernick) {
            this.usernick = usernick;
        }

        public String getUsersex() {
            return usersex;
        }

        public void setUsersex(String usersex) {
            this.usersex = usersex;
        }
    }

    public static class VisitOauthBean {
        /**
         * access_token : 授权凭证
         * token_expires : 有效unix时间戳
         */

        private String access_token;
        private String token_expires;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getToken_expires() {
            return token_expires;
        }

        public void setToken_expires(String token_expires) {
            this.token_expires = token_expires;
        }
    }
}
