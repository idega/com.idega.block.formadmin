package com.idega.block.formadmin.presentation.actions;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.idega.block.formadmin.presentation.FormViewerBlock;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class ViewAvailableFormsAction implements ActionListener, IPhaseValueProvider {
	public void processAction(ActionEvent ae) {
		
		Map session_map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		session_map.put(FormViewerBlock.PHASE, FormViewerBlock.PHASE1);
		session_map.put(FormViewerBlock.SELECTED_ROWID, session_map.get(FormViewerBlock.CURRENTLY_VIEWED_FORMID));
		session_map.remove(FormViewerBlock.CURRENTLY_VIEWED_SUBMITTED_DATA_IDENTIFIER);
	}
	
	public List getGridTableValues() { 
		throw new NullPointerException("__ Not implemented __");
	}
	
	public String getButtonValue() {
		
		return "View available forms";
	}
}
