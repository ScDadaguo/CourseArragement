package com.lyk.coursearrange.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author yui
 * @date 2022/8/30 19:50
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasePo {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Timestamp dateCreate;

    private Timestamp dateUpdate;

    private Boolean isDeleted;
}
