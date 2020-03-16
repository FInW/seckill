package com.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String nickname;

    private String password;

    private String salt;

    private Long phone;

    private String head;

    private Date registerDate;

    private Date lastLoginDate;

    private Integer loginCount;

    public User() {
    }

}
