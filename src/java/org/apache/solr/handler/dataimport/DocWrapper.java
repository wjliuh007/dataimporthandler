package org.apache.solr.handler.dataimport;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

public class DocWrapper extends SolrInputDocument {
  //final SolrInputDocument solrDocument = new SolrInputDocument();
  Map<String ,Object> session;

  public void setSessionAttribute(String key, Object val){
    if(session == null) session = new HashMap<>();
    session.put(key, val);
  }

  public Object getSessionAttribute(String key) {
    return session == null ? null : session.get(key);
  }
}