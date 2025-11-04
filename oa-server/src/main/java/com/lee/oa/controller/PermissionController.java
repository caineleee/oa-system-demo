package com.lee.oa.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.oa.pojo.MenuRole;
import com.lee.oa.pojo.Response;
import com.lee.oa.pojo.Role;
import com.lee.oa.service.IMenuRoleService;
import com.lee.oa.service.IRoleService;
import com.lee.oa.service.IMenuService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


/**
 * @ClassName PermissionController
 * @Description 权限组控制器
 * @Author lihongliang
 * @Date 2025/10/31 18:06
 * @Version 1.0
 */
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IMenuRoleService menuRoleService;

    @Operation(summary = "获取所有角色")
    @GetMapping("/")
    public Response getAllRoles() {
        return Response.success("access", roleService.list());
    }

    @Operation(summary = "添加角色")
    @PostMapping("/")
    public Response addRole(@RequestBody Role role) {
        role.setName(role.getName().toUpperCase(Locale.ROOT));
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleService.save(role) ? Response.success("添加成功") : Response.error("添加失败");
    }

    @Operation(summary = "更新角色")
    @PutMapping("/")
    public Response updateRole(@RequestBody Role role){
        role.setName(role.getName().toUpperCase(Locale.ROOT));
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleService.updateById(role) ? Response.success("更新成功") : Response.error("更新失败");
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public Response deleteRole(@PathVariable String id){
        return roleService.removeById(id) ? Response.success("删除成功") : Response.error("删除失败");
    }

    @Operation(summary = "查询所有菜单")
    @GetMapping("/menus")
    public Response getMenus() {
        return Response.success("access", menuService.getAllMenus());
    }

    @Operation(summary = "根据角色ID获取菜单ID列表")
    @GetMapping("/mid/{rid}")
    public Response getMidByRid(@PathVariable Integer rid) {
        return Response.success("access",
                menuRoleService.list(new QueryWrapper<MenuRole>().eq("role_id", rid))
                        .stream().map(MenuRole::getMenuId).collect(Collectors.toList()));
    }

    @Operation(summary = "更新角色访问菜单权限列表")
    @PutMapping("/update/role/menu")
    public Response updateRoleMenu(@RequestParam Integer roleId,
                                   @RequestParam List<Integer> menuIds) {
        String result = menuRoleService.updateMenuRole(roleId, menuIds);
        if ("更新成功".equals(result)) {
            return Response.success(result);
        } else {
            return Response.error(result);
        }
    }


}
