package org.shopin.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.shopin.validation.EmailConstraint;

public class OrderJson {

    @NotNull
    @EmailConstraint
    private String email = "";

    @NotNull
    @Pattern(regexp = "(\\{\"ncl\":\"[a-zA-Z ]{5,50}\",\"addrl\":\"[A-Z0-9a-z.:,\\)\\( ]{10,200}\",\"tell\":\"[0-9]{10}\",\"judetl\":\"[a-zA-Z0-9\\- ]{3,20}\",\"orasl\":\"[a-zA-Z0-9\\- ]{2,20}\",\"codl\":\"[0-9]{6}\",\"infol\":\"[A-Z0-9a-z.:,\\)\\( ]{0,100}\"(,\"ncf\":\"[a-zA-Z ]{5,50}\",\"addrf\":\"[A-Z0-9a-z.:,\\)\\( ]{10,200}\",\"telf\":\"[0-9]{10}\",\"judetf\":\"[a-zA-Z0-9\\- ]{3,20}\",\"orasf\":\"[a-zA-Z0-9\\- ]{2,20}\",\"codf\":\"[0-9]{6}\",\"infof\":\"[A-Z0-9a-z.:,\\)\\( ]{0,100}\")?\\})")
    private String addr = "";

    @NotNull
    @Pattern(regexp = "(\\{(\"SCI-[0-9]{1,3}\":\\{\"quantity\":[1-5]{1},\"id\":\"SCI-[0-9]{1,3}\",\"price\":([1-9]{1}[0-9]{0,3}|[1-9]{1}[0-9]{0,3}\\.{1}[0-9]{1,2}),\"msku\":\"[A-Za-z0-9]{10}\",\"msize\":\"[A-Za-z0-9\\-]{3,10}\"\\}[,]{0,1}){1,100}\\})")
    private String order = "";

    @NotNull
    @Pattern(regexp = "(1|2)")
    private String addrnr = "";

    @NotNull
    @Pattern(regexp = "(\\{(\"[A-Za-z0-9]{10}\":\\{\"eimage\":\"/(b|f|c)/(im|in|ac)/\",\"ename\":\"[A-Za-z0-9,:\\-_ ]{3,100}\"\\}[,]{0,1}){1,100}\\})")
    private String namesimg = "";

    public String getNamesimg() {
        return namesimg;
    }

    public void setNamesimg(String namesimg) {
        this.namesimg = namesimg;
    }

    public String getAddrnr() {
        return addrnr;
    }

    public void setAddrnr(String addrnr) {
        this.addrnr = addrnr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
