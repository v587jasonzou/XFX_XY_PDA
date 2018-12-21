package jx.yunda.com.terminal.modules.workshoptaskdispatch.model;

import java.io.Serializable;

/**
 * Created by hed on 2018/6/11.
 */

public class OrgForWorkshopTaskDispatch implements Serializable {

    String id;
    String orgname;
    String orgseq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getOrgseq() {
        return orgseq;
    }

    public void setOrgseq(String orgseq) {
        this.orgseq = orgseq;
    }
}
