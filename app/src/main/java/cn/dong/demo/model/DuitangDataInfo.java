package cn.dong.demo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author dong on 15/6/10.
 */
public class DuitangDataInfo {
    @SerializedName("blogs")
    public List<DuitangBlog> duitangBlogs;
}
