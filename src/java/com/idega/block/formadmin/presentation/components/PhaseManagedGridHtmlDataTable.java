package com.idega.block.formadmin.presentation.components;

import java.io.IOException;
import java.util.List;

import javax.faces.context.FacesContext;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class PhaseManagedGridHtmlDataTable extends GridHtmlDataTable {
	
	public static final String COLUMN_PROPERTIES = "com.idega.block.formadmin.presentation.components.PhaseManagedGridHtmlDataTable.COLUMN_PROPERTIES";
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException {
		
		setColumnsPropertiesNames(
				(List<String>)ctx.getExternalContext().getSessionMap().get(COLUMN_PROPERTIES)
		);
		
		super.encodeBegin(ctx);
	}
	
	
}