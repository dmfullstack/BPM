/**
 * SugarsoapPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ithrdevbuild.bpmdemo.crm;

public interface SugarsoapPortType extends java.rmi.Remote {
    public com.ithrdevbuild.bpmdemo.crm.Entry_value login(com.ithrdevbuild.bpmdemo.crm.User_auth user_auth, java.lang.String application_name, com.ithrdevbuild.bpmdemo.crm.Name_value[] name_value_list) throws java.rmi.RemoteException;
    public void logout(java.lang.String session) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.Get_entry_result_version2 get_entry(java.lang.String session, java.lang.String module_name, java.lang.String id, java.lang.String[] select_fields, com.ithrdevbuild.bpmdemo.crm.Link_name_to_fields_array[] link_name_to_fields_array, boolean track_view) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.Get_entry_result_version2 get_entries(java.lang.String session, java.lang.String module_name, java.lang.String[] ids, java.lang.String[] select_fields, com.ithrdevbuild.bpmdemo.crm.Link_name_to_fields_array[] link_name_to_fields_array, boolean track_view) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.Get_entry_list_result_version2 get_entry_list(java.lang.String session, java.lang.String module_name, java.lang.String query, java.lang.String order_by, int offset, java.lang.String[] select_fields, com.ithrdevbuild.bpmdemo.crm.Link_name_to_fields_array[] link_name_to_fields_array, int max_results, int deleted, boolean favorites) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.New_set_relationship_list_result set_relationship(java.lang.String session, java.lang.String module_name, java.lang.String module_id, java.lang.String link_field_name, java.lang.String[] related_ids, com.ithrdevbuild.bpmdemo.crm.Name_value[] name_value_list, int delete) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.New_set_relationship_list_result set_relationships(java.lang.String session, java.lang.String[] module_names, java.lang.String[] module_ids, java.lang.String[] link_field_names, java.lang.String[][] related_ids, com.ithrdevbuild.bpmdemo.crm.Name_value[][] name_value_lists, int[] delete_array) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.Get_entry_result_version2 get_relationships(java.lang.String session, java.lang.String module_name, java.lang.String module_id, java.lang.String link_field_name, java.lang.String related_module_query, java.lang.String[] related_fields, com.ithrdevbuild.bpmdemo.crm.Link_name_to_fields_array[] related_module_link_name_to_fields_array, int deleted, java.lang.String order_by) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.New_set_entry_result set_entry(java.lang.String session, java.lang.String module_name, com.ithrdevbuild.bpmdemo.crm.Name_value[] name_value_list) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.New_set_entries_result set_entries(java.lang.String session, java.lang.String module_name, com.ithrdevbuild.bpmdemo.crm.Name_value[][] name_value_lists) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.Get_server_info_result get_server_info() throws java.rmi.RemoteException;
    public java.lang.String get_user_id(java.lang.String session) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.New_module_fields get_module_fields(java.lang.String session, java.lang.String module_name, java.lang.String[] fields) throws java.rmi.RemoteException;
    public int seamless_login(java.lang.String session) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.New_set_entry_result set_note_attachment(java.lang.String session, com.ithrdevbuild.bpmdemo.crm.New_note_attachment note) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.New_return_note_attachment get_note_attachment(java.lang.String session, java.lang.String id) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.New_set_entry_result set_document_revision(java.lang.String session, com.ithrdevbuild.bpmdemo.crm.Document_revision note) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.New_return_document_revision get_document_revision(java.lang.String session, java.lang.String i) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.Return_search_result search_by_module(java.lang.String session, java.lang.String search_string, java.lang.String[] modules, int offset, int max_results, java.lang.String assigned_user_id, java.lang.String[] select_fields, boolean unified_search_only, boolean favorites) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.Module_list get_available_modules(java.lang.String session, java.lang.String filter) throws java.rmi.RemoteException;
    public java.lang.String get_user_team_id(java.lang.String session) throws java.rmi.RemoteException;
    public void set_campaign_merge(java.lang.String session, java.lang.String[] targets, java.lang.String campaign_id) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.Get_entries_count_result get_entries_count(java.lang.String session, java.lang.String module_name, java.lang.String query, int deleted) throws java.rmi.RemoteException;
    public java.lang.String[] get_module_fields_md5(java.lang.String session, java.lang.String[] module_names) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.Last_viewed_entry[] get_last_viewed(java.lang.String session, java.lang.String[] module_names) throws java.rmi.RemoteException;
    public com.ithrdevbuild.bpmdemo.crm.Upcoming_activity_entry[] get_upcoming_activities(java.lang.String session) throws java.rmi.RemoteException;
}
