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
* A class which represents the prospect_lists_prospects table.
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

@Module(name = "", tablename = "prospect_lists_prospects")
@JsonRootName(value = "prospect_lists_prospects")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProspectListsProspects {
    public String getId() {
        return id;
    }

    public void setId(String value) {
        id = value;
    }
    public String getProspectListId() {
        return prospectListId;
    }

    public void setProspectListId(String value) {
        prospectListId = value;
    }
    public String getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(String value) {
        relatedId = value;
    }
    public String getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(String value) {
        relatedType = value;
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
  
    @JsonProperty("prospect_list_id")
    private String prospectListId;
  
    @JsonProperty("related_id")
    private String relatedId;
  
    @JsonProperty("related_type")
    private String relatedType;
  
    @JsonProperty("date_modified")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date dateModified;
  
    @JsonProperty("deleted")
    private Integer deleted;
  
}
