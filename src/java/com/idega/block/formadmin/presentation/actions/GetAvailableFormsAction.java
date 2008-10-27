package com.idega.block.formadmin.presentation.actions;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;

import com.idega.block.formadmin.presentation.components.ISelectedRowProvider;
import com.idega.documentmanager.business.Form;
import com.idega.documentmanager.business.PersistenceManager;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.13 $
 * 
 * Last modified: $Date: 2008/10/27 20:20:26 $ by $Author: civilis $
 */
public class GetAvailableFormsAction implements ActionListener, ISelectedRowProvider {
	
	private PersistenceManager persistence_manager;
	
	private String selected_row;
	List<String> col_props;
	
	public void processAction(ActionEvent ae) { }
	
	public static final String COLUMNID_FORM_NAME = "label";
	
	public List<SelectItem> getAvailableForms() {
		
		List<Form> forms = getPersistenceManager().getStandaloneForms();
		ArrayList<SelectItem> items = new ArrayList<SelectItem>(forms.size());
		
		for (Form persistedForm : forms) {
			
			SelectItem item = new SelectItem();
			item.setLabel(persistedForm.getDisplayName());
			item.setValue(persistedForm.getFormId().toString());
			items.add(item);
		}
		
		return items;
	}
	
	public List<String> getTableColumnsProperties() {
		
		if(col_props == null) {
			
			col_props = new ArrayList<String>();
			col_props.add(COLUMNID_FORM_NAME);
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
		return selected_row;
	}
	
	private PersistenceManager getPersistenceManager() {
		
		return persistence_manager;
	}
	
	public void setPersistenceManager(PersistenceManager persistence_manager) {
		this.persistence_manager = persistence_manager;
	}
}