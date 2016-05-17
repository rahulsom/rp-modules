package com.github.rahulsom.rpconfig;

import java.util.Date;

/**
 * Reads Git Properties
 *
 * @author Rahul Somasunderam
 */
@SuppressWarnings("unused")
public class GitProperties {
    @SuppressWarnings("WeakerAccess")
    static class Commit {
        private String id;
        private String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    private String branch;
    private Commit commit;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }
}
