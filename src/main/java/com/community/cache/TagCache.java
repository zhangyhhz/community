package com.community.cache;

import com.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> get() {
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c++", "c", "golang", "objective-c", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDTOS.add(program);
        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台架构");
        framework.setTags(Arrays.asList("larave", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDTOS.add(framework);
        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDTOS.add(server);
        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql,memcached", "sqlserver", "postgresql", "sqlite"));
        tagDTOS.add(db);
        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "vim", "sublime-text", "eclipse", "maven", "ide", "svn", "visual-studio", "xcode intellij-idea", "visual-studio-code", "atom emacs", "textmate", "hg"));
        tagDTOS.add(tool);
        return tagDTOS;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, "，");
        List<TagDTO> tagDTOS = get();
        // 把二维数组两层内容循环出来
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
