package com.idega.block.formadmin.presentation.components;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.apache.myfaces.component.html.ext.HtmlDataTable;
import org.apache.myfaces.component.html.ext.HtmlInputHidden;
import org.apache.myfaces.custom.column.HtmlSimpleColumn;
import org.apache.myfaces.custom.htmlTag.HtmlTag;

import com.idega.block.formadmin.presentation.FormViewerBlock;

import quicktime.std.qtcomponents.SCInfo;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class GridHtmlDataTable extends HtmlDataTable {
	
	private String row_selected_color = "#89EEFE";
	private String selected_row_key;
	private String selected_row_id;
	
	public void setRowChangeColor(String color) {
		
		StringBuffer on_events;
		
		if(getRowOnMouseOver() != null)
			on_events = new StringBuffer(getRowOnMouseOver());
		else
			on_events = new StringBuffer();
		
		on_events.append(" GridHtmlDataTable_prev_color=this.style.backgroundColor;this.style.backgroundColor='")
		.append(color)
		.append("'; this.style.cursor='pointer';");
		
		setRowOnMouseOver(on_events.toString());
		setRowOnMouseOut("this.style.backgroundColor=GridHtmlDataTable_prev_color;");
	}
	
	public void setRowSelectedColor(String color) {
		row_selected_color = color;
	}
	
	public String getSelectedRowId(FacesContext ctx) {

		if(selected_row_key == null)
			return null;
		
		selected_row_id = (String)ctx.getExternalContext().getRequestParameterMap().get(selected_row_key);
		
		return selected_row_id;
	}
	
	public void setSelectedRowId(String row_id) {
		selected_row_id = row_id;
	}
	
	private void removeHelperTags(Map session_map, List parent_children) {
		
		for (Iterator iter = parent_children.iterator(); iter.hasNext();) {
			Object element = iter.next();
			
			if(element instanceof RowStateKeeper || element instanceof ScriptKeeper) {
			
				iter.remove();
			}
		}
	}
	
	protected void initRows(FacesContext ctx) {
		
		String grid_client_id = getClientId(ctx);
		
		RowStateKeeper selected_row_state_keeper = new RowStateKeeper();
		
		selected_row_state_keeper.setId(getId()+"_selected_row");
		selected_row_state_keeper.setRendered(true);
		
		String selected_row_variable = grid_client_id.replaceAll(":", "_")+"_selected_row";
		
		Map session_map = ctx.getExternalContext().getSessionMap();
		List parent_children = getParent().getChildren();
		
		removeHelperTags(session_map, parent_children);
		
		selected_row_id = (String)session_map.get(FormViewerBlock.SELECTED_ROWID);
		
		if(selected_row_id != null) {
			
			ScriptKeeper script_tag = new ScriptKeeper();
			script_tag.setValue("script");
			
			HtmlOutputText script_text = new HtmlOutputText();

			/* js code below
			 * 
			 * rows = document.getElementById('grid_client_id')
			 * .getElementsByTagName('tbody')[0].
			 * .getElementsByTagName('tr');
			 * 
			 * for(i = 0; i < rows.length; i++) {
			 * 
			 * 		if(if(rows[i].getElementsByTagName('td')[0].getElementsByTagName('input')[0].value == selected_row_id) {
			 * 			rows[i].style.backgroundColor = 'row_selected_color';
			 * 			selected_row_variable = rows[i];
			 * 			break;
			 * 		}
			 * }
			 * 
			 */
			
			String rows_variable_name = grid_client_id.replaceAll(":", "_")+"_rows";
			
			String script = new StringBuffer(rows_variable_name)
			
			.append(" = document.getElementById('")
			.append(grid_client_id)
			.append("')")
			.append(".getElementsByTagName('tbody')[0]")
			.append(".getElementsByTagName('tr');")
			.append("for(i = 0; i < ")
			.append(rows_variable_name)
			.append(".length; i++) {")
			.append("if(")
			.append(rows_variable_name)
			.append("[i].getElementsByTagName('td')[0].getElementsByTagName('input')[0].value == '")
			.append(selected_row_id)
			.append("') {")
			.append(rows_variable_name)
			.append("[i].style.backgroundColor = '")
			.append(row_selected_color)
			.append("';")
			.append(selected_row_variable)
			.append(" = ")
			.append(rows_variable_name)
			.append("[i];")
			.append("break;")
			.append("} }")
			
			.toString();
			
			script_text.setValue(script);
			script_text.setRendered(true);
			script_tag.getChildren().add(script_text);
			
			parent_children.add(script_tag);
			
			selected_row_state_keeper.setValue(selected_row_id);
		}
		
		parent_children.add(selected_row_state_keeper);
		selected_row_key = selected_row_state_keeper.getClientId(ctx);
		
		StringBuffer on_click;
		
		if(getRowOnClick() != null)
			on_click = new StringBuffer(getRowOnClick());
		else
			on_click = new StringBuffer();
		
//		js code below ----	
		
//		if(!window.selected_row || this != selected_row) {
//
//      	if(window.selected_row) { selected_row.style.backgroundColor = GridHtmlDataTable_prev_color; }
//
//      		GridHtmlDataTable_sel_row = this.firstChild.firstChild.value;
//      		
//				if(GridHtmlDataTable_sel_row) {
//      			
//      			document.getElementById('hidden_id').value = GridHtmlDataTable_sel_row;
//      
//      			this.style.backgroundColor='color';
//      			selected_lala = this; 
//					GridHtmlDataTable_prev_color = 'color';
//      	}
//		}
		
		on_click
		.append(" if(!window.")
		.append(selected_row_variable)
		.append(" || this != ")
		.append(selected_row_variable)
		.append(") {")
		
		.append(" if(window.")
		.append(selected_row_variable)
		.append(") { ")
		.append(selected_row_variable)
		.append(".style.backgroundColor = GridHtmlDataTable_prev_color; }")
		
		.append("GridHtmlDataTable_sel_row = this.firstChild.firstChild.value;")
		.append("if(GridHtmlDataTable_sel_row) {")
		.append("document.getElementById('")
		.append(selected_row_state_keeper.getClientId(ctx))
		.append("')")
		.append(".value = GridHtmlDataTable_sel_row;")
		.append("this.style.backgroundColor='")
		.append(row_selected_color)
		.append("';")
		.append(selected_row_variable)
		.append(" = this; GridHtmlDataTable_prev_color = '")
		.append(row_selected_color)
		.append("';")
		.append("} }");
		
		setRowOnClick(on_click.toString());
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException {
		
		initRows(ctx);
		super.encodeBegin(ctx);
	}
	
	public void setColumnsPropertiesNames(List<String> properties_names) {

		List table_children = getChildren();
		Application app = FacesContext.getCurrentInstance().getApplication();
		
		HtmlSimpleColumn col = new HtmlSimpleColumn();
		col.setRendered(true);
		col.setStyle("display: none;");
		
		HtmlInputHidden current_row_id_keeper = new HtmlInputHidden();
		
		current_row_id_keeper.setValueBinding("value", app.createValueBinding("#{forms_iterator.id}"));
		current_row_id_keeper.setRendered(true);
		
		col.getChildren().add(current_row_id_keeper);
		
		table_children.add(col);
		
		String att_name = "value";
		
		for (Iterator<String> iter = properties_names.iterator(); iter.hasNext();) {
			
			String prop_name = iter.next();
			
			col = new HtmlSimpleColumn();
			col.setRendered(true);
			
			HtmlOutputText col_label = new HtmlOutputText();
			
			String val_binding_exp = new StringBuffer("#{forms_iterator.")
			.append(prop_name)
			.append("}")
			.toString();
			
			col_label.setValueBinding(att_name, app.createValueBinding(val_binding_exp));
			col_label.setRendered(true);
			col.getChildren().add(col_label);
			table_children.add(col);
		}
		
		col = new HtmlSimpleColumn();
		col.setRendered(true);
		col.setStyle("display: none;");
		
		table_children.add(col);
		
		setVar("forms_iterator");
	}
	
	@Override
	public Object saveState(FacesContext ctx) {
		Object[] values = new Object[3];
		values[0] = super.saveState(ctx);
		values[1] = selected_row_key;
		values[2] = row_selected_color;

		return values;
	}
	@Override
	public void restoreState(FacesContext ctx, Object state) {
		
		Object[] values = (Object[])state;

		super.restoreState(ctx, values[0]);
		selected_row_key = (String)values[1];
		row_selected_color = (String)values[2];
	}
}