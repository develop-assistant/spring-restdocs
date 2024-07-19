package cn.idea360.openapi.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户
 *
 * @author cuishiying
 */
@Data
public class User implements Serializable {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 角色列表
     */
    private List<Role> roles;

}
