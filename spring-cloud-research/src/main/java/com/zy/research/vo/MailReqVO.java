package com.zy.research.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class MailReqVO implements Serializable {
    private static final long serialVersionUID = 5665036389536512090L;
    /**
     * 发件人
     */
    @NotEmpty(message = "发件人 不能为空")
    private String from;
    /**
     * 收件人
     */
    @NotEmpty(message = "收件人 不能为空")
    private List<String> toList;
    /**
     * 暗送人
     */
    private List<String> bccList;
    /**
     * 抄送人
     */
    private List<String> ccList;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String text;
    /**
     * 邮件附件
     */
    private List<String> attachmentFilePaths;
}
