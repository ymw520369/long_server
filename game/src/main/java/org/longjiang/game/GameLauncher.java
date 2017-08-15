package org.longjiang.game;

import org.alan.mars.cluster.ClusterSystem;
import org.alan.mars.config.NodeConfig;
import org.alan.mars.kafka.producer.LogProducer;
import org.alan.mars.monitor.FileMonitor;
import org.alan.mars.rpc.server.RpcServer;
import org.alan.mars.uid.UidCacheManager;
import org.alan.mars.uid.UidDao;
import org.alan.mars.uid.impl.UidDaoImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@ComponentScan({"org.longjiang", "org.alan"})
@Order(value = 1)
public class GameLauncher implements CommandLineRunner {

    public GameLauncher() {
    }

    public static void main(String[] args) {
        SpringApplication.run(GameLauncher.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

    @Bean
    public UidDao createUidDao() {
        return new UidDaoImpl();
    }

    @Bean
    public FileMonitor createFileMonitor() {
        return new FileMonitor();
    }

    @Bean
    public UidCacheManager uidCacheManager(ClusterSystem clusterSystem) {
        return new UidCacheManager(clusterSystem, true);
    }

    @Bean
    public NodeConfig nodeConfig() {
        return new NodeConfig();
    }

    @Bean
    public LogProducer logProducer() {
        return new LogProducer();
    }

    @Bean
    public RpcServer rpcServer(NodeConfig nodeConfig) {
        return new RpcServer(nodeConfig);
    }

}
