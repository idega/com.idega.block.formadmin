package com.idega.block.formadmin.presentation.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.block.formadmin.presentation.components.ISelectedRowProvider;
import com.idega.documentmanager.business.PersistenceManager;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version 1.0
 */
public class ViewAllSubmittedDataAction implements ActionListener, ISelectedRowProvider {
	
	private static final Logger log = Logger.getLogger(ViewAllSubmittedDataAction.class.getName());
	
	private PersistenceManager persistence_manager;

	public static final String COLUMNID_LABEL1 = "label1";
	public static final String COLUMNID_LABEL2 = "label2";
	
	private String selected_row;
	private String selected_forms_row;
	List<String> col_props;
	
	private static final String AV_FORMS_ACTION = "availableFormsAction";
	
	public void processAction(ActionEvent ae) { }
	
	public List getAllSubmittedData() {		
		FacesContext ctx = FacesContext.getCurrentInstance();
		Map session_map = ctx.getExternalContext().getSessionMap();
		
		GetAvailableFormsAction available_forms_action = 
			(GetAvailableFormsAction)session_map.get(AV_FORMS_ACTION);
		
		List submitted_data_names = null;
		
		if(available_forms_action != null && available_forms_action.getSelectedRow() != null) {
			
			try {
				submitted_data_names = getPersistenceManager().listSubmittedData(available_forms_action.getSelectedRow());
			} catch (Exception e) {
				log.warning("Error getting submitted data list");
				e.printStackTrace();
			}
		}
		
		return submitted_data_names == null ? new ArrayList() : submitted_data_names;
	}
	
	public List<String> getTableColumnsProperties() {
		
		if(col_props == null) {
			
			col_props = new ArrayList<String>();
			
			col_props.add(COLUMNID_LABEL1);
			col_props.add(COLUMNID_LABEL2);
		}
		return col_props;
	}
	
	public ISelectedRowProvider getSelectedRowProvider() {
		return this;
	}
	
	public void setSelectedRow(String selected_row) {
		this.selected_row = selected_row;
	}
	
	public String getSelectedRow() {
		
		Map session_map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		GetAvailableFormsAction available_forms_action = 
			(GetAvailableFormsAction)session_map.get(AV_FORMS_ACTION);
		
		if(available_forms_action == null)
			return null;
		
		if(selected_forms_row != null) {
			
			String selected_row = available_forms_action.getSelectedRow();
			
			if(!selected_forms_row.equals(selected_row)) {
				selected_forms_row = available_forms_action.getSelectedRow();
				this.selected_row = null;
			}
				
		} else {
			selected_row = null;
			selected_forms_row = available_forms_action.getSelectedRow();
		}
		
		return selected_row;
	}
	
	public boolean isFormReaderRendered() {
		
		Map session_map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		GetAvailableFormsAction available_forms_action = 
			(GetAvailableFormsAction)session_map.get(AV_FORMS_ACTION);
		
		return available_forms_action != null && 
			available_forms_action.getSelectedRow() != null &&
			getSelectedRow() != null;
	}
	
	private PersistenceManager getPersistenceManager() {
		
		return persistence_manager;
	}
	
	public void setPersistenceManager(PersistenceManager persistence_manager) {
		this.persistence_manager = persistence_manager;
	}
}