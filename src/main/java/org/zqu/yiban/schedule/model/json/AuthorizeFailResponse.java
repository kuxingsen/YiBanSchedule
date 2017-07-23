package org.zqu.yiban.schedule.model.json;

/**
 * Created by zqh on 2017/7/18.
 */
public class AuthorizeFailResponse extends AuthorizeBaseResponse{


    /**
     * visit_user : {"userid":"易班用户ID"}
     * visit_oauth : false
     */

    private VisitUserBean visit_user;
    private boolean visit_oauth;

    public VisitUserBean getVisit_user() {
        return visit_user;
    }

    public void setVisit_user(VisitUserBean visit_user) {
        this.visit_user = visit_user;
    }

    public boolean isVisit_oauth() {
        return visit_oauth;
    }

    public void setVisit_oauth(boolean visit_oauth) {
        this.visit_oauth = visit_oauth;
    }

    public static class VisitUserBean {
        /**
         * userid : 易班用户ID
         */

        private String userid;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
