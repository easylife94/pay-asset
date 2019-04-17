package com.pay.asset.web.config;

import com.pay.common.client.constants.ZookeeperCommonNamespace;
import com.pay.common.core.service.IDistributedLockService;
import com.pay.common.core.service.IIdService;
import com.pay.common.core.service.impl.IdServiceImpl;
import com.pay.common.core.service.impl.ZookeeperDistributedLockServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通用服务配置
 *
 * @author chenwei
 * @date 2019/4/17 15:15
 */
@Slf4j
@Configuration
public class CommonServiceConfig {

    /**
     * zookeeper分布式锁服务
     *
     * @param curatorFrameworkBuilder curator建造者对象
     * @return 返回分布式锁服务
     */
    @Bean
    public IDistributedLockService distributedLockService(CuratorFrameworkFactory.Builder curatorFrameworkBuilder) {
        return new ZookeeperDistributedLockServiceImpl(curatorFrameworkBuilder, ZookeeperCommonNamespace.LOCKS);
    }

    /**
     * 全局唯一Id服务
     *
     * @param curatorFrameworkBuilder curator建造者对象
     * @return 返回全局唯一Id服务
     */
    @Bean
    public IIdService idService(CuratorFrameworkFactory.Builder curatorFrameworkBuilder) {
        return new IdServiceImpl(curatorFrameworkBuilder);
    }
}
