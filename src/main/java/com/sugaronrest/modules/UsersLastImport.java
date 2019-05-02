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
* A class which represents the users_last_import table.
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


@Module(name = "", tablename = "users_last_import")
@JsonRootName(value = "users_last_import")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersLastImport {
    public String getId() {
        return id;
    }

    public void setId(String value) {
        id = value;
    }
    public String getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(String value) {
        assignedUserId = value;
    }
    public String getImportModule() {
        return importModule;
    }

    public void setImportModule(String value) {
        importModule = value;
    }
    public String getBeanType() {
        return beanType;
    }

    public void setBeanType(String value) {
        beanType = value;
    }
    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String value) {
        beanId = value;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer value) {
        deleted = value;
    }

    @JsonProperty("id")
    private String id;
  
    @JsonProperty("assigned_user_id")
    private String assignedUserId;
  
    @JsonProperty("import_module")
    private String importModule;
  
    @JsonProperty("bean_type")
    private String beanType;
  
    @JsonProperty("bean_id")
    private String beanId;
  
    @JsonProperty("deleted")
    private Integer deleted;
  
}
