package com.pay.asset.web.core.service;

import com.pay.asset.client.constants.*;
import com.pay.asset.client.dto.WalletRecordDTO;
import com.pay.asset.client.dto.WalletSubRecordDTO;
import com.pay.asset.core.service.IWalletService;
import com.pay.asset.web.PayAssetWebApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenwei
 * @date 2019/4/18 11:12
 */
public class WalletServiceTests extends PayAssetWebApplicationTests {

    @Autowired
    private IWalletService walletService;

    /**
     * 交易
     */
    @Test
    public void memberWalletTradeRecordTest() {
        WalletRecordDTO walletRecordDTO = new WalletRecordDTO();
        walletRecordDTO.setOrderNumber("TEST-" + System.currentTimeMillis());
        walletRecordDTO.setOrderStatus(WalletRecordOrderStatusEnum.PAYMENT);
        walletRecordDTO.setOrderType(WalletRecordOrderTypeEnum.TRADE_ORDER);
        walletRecordDTO.setOwnNumber("A00001");
        walletRecordDTO.setOwnRole(WalletOwnRoleEnum.MEMBER);
        List<WalletSubRecordDTO> recordList = new ArrayList<>();
        WalletSubRecordDTO record1 = new WalletSubRecordDTO();
        record1.setAmount(new BigDecimal("2.00"));
        record1.setPaymentType(WalletRecordPaymentTypeEnum.OUT);
        record1.setTradeType(WalletRecordTradeTypeEnum.TRADE_SERVICE_FEE);

        WalletSubRecordDTO record2 = new WalletSubRecordDTO();
        record2.setAmount(new BigDecimal("2.00"));
        record2.setPaymentType(WalletRecordPaymentTypeEnum.OUT);
        record2.setTradeType(WalletRecordTradeTypeEnum.TRADE_CHANNEL_FEE);
        recordList.add(record1);
        recordList.add(record2);
        walletRecordDTO.setSubRecords(recordList);
        walletService.walletRecord(walletRecordDTO);
    }

    /**
     * 充值
     */
    @Test
    public void memberWalletRechargeRecordTest() {
        WalletRecordDTO walletRecordDTO = new WalletRecordDTO();
        walletRecordDTO.setOrderNumber("TEST-" + System.currentTimeMillis());
        walletRecordDTO.setOrderStatus(WalletRecordOrderStatusEnum.PAYMENT);
        walletRecordDTO.setOrderType(WalletRecordOrderTypeEnum.RECHARGE_ORDER);
        walletRecordDTO.setOwnNumber("A00001");
        walletRecordDTO.setOwnRole(WalletOwnRoleEnum.MEMBER);
        List<WalletSubRecordDTO> recordList = new ArrayList<>();
        WalletSubRecordDTO record = new WalletSubRecordDTO();
        record.setAmount(new BigDecimal("1500"));
        record.setPaymentType(WalletRecordPaymentTypeEnum.IN);
        record.setTradeType(WalletRecordTradeTypeEnum.MEMBER_RECHARGE);
        recordList.add(record);
        walletRecordDTO.setSubRecords(recordList);
        walletService.walletRecord(walletRecordDTO);
    }

    /**
     * 提现下单
     */
    @Test
    public void memberWalletWithdrawOrderRecordTest() {
        WalletRecordDTO walletRecordDTO = new WalletRecordDTO();
        walletRecordDTO.setOrderNumber("TEST-" + System.currentTimeMillis());
        walletRecordDTO.setOrderStatus(WalletRecordOrderStatusEnum.ORDER);
        walletRecordDTO.setOrderType(WalletRecordOrderTypeEnum.WITHDRAW_ORDER);
        walletRecordDTO.setOwnNumber("A00001");
        walletRecordDTO.setOwnRole(WalletOwnRoleEnum.MEMBER);
        List<WalletSubRecordDTO> recordList = new ArrayList<>();

        //提现
        WalletSubRecordDTO record1 = new WalletSubRecordDTO();
        record1.setAmount(new BigDecimal("250"));
        record1.setPaymentType(WalletRecordPaymentTypeEnum.FROZEN);
        record1.setTradeType(WalletRecordTradeTypeEnum.WITHDRAW_FROZEN);
        recordList.add(record1);
        walletRecordDTO.setSubRecords(recordList);
        walletService.walletRecord(walletRecordDTO);
    }

    /**
     * 提现成功
     */
    @Test
    public void memberWalletWithdrawPaymentRecordTest() {
        WalletRecordDTO walletRecordDTO = new WalletRecordDTO();
        walletRecordDTO.setOrderNumber("TEST-" + System.currentTimeMillis());
        walletRecordDTO.setOrderStatus(WalletRecordOrderStatusEnum.PAYMENT);
        walletRecordDTO.setOrderType(WalletRecordOrderTypeEnum.WITHDRAW_ORDER);
        walletRecordDTO.setOwnNumber("A00001");
        walletRecordDTO.setOwnRole(WalletOwnRoleEnum.MEMBER);
        List<WalletSubRecordDTO> recordList = new ArrayList<>();

        //提现
        WalletSubRecordDTO record1 = new WalletSubRecordDTO();
        record1.setAmount(new BigDecimal("250"));
        record1.setPaymentType(WalletRecordPaymentTypeEnum.OUT_UNFROZEN);
        record1.setTradeType(WalletRecordTradeTypeEnum.WITHDRAW_UNFROZEN);

        WalletSubRecordDTO record2 = new WalletSubRecordDTO();
        record2.setAmount(new BigDecimal("10"));
        record2.setPaymentType(WalletRecordPaymentTypeEnum.OUT);
        record2.setTradeType(WalletRecordTradeTypeEnum.WITHDRAW_SERVICE_FEE);

        WalletSubRecordDTO record3 = new WalletSubRecordDTO();
        record3.setAmount(new BigDecimal("10"));
        record3.setPaymentType(WalletRecordPaymentTypeEnum.OUT);
        record3.setTradeType(WalletRecordTradeTypeEnum.WITHDRAW_TAX);
        recordList.add(record1);
        recordList.add(record2);
        recordList.add(record3);
        walletRecordDTO.setSubRecords(recordList);
        walletService.walletRecord(walletRecordDTO);
    }


    /**
     * 结算分润
     */
    @Test
    public void agentWalletCheckRecordTest() {
        WalletRecordDTO walletRecordDTO = new WalletRecordDTO();
        walletRecordDTO.setOrderNumber("TEST-" + System.currentTimeMillis());
        walletRecordDTO.setOrderStatus(WalletRecordOrderStatusEnum.CHECK);
        walletRecordDTO.setOrderType(WalletRecordOrderTypeEnum.TRADE_ORDER);
        walletRecordDTO.setOwnNumber("A10000");
        walletRecordDTO.setOwnRole(WalletOwnRoleEnum.AGENT);
        List<WalletSubRecordDTO> recordList = new ArrayList<>();

        //提现
        WalletSubRecordDTO record1 = new WalletSubRecordDTO();
        record1.setAmount(new BigDecimal("2"));
        record1.setPaymentType(WalletRecordPaymentTypeEnum.IN);
        record1.setTradeType(WalletRecordTradeTypeEnum.TRADE_SERVICE_PROFIT);

        WalletSubRecordDTO record2 = new WalletSubRecordDTO();
        record2.setAmount(new BigDecimal("2"));
        record2.setPaymentType(WalletRecordPaymentTypeEnum.IN);
        record2.setTradeType(WalletRecordTradeTypeEnum.TRADE_CHANNEL_PROFIT);

        recordList.add(record1);
        recordList.add(record2);
        walletRecordDTO.setSubRecords(recordList);
        walletService.walletRecord(walletRecordDTO);
    }

    /**
     * 代理商提现下单
     */
    @Test
    public void agentWalletWithdrawOrderRecordTest() {
        WalletRecordDTO walletRecordDTO = new WalletRecordDTO();
        walletRecordDTO.setOrderNumber("TEST-" + System.currentTimeMillis());
        walletRecordDTO.setOrderStatus(WalletRecordOrderStatusEnum.ORDER);
        walletRecordDTO.setOrderType(WalletRecordOrderTypeEnum.WITHDRAW_ORDER);
        walletRecordDTO.setOwnNumber("A10000");
        walletRecordDTO.setOwnRole(WalletOwnRoleEnum.AGENT);
        List<WalletSubRecordDTO> recordList = new ArrayList<>();

        //提现
        WalletSubRecordDTO record1 = new WalletSubRecordDTO();
        record1.setAmount(new BigDecimal("2"));
        record1.setPaymentType(WalletRecordPaymentTypeEnum.FROZEN);
        record1.setTradeType(WalletRecordTradeTypeEnum.WITHDRAW_FROZEN);
        recordList.add(record1);
        walletRecordDTO.setSubRecords(recordList);
        walletService.walletRecord(walletRecordDTO);
    }

    /**
     * 代理商提现成功
     */
    @Test
    public void agentWalletWithdrawPaymentRecordTest() {
        WalletRecordDTO walletRecordDTO = new WalletRecordDTO();
        walletRecordDTO.setOrderNumber("TEST-" + System.currentTimeMillis());
        walletRecordDTO.setOrderStatus(WalletRecordOrderStatusEnum.PAYMENT);
        walletRecordDTO.setOrderType(WalletRecordOrderTypeEnum.WITHDRAW_ORDER);
        walletRecordDTO.setOwnNumber("A10000");
        walletRecordDTO.setOwnRole(WalletOwnRoleEnum.AGENT);
        List<WalletSubRecordDTO> recordList = new ArrayList<>();

        //提现
        WalletSubRecordDTO record1 = new WalletSubRecordDTO();
        record1.setAmount(new BigDecimal("2"));
        record1.setPaymentType(WalletRecordPaymentTypeEnum.OUT_UNFROZEN);
        record1.setTradeType(WalletRecordTradeTypeEnum.WITHDRAW_UNFROZEN);

        WalletSubRecordDTO record2 = new WalletSubRecordDTO();
        record2.setAmount(new BigDecimal("0.01"));
        record2.setPaymentType(WalletRecordPaymentTypeEnum.OUT);
        record2.setTradeType(WalletRecordTradeTypeEnum.WITHDRAW_SERVICE_FEE);

        WalletSubRecordDTO record3 = new WalletSubRecordDTO();
        record3.setAmount(new BigDecimal("0.01"));
        record3.setPaymentType(WalletRecordPaymentTypeEnum.OUT);
        record3.setTradeType(WalletRecordTradeTypeEnum.WITHDRAW_TAX);
        recordList.add(record1);
        recordList.add(record2);
        recordList.add(record3);
        walletRecordDTO.setSubRecords(recordList);
        walletService.walletRecord(walletRecordDTO);
    }

    /**
     * 交易去重
     */
    @Test
    public void uniqueWalletTradeRecordTest() {
        String orderNumber = "TEST-" + System.currentTimeMillis();
        for (int i = 0; i < 2;i++ ) {
            WalletRecordDTO walletRecordDTO = new WalletRecordDTO();
            walletRecordDTO.setOrderNumber(orderNumber);
            walletRecordDTO.setOrderStatus(WalletRecordOrderStatusEnum.PAYMENT);
            walletRecordDTO.setOrderType(WalletRecordOrderTypeEnum.TRADE_ORDER);
            walletRecordDTO.setOwnNumber("A00001");
            walletRecordDTO.setOwnRole(WalletOwnRoleEnum.MEMBER);
            List<WalletSubRecordDTO> recordList = new ArrayList<>();
            WalletSubRecordDTO record1 = new WalletSubRecordDTO();
            record1.setAmount(new BigDecimal("2.00"));
            record1.setPaymentType(WalletRecordPaymentTypeEnum.OUT);
            record1.setTradeType(WalletRecordTradeTypeEnum.TRADE_SERVICE_FEE);

            WalletSubRecordDTO record2 = new WalletSubRecordDTO();
            record2.setAmount(new BigDecimal("2.00"));
            record2.setPaymentType(WalletRecordPaymentTypeEnum.OUT);
            record2.setTradeType(WalletRecordTradeTypeEnum.TRADE_CHANNEL_FEE);
            recordList.add(record1);
            recordList.add(record2);
            walletRecordDTO.setSubRecords(recordList);
            walletService.walletRecord(walletRecordDTO);
        }
    }

}
