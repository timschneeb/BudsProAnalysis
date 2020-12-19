package androidx.work.impl.model;

import androidx.work.Data;
import java.util.List;

public interface WorkProgressDao {
    void delete(String str);

    void deleteAll();

    Data getProgressForWorkSpecId(String str);

    List<Data> getProgressForWorkSpecIds(List<String> list);

    void insert(WorkProgress workProgress);
}
