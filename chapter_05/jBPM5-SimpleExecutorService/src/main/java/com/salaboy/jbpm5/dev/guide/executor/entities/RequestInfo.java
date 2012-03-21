/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.salaboy.jbpm5.dev.guide.executor.entities;

import java.util.Arrays;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author salaboy
 */
@Entity(name = "RequestInfo")
public class RequestInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Enumerated(EnumType.STRING)
    private STATUS status;
    private String commandName;
    private String message;
    //Business Key for callback
    private String key;
    @Lob
    private byte[] requestData;
    @Lob
    private byte[] responseData;
    @OneToOne(optional = true, cascade= CascadeType.ALL)
    @JoinColumn(
    	name="ERROR_ID", unique=true, nullable=true, updatable=false)
    private ErrorInfo errorInfo;

    public RequestInfo() {
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo error) {
        this.errorInfo = error;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public byte[] getRequestData() {
        return requestData;
    }

    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    public byte[] getResponseData() {
        return responseData;
    }

    public void setResponseData(byte[] responseData) {
        this.responseData = responseData;
    }

    @Override
    public String toString() {
        return "RequestInfo{" + "id=" + id + ", time=" + time + ", status=" + status + ", commandName=" + commandName + ", message=" + message + ", key=" + key + ", requestData=" + requestData + ", responseData=" + responseData + ", error=" + errorInfo + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RequestInfo other = (RequestInfo) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.time != other.time && (this.time == null || !this.time.equals(other.time))) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if ((this.commandName == null) ? (other.commandName != null) : !this.commandName.equals(other.commandName)) {
            return false;
        }
        if ((this.message == null) ? (other.message != null) : !this.message.equals(other.message)) {
            return false;
        }
        if ((this.key == null) ? (other.key != null) : !this.key.equals(other.key)) {
            return false;
        }
        if (!Arrays.equals(this.requestData, other.requestData)) {
            return false;
        }
        if (!Arrays.equals(this.responseData, other.responseData)) {
            return false;
        }
        if (this.errorInfo != other.errorInfo && (this.errorInfo == null || !this.errorInfo.equals(other.errorInfo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 79 * hash + (this.time != null ? this.time.hashCode() : 0);
        hash = 79 * hash + (this.status != null ? this.status.hashCode() : 0);
        hash = 79 * hash + (this.commandName != null ? this.commandName.hashCode() : 0);
        hash = 79 * hash + (this.message != null ? this.message.hashCode() : 0);
        hash = 79 * hash + (this.key != null ? this.key.hashCode() : 0);
        hash = 79 * hash + Arrays.hashCode(this.requestData);
        hash = 79 * hash + Arrays.hashCode(this.responseData);
        hash = 79 * hash + (this.errorInfo != null ? this.errorInfo.hashCode() : 0);
        return hash;
    }
    
    
}
