package com.panic_buying.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
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
