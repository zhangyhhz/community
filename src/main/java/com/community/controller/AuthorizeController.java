package com.community.controller;

import com.community.dto.AccessTokenDto;
import com.community.dto.GithubUser;
import com.community.model.User;
import com.community.provider.GithubProvider;
import com.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/*  Github授权登陆的实现
 *   功能代码：getAccessToken，getUser 见 GithubProvider
 *
 *   帮助：https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
 * */

@Controller
@Slf4j
public class AuthorizeController {

    // 把写好的实例加载
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName((githubUser.getName()));
            user.setLogin(githubUser.getLogin());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            // 登陆成功，写cookie和session     session存入数据库
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";    //  重定向到index页面
        } else {
            // 登陆失败
            log.error("callback get github error,{}", githubUser);
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
