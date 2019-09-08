package jodd.forum.model;

import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbId;
import jodd.db.oom.meta.DbTable;

import java.util.Date;

@DbTable
public class Info {
    @DbId
    private Integer iid;
    @DbColumn
    private String requestUrl;
    @DbColumn
    private String contextPath;
    @DbColumn
    private String remoteAddr;
    @DbColumn
    private Date access_time;

    public Info() {
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public Date getAccess_time() {
        return access_time;
    }

    public void setAccess_time(Date access_time) {
        this.access_time = access_time;
    }

    @Override
    public String toString() {
        return "Info{" +
                "iid=" + iid +
                ", requestUrl='" + requestUrl + '\'' +
                ", contextPath='" + contextPath + '\'' +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", access_time='" + access_time + '\'' +
                '}';
    }
}
