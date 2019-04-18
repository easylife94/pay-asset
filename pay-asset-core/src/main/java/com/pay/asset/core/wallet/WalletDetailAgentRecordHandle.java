package com.pay.asset.core.wallet;

import com.pay.asset.client.constants.WalletRecordTradeTypeEnum;
import com.pay.asset.client.model.AbstractWalletDetailBaseDO;
import com.pay.asset.client.model.WalletDetailAgentDO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.pay.asset.client.constants.SpringBeanNamespaces.WALLET_DETAIL_HANDLE;

/**
 * @author chenwei
 * @date 2019/4/18 14:51
 */
@Component(WALLET_DETAIL_HANDLE + "AGENT")
public class WalletDetailAgentRecordHandle implements IWalletDetailRecordHandle {


    @Override
    public void record(AbstractWalletDetailBaseDO walletDetailDO, WalletRecordTradeTypeEnum recordTradeType, BigDecimal amount) {
        WalletDetailAgentDO walletDetailAgentDO = (WalletDetailAgentDO) walletDetailDO;
        BigDecimal incomeProfit = walletDetailAgentDO.getIncomeProfit();
        BigDecimal expenditureWithdraw = walletDetailAgentDO.getExpenditureWithdraw();
        BigDecimal expenditureWithdrawFee = walletDetailAgentDO.getExpenditureWithdrawFee();
        switch (recordTradeType) {
            case TRADE_CHANNEL_PROFIT:
            case TRADE_SERVICE_PROFIT:
                incomeProfit = incomeProfit.add(amount);
                break;
            case WITHDRAW_UNFROZEN:
                expenditureWithdraw = expenditureWithdraw.add(amount);
                break;
            case WITHDRAW_TAX:
            case WITHDRAW_SERVICE_FEE:
                expenditureWithdrawFee = expenditureWithdrawFee.add(amount);
                break;
            default:
                //跳过
        }
        walletDetailAgentDO.setIncomeProfit(incomeProfit);
        walletDetailAgentDO.setExpenditureWithdraw(expenditureWithdraw);
        walletDetailAgentDO.setExpenditureWithdrawFee(expenditureWithdrawFee);
    }
}
