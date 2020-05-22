package com.ezcoding.facility.module.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.facility.module.message.bean.model.MessageReadRecord;
import com.ezcoding.facility.module.message.dao.MessageReadRecordMapper;
import com.ezcoding.facility.module.message.service.IMessageReadRecordService;
import org.springframework.stereotype.Service;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-20 14:24
 */
@Service
public class MessageReadRecordServiceImpl extends ServiceImpl<MessageReadRecordMapper, MessageReadRecord> implements IMessageReadRecordService {

}
