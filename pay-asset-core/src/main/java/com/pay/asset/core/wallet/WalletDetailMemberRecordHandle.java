package com.pay.asset.core.wallet;

import com.pay.asset.client.constants.WalletRecordTradeTypeEnum;
import com.pay.asset.client.model.AbstractWalletDetailBaseDO;
import com.pay.asset.client.model.WalletDetailMemberDO;
import com.pay.asset.core.dao.WalletDetailMemberDao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.pay.asset.client.constants.SpringBeanNamespaces.WALLET_DETAIL_HANDLE;

/**
 * @author chenwei
 * @date 2019/4/18 15:16
 */
@Component(WALLET_DETAIL_HANDLE + "MEMBER")
public class WalletDetailMemberRecordHandle implements IWalletDetailRecordHandle {

    @Override
    public void record(AbstractWalletDetailBaseDO walletDetailDO, WalletRecordTradeTypeEnum recordTradeType, BigDecimal amount) {
        WalletDetailMemberDO walletDetailMemberDO = (WalletDetailMemberDO) walletDetailDO;
        BigDecimal incomeRecharge = walletDetailMemberDO.getIncomeRecharge();
        BigDecimal expenditureWithdraw = walletDetailMemberDO.getExpenditureWithdraw();
        BigDecimal expenditureTradeFee = walletDetailMemberDO.getExpenditureTradeFee();
        BigDecimal expenditureWithdrawFee = walletDetailMemberDO.getExpenditureWithdrawFee();
        switch (recordTradeType){
            case MEMBER_RECHARGE:
                incomeRecharge = incomeRecharge.add(amount);
                break;
            case TRADE_CHANNEL_FEE:
            case TRADE_SERVICE_FEE:
                expenditureTradeFee = expenditureTradeFee.add(amount);
                break;
            case WITHDRAW_TAX:
            case WITHDRAW_SERVICE_FEE:
                expenditureWithdrawFee = expenditureWithdrawFee.add(amount);
                break;
            case WITHDRAW_UNFROZEN:
                expenditureWithdraw = expenditureWithdraw.add(amount);
            default:
                //跳过
        }
        walletDetailMemberDO.setIncomeRecharge(incomeRecharge);
        walletDetailMemberDO.setExpenditureWithdraw(expenditureWithdraw);
        walletDetailMemberDO.setExpenditureTradeFee(expenditureTradeFee);
        walletDetailMemberDO.setExpenditureWithdrawFee(expenditureWithdrawFee);

    }
}
