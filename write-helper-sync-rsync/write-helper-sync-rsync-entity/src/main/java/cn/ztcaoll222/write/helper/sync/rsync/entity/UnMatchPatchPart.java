package cn.ztcaoll222.write.helper.sync.rsync.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 不匹配的 chunk
 *
 * @author ztcaoll222
 * Create time: 2019/9/1 14:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UnMatchPatchPart extends PatchPart {
    private static final long serialVersionUID = 8436825211697348104L;

    private String content;

    public UnMatchPatchPart(String content) {
        super();
        this.content = content;
    }
}
