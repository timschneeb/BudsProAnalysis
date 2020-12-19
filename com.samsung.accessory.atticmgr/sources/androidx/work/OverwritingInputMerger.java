package androidx.work;

import androidx.work.Data;
import java.util.HashMap;
import java.util.List;

public final class OverwritingInputMerger extends InputMerger {
    @Override // androidx.work.InputMerger
    public Data merge(List<Data> list) {
        Data.Builder builder = new Data.Builder();
        HashMap hashMap = new HashMap();
        for (Data data : list) {
            hashMap.putAll(data.getKeyValueMap());
        }
        builder.putAll(hashMap);
        return builder.build();
    }
}
