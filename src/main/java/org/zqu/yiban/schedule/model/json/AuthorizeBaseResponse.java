package org.zqu.yiban.schedule.model.json;

/**
 * Created by zqh on 2017/7/18.
 */
public class AuthorizeBaseResponse {

    /**
     * visit_time : 访问unix时间戳
     * visit_user : {"userid":"易班用户ID"}
     * visit_oauth : false
     */

    private int visit_time;

    public int getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(int visit_time) {
        this.visit_time = visit_time;
    }




}
