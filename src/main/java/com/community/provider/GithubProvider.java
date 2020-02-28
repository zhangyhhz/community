package com.community.provider;

import com.alibaba.fastjson.JSON;
import com.community.dto.AccessTokenDto;
import com.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
*   getAccessToken：
*       向github发起POST请求，接收github返回的code并解析其中的access_token
*   getUser：
*       通过github返回的access_token访问API获取用户信息
* */

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        String access_tokenUrl = "https://github.com/login/oauth/access_token?client_id=" + accessTokenDto.getClient_id() + "&client_secert=" + accessTokenDto.getClient_secret() + "&code=" + accessTokenDto.getCode() + "&redirect_uri=" + accessTokenDto.getRedirect_uri() + "&state=" + accessTokenDto.getState();
        Request request = new Request.Builder().url(access_tokenUrl).post(body).build();
        try(Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/user?access_token=" + accessToken).build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        }catch (IOException e){
        }
        return null;
    }
}
