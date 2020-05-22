package com.ezcoding.facility.module.message.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.facility.module.message.bean.model.PersonalMessage;
import com.ezcoding.facility.module.message.bean.model.PersonalMessageSearchCondition;
import com.ezcoding.facility.module.message.dao.PersonalMessageMapper;
import com.ezcoding.facility.module.message.service.IPersonalMessageService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-23 15:42
 */
@Service
public class PersonalMessageServiceImpl extends ServiceImpl<PersonalMessageMapper, PersonalMessage> implements IService<PersonalMessage>, IPersonalMessageService {

    @Override
    public Integer countUnread(PersonalMessageSearchCondition personalMessageSearchCondition) {
        Integer count = baseMapper.selectUnreadCountByCondition(personalMessageSearchCondition);
        return count == null ? 0 : count;
    }

    @Override
    public List<PersonalMessage> send(String content, String sender, List<String> receivers) {
        if (CollectionUtils.isEmpty(receivers)) {
            return null;
        }
        Date date = new Date();
        List<PersonalMessage> collect = receivers.stream().map(r -> {
            PersonalMessage personalMessage = new PersonalMessage();
            personalMessage.setContent(content);
            personalMessage.setSender(sender);
            personalMessage.setReceiver(r);
            personalMessage.setSendTime(date);
            return personalMessage;
        }).collect(Collectors.toList());
        saveBatch(collect);
        return collect;
    }

    @Override
    public Page<PersonalMessage> fetch(PersonalMessageSearchCondition personalMessageSearchCondition, Page<PersonalMessage> page) {
        List<PersonalMessage> personalMessages = baseMapper.selectListByCondition(personalMessageSearchCondition, page);
        page.setRecords(personalMessages);
        return page;
    }

    @Override
    public void read(String receiver, Collection<Long> ids) {
        baseMapper.updateReadStatus(receiver, ids);
    }

    @Override
    public void readAll(String receiver) {
        PersonalMessage personalMessage = new PersonalMessage();
        personalMessage.setAlreadyRead(true);

        PersonalMessage tmp = new PersonalMessage();
        tmp.setReceiver(receiver);
        tmp.setAlreadyRead(false);
        baseMapper.update(personalMessage, Wrappers.query(tmp));
    }

    @Override
    public void delete(String receiver, Collection<Long> ids) {
        baseMapper.deleteBatch(receiver, ids);
    }

    @Override
    public void deleteAll(String receiver) {
        PersonalMessage personalMessage = new PersonalMessage();
        personalMessage.setReceiver(receiver);
        baseMapper.delete(Wrappers.query(personalMessage));
    }

}
