package cn.idea360.openapi.controller;

import cn.idea360.openapi.model.User;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 *
 * @author cuishiying
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * 添加帐号
     *
     * @param user 帐号信息
     * @return 帐号ID
     */
    @PostMapping
    public Integer addUser(@RequestBody User user) {
        user.setId(1);
        return user.getId();
    }

    /**
     * 获取帐号信息
     *
     * @param userId 帐号ID
     * @param userName 用户名
     * @return 帐号基础信息
     */
    @GetMapping("/{userId}")
    public User getUser(@PathVariable Integer userId, @RequestParam String userName) {
        return new User();
    }

}
