package com.pay.asset.core.rabbit;

import com.alibaba.fastjson.JSONObject;
import com.pay.asset.client.constants.PayAssetMessageQueueNames;
import com.pay.asset.client.dto.async.CheckTradeMessageDTO;
import com.pay.asset.client.dto.async.TradeStatisticsMessageDTO;
import com.pay.asset.client.dto.async.WalletRecordMessageDTO;
import com.pay.asset.core.service.IWalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息队列接收器
 *
 * @author chenwei
 * @date 2019/1/15 16:34
 */
@Component
@Slf4j
public class RabbitMqReceiver {

    private final IWalletService walletService;

    @Autowired
    public RabbitMqReceiver(IWalletService walletService) {
        this.walletService = walletService;
    }

    @RabbitListener(queues = PayAssetMessageQueueNames.QUEUE_TRADE_STATISTICS)
    public void tradeCreate(TradeStatisticsMessageDTO tradeStatisticsMessageDTO) {
        try {
            log.info("收到交易统计消息：{}", tradeStatisticsMessageDTO);
            //1.商户交易数据统计
            //1.1小时数据统计


            //1.2日数据统计
            //1.3月数据统计
            //1.4年数据统计

        } catch (Exception e) {
            e.printStackTrace();
            log.error("交易创建消息处理异常，消息内容:{}，异常：{}", tradeStatisticsMessageDTO, e.getMessage());
        }
    }

    @RabbitListener(queues = PayAssetMessageQueueNames.QUEUE_WALLET_RECORD)
    public void walletRecord(String content) {
        try {
            log.info("收到钱包记录消息：{}", content);
            WalletRecordMessageDTO walletRecordMessageDTO = JSONObject.toJavaObject(JSONObject.parseObject(content), WalletRecordMessageDTO.class);
            walletService.walletRecord(walletRecordMessageDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("交易钱包记录处理异常，消息内容:{}，异常：{}", content, e.getMessage());
        }
    }

    @RabbitListener(queues = PayAssetMessageQueueNames.QUEUE_CHECK_TRADE_CREATE)
    public void checkTradeCreate(String content) {
        try {
            log.info("收到结算交易创建消息：{}", content);
            //todo 结算交易创建消息
        } catch (Exception e) {
            e.printStackTrace();
            log.error("结算交易创建消息处理异常，消息内容:{}，异常：{}", content, e.getMessage());
        }
    }

    @RabbitListener(queues = PayAssetMessageQueueNames.QUEUE_CHECK_TRADE)
    public void checkTrade(String content) {
        try {
            log.info("收到结算交易消息：{}", content);
            CheckTradeMessageDTO checkTradeMessageDTO = JSONObject.toJavaObject(JSONObject.parseObject(content), CheckTradeMessageDTO.class);
            //todo 处理结算交易消息
        } catch (Exception e) {
            e.printStackTrace();
            log.error("结算交易消息处理异常，消息内容:{}，异常：{}", content, e.getMessage());
        }
    }

}
