package com.lee.oa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName DockerComposeInitializer
 * @Description Docker 容器启动检查配置类
 * @Author lihongliang
 * @Date 2025/10/9 18:18
 * @Version 1.0
 */
@Component
public class DockerComposeInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DockerComposeInitializer.class);

    // 需要检查的容器名称
    private static final List<String> REQUIRED_CONTAINERS = Arrays.asList(
            "mysql-server",
            "redis-master",
            "redis-slave1",
            "redis-slave2",
            "redis-sentinel1",
            "redis-sentinel2",
            "redis-sentinel3"
    );

    // docker-compose.yaml 文件路径（相对于项目根目录）
    private static final String DOCKER_COMPOSE_PATH = "oa-server/src/main/resources/docker-compose.yaml";

    // Docker 网络名称
    private static final String DOCKER_NETWORK_NAME = "redis-net";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("检查 Docker 网络状态...");

        // 检查并创建 Docker 网络
        if (!isDockerNetworkExists(DOCKER_NETWORK_NAME)) {
            logger.info("Docker 网络 {} 不存在，正在创建...", DOCKER_NETWORK_NAME);
            createDockerNetwork(DOCKER_NETWORK_NAME);
        } else {
            logger.info("Docker 网络 {} 已存在", DOCKER_NETWORK_NAME);
        }

        logger.info("检查 Docker 容器状态...");

        if (!areAllContainersRunning()) {
            logger.info("检测到部分或全部容器未运行，正在启动 Docker 容器...");
            startDockerContainers();
            logger.info("所有 Docker 容器启动完成");
        } else {
            logger.info("所有必需的 Docker 容器已在运行中");
        }
    }

    /**
     * 检查 Docker 网络是否存在
     */
    private boolean isDockerNetworkExists(String networkName) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("docker", "network", "ls", "--format", "{{.Name}}", "--filter", "name=" + networkName);

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equals(networkName)) {
                int exitCode = process.waitFor();
                return exitCode == 0;
            }
        }

        int exitCode = process.waitFor();
        return exitCode == 0 && false;
    }

    /**
     * 创建 Docker 网络
     */
    private void createDockerNetwork(String networkName) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("docker", "network", "create", networkName);

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            logger.info("Docker 网络创建输出: {}", line);
        }

        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            logger.error("Docker 网络创建错误: {}", errorLine);
        }

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            logger.info("Docker 网络 {} 创建成功", networkName);
        } else {
            logger.error("Docker 网络 {} 创建失败，退出码: {}", networkName, exitCode);
        }
    }

    /**
     * 检查所有必需的容器是否都在运行
     */
    private boolean areAllContainersRunning() {
        try {
            for (String containerName : REQUIRED_CONTAINERS) {
                if (!isContainerRunning(containerName)) {
                    logger.warn("容器 {} 未运行", containerName);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("检查容器状态时出错", e);
            return false;
        }
    }

    /**
     * 检查单个容器是否在运行
     */
    private boolean isContainerRunning(String containerName) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("docker", "ps", "--format", "{{.Names}}", "--filter", "name=" + containerName);

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(containerName)) {
                int exitCode = process.waitFor();
                return exitCode == 0;
            }
        }

        int exitCode = process.waitFor();
        return exitCode == 0 && false; // 如果没有找到匹配的容器名，则返回false
    }

    /**
     * 启动 Docker 容器
     */
    private void startDockerContainers() {
        try {
            // 检查 docker-compose 文件是否存在
            File composeFile = new File(DOCKER_COMPOSE_PATH);
            if (!composeFile.exists()) {
                logger.error("docker-compose 文件不存在: {}", composeFile.getAbsolutePath());
                return;
            }

            // 构建启动命令
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("docker-compose", "-f", DOCKER_COMPOSE_PATH, "up", "-d");
            processBuilder.directory(new File(".")); // 设置工作目录为项目根目录

            logger.info("执行命令: docker-compose -f {} up -d", DOCKER_COMPOSE_PATH);

            // 启动进程
            Process process = processBuilder.start();

            // 读取输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info("Docker Compose 输出: {}", line);
            }

            // 等待进程完成
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logger.info("Docker 容器启动成功");

                // 等待容器完全启动
                waitForContainersReady();
            } else {
                // 读取错误输出
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    logger.error("Docker Compose 错误: {}", errorLine);
                }
                logger.error("Docker 容器启动失败，退出码: {}", exitCode);
            }
        } catch (Exception e) {
            logger.error("启动 Docker 容器时出错", e);
        }
    }

    /**
     * 等待容器准备就绪
     */
    private void waitForContainersReady() throws InterruptedException {
        logger.info("等待容器准备就绪...");

        // 等待一段时间让容器启动
        Thread.sleep(10000); // 等待10秒

        // 可以添加更精确的健康检查逻辑
        // 例如检查 MySQL 是否可以连接，Redis 是否可以访问等
        logger.info("容器启动等待完成");
    }
}
