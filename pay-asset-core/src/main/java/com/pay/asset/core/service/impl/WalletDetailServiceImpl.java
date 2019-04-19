package com.pay.asset.core.service.impl;

import com.pay.asset.client.constants.WalletOwnRoleEnum;
import com.pay.asset.client.model.AbstractWalletDetailBaseDO;
import com.pay.asset.client.model.WalletDetailAgentDO;
import com.pay.asset.client.model.WalletDetailMemberDO;
import com.pay.asset.core.dao.WalletDetailAgentDao;
import com.pay.asset.core.dao.WalletDetailMemberDao;
import com.pay.asset.core.service.IWalletDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenwei
 * @date 2019/4/19 11:52
 */
@Service
public class WalletDetailServiceImpl implements IWalletDetailService {

    private final WalletDetailAgentDao walletDetailAgentDao;
    private final WalletDetailMemberDao walletDetailMemberDao;

    @Autowired
    public WalletDetailServiceImpl(WalletDetailAgentDao walletDetailAgentDao, WalletDetailMemberDao walletDetailMemberDao) {
        this.walletDetailAgentDao = walletDetailAgentDao;
        this.walletDetailMemberDao = walletDetailMemberDao;
    }

    @Override
    public AbstractWalletDetailBaseDO selectByWalletId(WalletOwnRoleEnum ownRole, Long walletId) {
        switch (ownRole) {
            case AGENT:
                return walletDetailAgentDao.selectByWalletId(walletId);
            case MEMBER:
                return walletDetailMemberDao.selectByWalletId(walletId);
            default:
                throw new RuntimeException("不支持钱包角色类型：" + ownRole.name());
        }
    }

    @Override
    public Boolean update(WalletOwnRoleEnum ownRole, AbstractWalletDetailBaseDO walletDetailDO) {
        switch (ownRole) {
            case AGENT:
                return walletDetailAgentDao.updateByPrimaryKeySelective((WalletDetailAgentDO) walletDetailDO) > 0;
            case MEMBER:
                return walletDetailMemberDao.updateByPrimaryKeySelective((WalletDetailMemberDO) walletDetailDO) > 0;
            default:
                throw new RuntimeException("不支持钱包角色类型：" + ownRole.name());
        }
    }
}
