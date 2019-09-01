package cn.ztcaoll222.write.helper.sync.rsync.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ztcaoll222
 * Create time: 2019/9/1 15:17
 */
@Data
public class Chunk implements Serializable {
    private static final long serialVersionUID = 3831940183433577880L;

    /**
     * 块号
     */
    private long index;
    /**
     * 弱校验和
     */
    private long weakCheckSum;
    /**
     * 强校验
     */
    private String strongCheckSum;
}
