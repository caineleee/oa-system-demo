package com.lee.oa.controller;

import com.lee.oa.pojo.JobLevel;
import com.lee.oa.pojo.Response;
import com.lee.oa.service.IJobLevelService;
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
 * @ClassName JobLevelController
 * @Description 职称控制器
 * @Author lihongliang
 * @Date 2025/10/29 09:05
 * @Version 1.0
 */
@RestController
@Tag(name = "职称控制器", description = "用于提供职称接口")
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {

    @Autowired
    private IJobLevelService jobLevelService;

    @Operation(summary = "获取所有职称")
    @GetMapping("/")
    public Response getAllJobLevels() {
        return Response.success("success", jobLevelService.list());
    }

    @Operation(summary = "添加职称")
    @PostMapping("/")
    public Response addJobLevel(@RequestBody JobLevel jobLevel) {
        jobLevel.setCreateDate(LocalDateTime.now());
        if (!jobLevelService.save(jobLevel)) {
            return Response.error("添加失败");
        }
        return Response.success("添加成功");
    }

    @Operation(summary = "更新职称")
    @PutMapping("/")
    public Response updateJobLevel(@RequestBody JobLevel jobLevel) {
        if (!jobLevelService.updateById(jobLevel)) {
            return Response.error("更新失败");
        }
        return Response.success("更新成功");
    }

    @Operation(summary = "删除职称")
    @DeleteMapping("/{id}")
    public Response deleteJobLevel(@PathVariable Integer id) {
        if (!jobLevelService.removeById(id)) {
            return Response.error("删除失败");
        }
        return Response.success("删除成功");
    }

    @Operation(summary = "批量删除职称")
    @DeleteMapping("/")
    public Response deleteJobLevels(@RequestParam List<Integer> ids) {
        if (!jobLevelService.removeByIds(ids)) {
            return Response.error("删除失败");
        }
        return Response.success("删除成功");
    }
}
