package com.idega.block.formadmin.presentation.tags;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version 1.0
 */
public class FormViewerBlockTag extends UIComponentTag {
	
	public String getComponentType() {
		return "FormAdmin";
	}

	public String getRendererType() {
		return null;
	}

	protected void setProperties(UIComponent component) {
		super.setProperties(component);
	}
}
