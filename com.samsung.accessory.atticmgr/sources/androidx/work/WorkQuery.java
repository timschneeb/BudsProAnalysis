package androidx.work;

import androidx.work.WorkInfo;
import java.util.ArrayList;
import java.util.List;

public final class WorkQuery {
    private final List<WorkInfo.State> mStates;
    private final List<String> mTags;
    private final List<String> mUniqueWorkNames;

    WorkQuery(Builder builder) {
        this.mUniqueWorkNames = builder.mUniqueWorkNames;
        this.mTags = builder.mTags;
        this.mStates = builder.mStates;
    }

    public List<String> getUniqueWorkNames() {
        return this.mUniqueWorkNames;
    }

    public List<String> getTags() {
        return this.mTags;
    }

    public List<WorkInfo.State> getStates() {
        return this.mStates;
    }

    public static final class Builder {
        List<WorkInfo.State> mStates = new ArrayList();
        List<String> mTags = new ArrayList();
        List<String> mUniqueWorkNames = new ArrayList();

        private Builder() {
        }

        public static Builder fromUniqueWorkNames(List<String> list) {
            Builder builder = new Builder();
            builder.addUniqueWorkNames(list);
            return builder;
        }

        public static Builder fromTags(List<String> list) {
            Builder builder = new Builder();
            builder.addTags(list);
            return builder;
        }

        public static Builder fromStates(List<WorkInfo.State> list) {
            Builder builder = new Builder();
            builder.addStates(list);
            return builder;
        }

        public Builder addUniqueWorkNames(List<String> list) {
            this.mUniqueWorkNames.addAll(list);
            return this;
        }

        public Builder addTags(List<String> list) {
            this.mTags.addAll(list);
            return this;
        }

        public Builder addStates(List<WorkInfo.State> list) {
            this.mStates.addAll(list);
            return this;
        }

        public WorkQuery build() {
            if (!this.mUniqueWorkNames.isEmpty() || !this.mTags.isEmpty() || !this.mStates.isEmpty()) {
                return new WorkQuery(this);
            }
            throw new IllegalArgumentException("Must specify uniqueNames, tags or states when building a WorkQuery");
        }
    }
}
