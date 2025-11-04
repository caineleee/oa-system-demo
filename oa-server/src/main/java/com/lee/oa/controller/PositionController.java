package com.lee.oa.controller;

import com.lee.oa.pojo.Position;
import com.lee.oa.pojo.Response;
import com.lee.oa.service.IPositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName PositionController
 * @Description 前端职位控制器
 * @Author lihongliang
 * @Date 2025/10/25 19:12
 * @Version 1.0
 */
@RestController
@Tag(name = "职位控制器", description = "职位管理")
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @Operation(summary = "获取所有职位信息")
    @GetMapping("/")
    public Response getAllPositions() {
        return Response.success("success", positionService.list());
    }

    @Operation(summary = "添加职位信息")
    @PostMapping("/")
    public Response addPosition(@RequestBody Position position) {
        position.setCreateDate(LocalDateTime.now());
        if (positionService.save(position)) {
            return Response.success("添加职位成功");
        }
        return Response.error("添加职位失败");
    }

    @Operation(summary = "更新职位信息")
    @PutMapping("/")
    public Response updatePosition(@RequestBody Position position) {
        if (positionService.updateById(position)) {
            return Response.success("更新职位成功");
        }
        return Response.error("更新职位失败");
    }

    @Operation(summary = "删除职位信息")
    @DeleteMapping("/{id}")
    public Response deletePosition(@PathVariable Integer id) {
        if (positionService.removeById(id)) {
            return Response.success("删除职位成功");
        }
        return Response.error("删除职位失败");
    }

    @Operation(summary = "批量删除职位信息")
    @DeleteMapping("/")
    public Response deletePositions(@RequestParam List<Integer> ids) {
        if (positionService.removeByIds(ids)) {
            return Response.success("批量删除职位成功");
        }
        return Response.error("批量删除职位失败");
    }

}
