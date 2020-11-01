package com.zy.service;

import com.zy.vo.MailReqVO;

public interface IMailService {
    void sendSimpleMail(MailReqVO mailReqVO) throws Exception;
    void sendSimpleMailWithAttachment(MailReqVO mailReqVO) throws Exception;
}
