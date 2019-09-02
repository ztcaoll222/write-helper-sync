package cn.ztcaoll222.write.helper.sync.rsync.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * 匹配的 chunk
 *
 * @author ztcaoll222
 * Create time: 2019/9/1 14:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MatchPatchPart extends PatchPart {
    private static final long serialVersionUID = -4549024071937604120L;

    @Setter(AccessLevel.NONE)
    private long index;

    public MatchPatchPart(long index) {
        this.index = index;
    }
}
