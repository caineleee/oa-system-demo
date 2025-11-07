package com.lee.oa.controller;

import com.lee.oa.pojo.Response;
import com.lee.oa.pojo.User;
import com.lee.oa.service.IRoleService;
import com.lee.oa.service.IUserService;
import com.lee.oa.util.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName UserController
 * @Description 后端系统操作员控制器(只在数据库添加,不会在界面添加,所以没有添加API)
 * @Author lihongliang
 * @Date 2025/11/5 18:48
 * @Version 1.0
 */
@RestController
@RequestMapping("/system/admin")
@Tag(name = "操作员控制器", description = "操作员控制器")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Operation(summary = "获取操作员列表")
    @GetMapping("/")
    public Response getAdminList(@RequestParam String keywords) {
        List<User> admins = userService.getAdmins(keywords)
                .stream()
                .map(user -> user.setPassword(null))
                .collect(Collectors.toList());
        return Response.success("success", admins);
    }

    @Operation(summary = "更新操作员信息")
    @PutMapping("/")
    public Response updateAdmin(@RequestBody User user) {
        if (user.getId() == null) {
            return Response.error("用户ID不能为空");
        }
        User record = userService.getById(user.getId());
        BeanUtil.copyPropertiesWithNonNull(user, record);
        boolean result = userService.updateById(record);
        return result ? Response.success("更新成功") : Response.error("更新失败");
    }

    @Operation(summary = "删除操作员")
    @DeleteMapping("/{id}")
    public Response deleteAdmin(@PathVariable Integer id) {
        boolean result = userService.removeById(id);
        return result ? Response.success("删除成功") : Response.error("删除失败");
    }


    @Operation(summary = "获取所有角色")
    @GetMapping("/roles")
    public Response getAllRoles() {
        return Response.success("success", roleService.list());
    }

    @Operation(summary = "更新操作员角色")
    @PutMapping("/roles")
    public Response updateAdminRoles(@RequestParam Integer userId, @RequestParam List<Integer> roleIds) {
        String result = userService.AddUserRoles(userId, roleIds);
        return Response.success(result);
    }

}
