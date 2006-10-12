package com.idega.block.formadmin.presentation;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.apache.myfaces.component.html.ext.HtmlCommandButton;

import com.idega.webface.WFBlock;
import com.idega.webface.WFUtil;


/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public class FormViewerBlock extends WFBlock {
	
	public FormViewerBlock() {
		super("Form Admin");
	}

	@Override
	public void initializeComponent(FacesContext ctx) {
		
		HtmlForm form = new HtmlForm();
		form.setId("fooooooooooorma");
		
		HtmlOutputText title = WFUtil.getText("wuahaaaaaaaaaaaaaaaaaaa aa    kk");
		title.setRendered(true);
		
		HtmlCommandButton button = new HtmlCommandButton();
		button.setId("knopkes_id-xxxxxx");
		button.setValue("value-xxxxx");
		button.setRendered(true);
		
		List<UIComponent> form_children = form.getChildren();
		
		form_children.add(title);
		form_children.add(button);
		
		getFacets().put("form", form);
		getFacets().put("title", title);
		getFacets().put("button", button);
	}
	
	@Override
	public void decode(FacesContext ctx) {
		super.decode(ctx);
		
		System.out.println("kvieciamas decode______");
	}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		super.encodeChildren(context);
		
		UIComponent form = getFacet("form");
		
		if(form != null) {
			
			form.setRendered(true);
			renderChild(context, form);
		}
	}
}