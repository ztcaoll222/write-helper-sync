package cn.ztcaoll222.write.helper.sync.rsync.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Patch内容抽象类, 因为有2种不同的内容
 * 对于已经存在的内容, 只需要记录index
 * 不存在的内容，需要存数据
 *
 * @author ztcaoll222
 * Create time: 2019/9/1 14:09
 */
@Data
public class PatchPart implements Serializable {
    private static final long serialVersionUID = 3446058285070424451L;
}
