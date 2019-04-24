package com.pay.asset.core.service;

/**
 * @author chenwei
 * @date 2019/4/24 11:10
 */
public interface ICheckTradeService {

    /**
     * 结算交易定时任务执行方法
     * 发送异步消息处理结算，避免方法执行时间过长导致定时任务异常
     * 因为是定时任务多个worker会同时执行需要加锁顺序执行保证线程安全
     */
    void asyncRunCheckTradeTask(Long count);


}
