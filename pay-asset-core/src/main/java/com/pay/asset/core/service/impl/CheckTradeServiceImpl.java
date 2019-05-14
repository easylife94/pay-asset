package com.pay.asset.core.service.impl;

import com.pay.asset.client.dto.CheckTradeCreateDTO;
import com.pay.asset.client.model.CheckTradeDO;
import com.pay.asset.core.dao.CheckTradeDao;
import com.pay.asset.core.service.ICheckTradeService;
import com.pay.asset.core.service.ITradeDayService;
import com.pay.common.client.constants.CheckStatusEnum;
import com.pay.common.client.exception.PayException;
import com.pay.common.core.service.IDistributedLockService;
import com.pay.common.core.service.IIdService;
import com.pay.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author chenwei
 * @date 2019/4/24 11:11
 */
@Slf4j
@Service
public class CheckTradeServiceImpl implements ICheckTradeService {

    private static final String ASYNC_CHECK_TRADE_LOCK = "asyncCheckTradeLock";

    private final CheckTradeDao checkTradeDao;
    private final IDistributedLockService distributedLockService;
    private final IIdService idService;
    private final ITradeDayService tradeDayService;

    public CheckTradeServiceImpl(CheckTradeDao checkTradeDao, IDistributedLockService distributedLockService, IIdService idService, ITradeDayService tradeDayService) {
        this.checkTradeDao = checkTradeDao;
        this.distributedLockService = distributedLockService;
        this.idService = idService;
        this.tradeDayService = tradeDayService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void asyncRunCheckTradeTask(Long count) {
        try {
            distributedLockService.lock(ASYNC_CHECK_TRADE_LOCK);
            log.info("发送结算交易异步消息");
            //todo 发送结算交易异步消息
            //1.先获取待结算记录
            //2.已发送结算异步消息更新状态为结算中
            List<CheckTradeDO> checkTradeDOs = checkTradeDao.selectWaitCheck(count);
            for(CheckTradeDO c : checkTradeDOs){

            }
            int i = checkTradeDao.updateChecking(checkTradeDOs);

            if (i != checkTradeDOs.size()) {
                throw new PayException("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            distributedLockService.unlock(ASYNC_CHECK_TRADE_LOCK);
        }
    }

    @Override
    public void createCheckTrade(CheckTradeCreateDTO checkTradeCreateDTO) {
        CheckTradeDO checkTradeDO = new CheckTradeDO(idService.generateId());
        checkTradeDO.setSysOrderNumber(checkTradeCreateDTO.getSysOrderNumber());
        checkTradeDO.setTradeAmount(checkTradeCreateDTO.getTradeAmount());
        checkTradeDO.setCheckStatus(CheckStatusEnum.WAIT.name());
        checkTradeDO.setCheckMethod(checkTradeCreateDTO.getCheckMethod().name());
        Date tradeDate = checkTradeCreateDTO.getTradeDate();
        Date checkDate;
        switch (checkTradeCreateDTO.getCheckDay()) {
            case D0:
                checkDate = DateUtils.checkDateD0(tradeDate, checkTradeCreateDTO.getCheckTimeHour(),
                        checkTradeCreateDTO.getCheckTimeMin(), 0);
                break;
            case D1:
                checkDate = DateUtils.checkDateD1(tradeDate, checkTradeCreateDTO.getCheckTimeHour(),
                        checkTradeCreateDTO.getCheckTimeMin(), 0);
                break;
            case T0:
                if (tradeDayService.isTradeDay(tradeDate)) {
                    checkDate = DateUtils.checkDateD0(tradeDate, checkTradeCreateDTO.getCheckTimeHour(),
                            checkTradeCreateDTO.getCheckTimeMin(), 0);
                } else {
                    checkDate = DateUtils.checkDateD0(tradeDayService.nextTradeDay(tradeDate), checkTradeCreateDTO.getCheckTimeHour(),
                            checkTradeCreateDTO.getCheckTimeMin(), 0);
                }
                break;
            case T1:
                checkDate = DateUtils.checkDateD0(tradeDayService.nextTradeDay(tradeDate), checkTradeCreateDTO.getCheckTimeHour(),
                        checkTradeCreateDTO.getCheckTimeMin(), 0);
                break;
            default:
                //跳过
                throw new PayException("不支持结算日枚举类型：" + checkTradeCreateDTO.getCheckDay());
        }
        checkTradeDO.setCheckDate(checkDate);

        checkTradeDao.insert(checkTradeDO);
    }
}
