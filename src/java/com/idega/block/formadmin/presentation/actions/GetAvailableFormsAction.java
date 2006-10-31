package com.idega.block.formadmin.presentation.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.block.form.bean.FormBean;
import com.idega.block.form.presentation.FormListViewer;
import com.idega.block.formadmin.presentation.components.PhaseManagedGridHtmlDataTable;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class GetAvailableFormsAction implements ActionListener, IPhaseValueProvider {
	
	public void processAction(ActionEvent ae) {
		
	}
	
	public static final String COLUMNID_FORM_NAME = "form_name";
	private static final String COLUMNID_ID = "id";
	
	public List getAvailableForms() {
		
		FormListViewer viewer = new FormListViewer();
		
		List<FormBean> form_beans = viewer.getForms();
		
		if(form_beans != null) {
			
			List<Map> forms_names = new ArrayList<Map>();
			
			for (Iterator<FormBean> iter = form_beans.iterator(); iter.hasNext();) {
				
				Map<String, String> form_name = new HashMap<String, String>();
				
				FormBean bean = iter.next();
				
//				TODO: change there when getId will be there
				String resource_path = bean.getResourcePath();
				
				if(resource_path.contains("/")) {
					resource_path = resource_path.substring(resource_path.lastIndexOf('/')+1);
				}
				if(resource_path.contains(".")) {
					resource_path = resource_path.substring(0, resource_path.lastIndexOf('.'));
				}
				
				form_name.put(COLUMNID_ID, resource_path);
				form_name.put(COLUMNID_FORM_NAME, bean.getName());
				
				forms_names.add(form_name);
			}
			
			return forms_names;
		}
		
		return null;
	}
	
	public static void initiateTableColumnsProperties(Map session_map) {
		
		List<String> col_props = new ArrayList<String>();
		col_props.add(COLUMNID_FORM_NAME);
		
		session_map.put(PhaseManagedGridHtmlDataTable.COLUMN_PROPERTIES, col_props);
	}
	
	public List getGridTableValues() {

		return getAvailableForms();
	}
	
	public String getButtonValue() {
		
		return "Get available forms";
	}
}
