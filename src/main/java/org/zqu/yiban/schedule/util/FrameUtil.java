package org.zqu.yiban.schedule.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.zqu.yiban.schedule.model.json.AuthorizeBaseResponse;
import org.zqu.yiban.schedule.model.json.AuthorizeFailResponse;
import org.zqu.yiban.schedule.model.json.AuthorizeSuccResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * Created by zqh on 2017/7/18.
 */
public class FrameUtil {
    final String IAPP_AUTHURL = "https://openapi.yiban.cn/oauth/authorize";
    final String TEXT_ENCODING = "UTF-8";

    final String PARAM_VERIFY = "verify4j_request";
    final String PARAM_ORIGIN = "verify_request";

    private HttpServletRequest request;
    private HttpServletResponse response;

    String appid;
    String secrt;
    String url;

    String query;
    String result;

    boolean isAuthed;
    String token;
    String userid;
    String username;
    int timestamp;
    int formheight;

    /**
     * 构造函数
     * <p>
     * 除了配置的AppID、AppSecret和回调URL外，
     * 还需要传入HttpServlet中的Request与Response对象
     *
     * @param request
     * @param response
     * @param backurl  AppID值，用作AES解密的IV
     * @param appid    AppSecret值，用作AES解密的KEY
     * @param appid    回调的URL地址
     */
    public FrameUtil(HttpServletRequest request, HttpServletResponse response, String appid, String secret, String backurl) {
        this.request = request;
        this.response = response;
        this.secrt = secret;
        this.appid = appid;
        this.url = backurl;
        this.token = null;
        this.isAuthed = false;
        this.timestamp = 0;
        this.formheight = 60;
    }

    /**
     * 授权验证操作
     */
    public boolean perform()
            throws Exception {
        if (appid.length() == 16) {
            query = request.getParameter(PARAM_ORIGIN);
        } else {
            query = request.getParameter(PARAM_VERIFY);
        }
        result = AESDecoder.dec(query, secrt, appid).trim();
        if (result == null) {
            throw new IllegalAccessException("AES decrypt ERROR");
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        AuthorizelnterceptorDeserializer deserializer = new AuthorizelnterceptorDeserializer();
        gsonBuilder.registerTypeAdapter(AuthorizeBaseResponse.class, deserializer);
        Gson customGson = gsonBuilder.create();
        AuthorizeBaseResponse response = customGson.fromJson(result, AuthorizeBaseResponse.class);
        if (response instanceof AuthorizeFailResponse) {   //如果response是AuthorizeFailResponse的一个实例
            isAuthed = ((AuthorizeFailResponse) response).isVisit_oauth();
        } else if (response instanceof AuthorizeSuccResponse) {       //如果response是AuthorizeSuccResponse的一个实例
            token = ((AuthorizeSuccResponse) response).getVisit_oauth().getAccess_token();
            userid = ((AuthorizeSuccResponse) response).getVisit_user().getUserid();
            username = ((AuthorizeSuccResponse) response).getVisit_user().getUsername();
        } else {
            throw new IllegalAccessException("Json format ERROR");
        }
        timestamp = response.getVisit_time();
        if (token != null && token.length() >= 8) {
            isAuthed = true;
        }
        if (!isAuthed) {
            if (request.getMethod().toUpperCase().equals("GET")) {
                iappForAuth();
            } else {
                jumpForAuth();
            }
            return false;
        }
        return true;
    }

    /**
     * 设置授权弹窗高度
     */
    public void setHeight(int height) {
        this.formheight = height;
    }

    /**
     * 加密的验证字符串
     */
    public String getQuery() {
        return query;
    }

    /**
     * 解密后的验证字符串（JSON格式）
     */
    public String getResult() {
        return result;
    }

    /**
     * 用户ID值
     */
    public String getUserId() {
        return userid;
    }

    /**
     * 用户名称
     */
    public String getUserName() {
        return username;
    }

    /**
     * ACCESS TOKEN 值
     */
    public String getAccessToken() {
        return token;
    }

    /**
     * 验证时间
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * 重定向到授权认证页面
     * <p>
     * 在perform()方法中若未通过授权，
     * 自动调用此方法重定向到授权服务器进行授权
     */
    public void jumpForAuth()
            throws Exception {
        this.jumpForAuth(formheight);
    }

    public void iappForAuth() {
        try {
            StringBuffer buffer = new StringBuffer(IAPP_AUTHURL);
            buffer.append("?client_id=");
            buffer.append(appid);
            buffer.append("&redirect_uri=");
            buffer.append(URLEncoder.encode(url, TEXT_ENCODING));
            buffer.append("&display=html");
            response.sendRedirect(buffer.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 重定向到授权认证页面
     * <p>
     * 在perform()方法中若未通过授权，
     * 自动调用此方法重定向到授权服务器进行授权
     *
     * @param height 授权窗口高度
     */
    public void jumpForAuth(int height)
            throws Exception {
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
        out.println("<title>{YBLANG::WEB_APP_TITLE}</title>");
        out.println("<script src=\"http://f.yiban.cn/global/js/jquery1.11.0.min.js\" type=\"text/javascript\"></script>");
        out.println("<script src=\"http://f.yiban.cn/apps/js/authiframe.js\" type=\"text/javascript\"></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<script type=\"text/javascript\">");
        out.println("$(function() {");
        out.println("(function(){");
        out.println("App.AuthDialog.show({");
        out.printf("client_id: \"%s\",\n", this.appid);
        out.printf("redirect_uri: \"%s\",\n", this.url);
        out.printf("height: %d,\n", height);
        out.println("scope:  \"\"");
        out.println("});");
        out.println("})();");
        out.println("});");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * 站内应用页面自适应代码
     * <p>
     * 在页面中嵌入此代码可以进行页面自适应
     * 返回字符串，可以选择是否使用此代码进行自适应
     */
    public String adaptive() {
        return adaptive(1024, 768, "c_iframe");
    }

    /**
     * 站内应用页面自适应代码
     * <p>
     * 在页面中嵌入此代码可以进行页面自适应
     * 返回字符串，可以选择是否使用此代码进行自适应
     *
     * @param width    宽
     * @param height    高
     * @param framename Frame名称
     * @return String 页面自适应代码
     */
    public String adaptive(int width, int height, String framename) {
        String source = "<iframe id=\"" + framename + "\" height=\"0\" width=\"0\" src=\"\" style=\"display:none\"></iframe>\n";
        source += "<script type=\"text/javascript\">\n";
        source += "function setSize(w, h) {\n";
        source += "	var c_iframe = document.getElementById(\"" + framename + "\");\n";
        source += "		c_iframe.src = \"http://f.yiban.cn/apps.html#\" + w + \"|\" + h;\n";
        source += "	}\n";
        source += "setSize(" + String.valueOf(width) + ", " + String.valueOf(height) + ");\n";
        source += "</script>\n";
        return source;
    }


}
