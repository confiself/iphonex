package iphonex;

import okhttp3.*;

import java.io.IOException;

public class IPhoneX {
    private String cookie;
    private String cellNum;
    private String cellNumEnc;
    private String verifyCode;
    private ReqManager reqManager;
    private final int id;
    private OkHttpClient okHttpClient;
    public void setCookie(String cookie){
        this.cookie = cookie;
    }
    public String getCookie(){
        return cookie;
    }
    public void setCellNum(String cellNum){
        this.cellNum = cellNum;
    }
    public String getCellNum(){
        return cellNum;}
    public void setVerifyCode(String verifyCode){
        this.verifyCode = verifyCode;
    }
    public String getVerifyCode(){
        return verifyCode;
    }
    public void setCellNumEnc(String verifyCode){
        this.verifyCode = verifyCode;
    }
    public String getCellNumEnc(){
        return cellNumEnc;
    }
    public IPhoneX(ReqManager reqManager, int id, String cellNum, String cellNumEnc){
        this.reqManager = reqManager;
        this.id = id;
        this.cellNum = cellNum;
        this.cellNumEnc = cellNumEnc;
        okHttpClient = new OkHttpClient();
    }

    public void sendMsg(){
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String verifyCode = response.body().string();
                reqManager.sendMsg(id, String.valueOf(id), "发送验证码成功");
            }

            public void onFailure(Call call, IOException e) {
                reqManager.login(id, e.toString(), "发送验证码失败");

            }
        });
    }
    public void login(){
        System.out.println(id + " verifyCode:" + verifyCode);
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String cookie = response.body().string();
                reqManager.login(id, String.valueOf(id), "登录成功");
            }

            public void onFailure(Call call, IOException e) {
                reqManager.login(id, e.toString(), "登录失败");

            }
        });
    }
    public void buy(){
        System.out.println(id + " cookie: " + verifyCode);
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                reqManager.buy(id, str, "购买成功");
            }

            public void onFailure(Call call, IOException e) {

                reqManager.buy(id, e.toString(), "购买失败");
            }
        });
    }


}
