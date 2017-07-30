package org.zqu.yiban.schedule.util;

import com.google.gson.*;
import org.zqu.yiban.schedule.model.json.AuthorizeBaseResponse;
import org.zqu.yiban.schedule.model.json.AuthorizeFailResponse;
import org.zqu.yiban.schedule.model.json.AuthorizeSuccResponse;

import java.lang.reflect.Type;

/**
 * Created by zqh on 2017/7/18.
 */
public class  AuthorizelnterceptorDeserializer implements JsonDeserializer<AuthorizeBaseResponse> {
    @Override
    public AuthorizeBaseResponse deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.has("visit_oauth")) {
            JsonElement visitOauth = jsonObject.get("visit_oauth");
            if (visitOauth instanceof JsonObject) {
                AuthorizeSuccResponse date = new AuthorizeSuccResponse();
                date.setVisit_time(jsonObject.get("visit_time").getAsInt());
                AuthorizeSuccResponse.VisitUserBean userBean = new AuthorizeSuccResponse.VisitUserBean();

                userBean.setUserid(jsonObject.get("visit_user").getAsJsonObject().get("userid").getAsString());
                userBean.setUsername(jsonObject.get("visit_user").getAsJsonObject().get("username").getAsString());
                userBean.setUsernick(jsonObject.get("visit_user").getAsJsonObject().get("usernick").getAsString());
                userBean.setUsersex(jsonObject.get("visit_user").getAsJsonObject().get("usersex").getAsString());
                date.setVisit_user(userBean);
                AuthorizeSuccResponse.VisitOauthBean oauthBean = new AuthorizeSuccResponse.VisitOauthBean();
                oauthBean.setAccess_token(jsonObject.get("visit_oauth").getAsJsonObject().get("access_token").getAsString());
                oauthBean.setToken_expires(jsonObject.get("visit_oauth").getAsJsonObject().get("token_expires").getAsString());
                date.setVisit_oauth(oauthBean);
                return date;

            } else {
                AuthorizeFailResponse date = new AuthorizeFailResponse();
                date.setVisit_time(jsonObject.get("visit_time").getAsInt());
                AuthorizeFailResponse.VisitUserBean userBean = new AuthorizeFailResponse.VisitUserBean();
                userBean.setUserid(jsonObject.get("visit_user").getAsJsonObject().get("userid").getAsString());
                date.setVisit_user(userBean);
                date.setVisit_oauth(jsonObject.get("visit_oauth").getAsBoolean());
                return date;
            }
        }
        return null;
    }
}
