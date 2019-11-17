package com.ezcoding.account.module.user.core.verification;

import com.ezcoding.account.module.user.bean.model.CheckVerificationInfo;
import com.ezcoding.account.module.user.bean.model.VerificationInfo;
import com.ezcoding.account.module.user.constant.UserConstants;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.common.foundation.core.tools.uuid.IUUIDProducer;
import com.ezcoding.common.foundation.core.tools.uuid.OriginalUUIDProducer;
import com.ezcoding.common.foundation.core.tools.verification.IVerificationCodeGenerator;
import com.ezcoding.common.foundation.core.tools.verification.VerificationCode;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-11 13:59
 */
public class RedisVerificationServiceImpl implements IVerificationService {

    private static final String PREFIX = "user:verification:";

    private RedisTemplate<String, VerificationInfo> redisTemplate;
    private IVerificationCodeGenerator verificationCodeGenerator;
    private IUUIDProducer receiptProducer = OriginalUUIDProducer.getInstance();

    @Override
    public VerificationInfo generate(String tag) {
        VerificationInfo verificationInfo = new VerificationInfo();
        //生成随机回执
        String receipt = receiptProducer.produce();
        verificationInfo.setReceipt(receipt);
        verificationInfo.setTag(tag);
        try {
            VerificationCode verificationCode = verificationCodeGenerator.generate();
            verificationInfo.setVerificationCode(verificationCode);
            redisTemplate.opsForValue().set(PREFIX + receipt, verificationInfo, UserConstants.VERIFICATION_CODE_TIMEOUT, TimeUnit.SECONDS);
        } catch (IOException e) {
            throw UserExceptionConstants.USER_VERIFICATION_CODE_GENERATE_ERROR.instance().cause(e).build();
        }
        return verificationInfo;
    }

    @Override
    public boolean check(CheckVerificationInfo checkVerificationInfo) {
        VerificationInfo verificationInfo = this.get(checkVerificationInfo.getReceipt());

        if (verificationInfo == null) {
            return false;
        }
        if (verificationInfo.getTag() == null) {
            return verificationInfo.getVerificationCode().verifyIgnoreCase(checkVerificationInfo.getVerificationCode());
        }
        return Objects.equals(checkVerificationInfo.getTag(), verificationInfo.getTag()) &&
                verificationInfo.getVerificationCode().verifyIgnoreCase(checkVerificationInfo.getVerificationCode());
    }

    @Override
    public VerificationInfo get(String receipt) {
        return redisTemplate.opsForValue().get(PREFIX + receipt);
    }

    @Override
    public VerificationInfo delete(String receipt) {
        VerificationInfo verificationInfo = this.get(receipt);
        redisTemplate.delete(PREFIX + receipt);
        return verificationInfo;
    }

    public RedisTemplate<String, VerificationInfo> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, VerificationInfo> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public IVerificationCodeGenerator getVerificationCodeGenerator() {
        return verificationCodeGenerator;
    }

    public void setVerificationCodeGenerator(IVerificationCodeGenerator verificationCodeGenerator) {
        this.verificationCodeGenerator = verificationCodeGenerator;
    }

    public IUUIDProducer getReceiptProducer() {
        return receiptProducer;
    }

    public void setReceiptProducer(IUUIDProducer receiptProducer) {
        this.receiptProducer = receiptProducer;
    }
}
