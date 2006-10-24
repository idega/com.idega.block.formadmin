package com.idega.block.formadmin.presentation.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.block.formadmin.presentation.components.PhaseManagedGridHtmlDataTable;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class ViewAllSubmittedDataAction implements ActionListener, IPhaseValueProvider {
	
	public void processAction(ActionEvent ae) {
		
	}
	
	public static final String COLUMNID_LABEL1 = "label1";
	public static final String COLUMNID_LABEL2 = "label2";
	private static final String COLUMNID_ID = "id";
	public static final String SUBMITTED_DATA = "com.idega.block.formadmin.presentation.actions.ViewAllSubmittedDataAction.SUBMITTED_DATA";
	public static final String SUBMITTED_DATA_COLUMNS_PROPERTIES = "com.idega.block.formadmin.presentation.actions.ViewAllSubmittedDataAction.SUBMITTED_DATA_COLUMNS_PROPERTIES";
	
	public void getAllSubmittedData() {
		
		List<Map> submitted_data_names = new ArrayList<Map>();
		
		for (int i = 0; i < 10; i++) {
			
			Map<String, String> submitted_data = new HashMap<String, String>();
			
			submitted_data.put(COLUMNID_ID, i+"_");
			submitted_data.put(COLUMNID_LABEL1, "label "+i);
			submitted_data.put(COLUMNID_LABEL2, "label2 "+i);
			
			submitted_data_names.add(submitted_data);
		}
		
		Map session_map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		session_map.put(SUBMITTED_DATA, submitted_data_names);
	}
	
	public static void initiateTableColumnsProperties(Map session_map) {
		
		List<String> col_props = new ArrayList<String>();
		col_props.add(COLUMNID_LABEL1);
		col_props.add(COLUMNID_LABEL2);
		
		session_map.put(PhaseManagedGridHtmlDataTable.COLUMN_PROPERTIES, col_props);
	}
	
	public List getGridTableValues() {
		
		Map session_map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		getAllSubmittedData();
		
		List submitted_data = 
			(List)session_map.get(SUBMITTED_DATA);
		
		return submitted_data;
	}
	
	public String getButtonValue() {
		
		return "View all submitted data";
	}
}
