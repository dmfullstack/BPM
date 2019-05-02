/**
* <auto-generated />
* This file was generated by a StringTemplate 4 template.
* Don't change it directly as your change would get overwritten. Instead, make changes
* to the .stg file (i.e. the StringTemplate 4 template file) and save it to regenerate this file.
*
* For more infor on StringTemplate 4 template please go to -
* https://github.com/antlr/antlrcs
*
* @author  Kola Oyewumi
* @version 1.0.0
* @since   2017-01-03
*
* A class which represents the calls_users table.
*/

package com.sugaronrest.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sugaronrest.restapicalls.Module;
import com.sugaronrest.restapicalls.CustomDateDeserializer;
import com.sugaronrest.restapicalls.CustomDateSerializer;

import java.util.Date;

@Module(name = "", tablename = "calls_users")
@JsonRootName(value = "calls_users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallsUsers {
    public String getId() {
        return id;
    }

    public void setId(String value) {
        id = value;
    }
    public String getCallId() {
        return callId;
    }

    public void setCallId(String value) {
        callId = value;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String value) {
        userId = value;
    }
    public String getRequired() {
        return required;
    }

    public void setRequired(String value) {
        required = value;
    }
    public String getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(String value) {
        acceptStatus = value;
    }
    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date value) {
        dateModified = value;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer value) {
        deleted = value;
    }

    @JsonProperty("id")
    private String id;
  
    @JsonProperty("call_id")
    private String callId;
  
    @JsonProperty("user_id")
    private String userId;
  
    @JsonProperty("required")
    private String required;
  
    @JsonProperty("accept_status")
    private String acceptStatus;
  
    @JsonProperty("date_modified")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date dateModified;
  
    @JsonProperty("deleted")
    private Integer deleted;
  
}
