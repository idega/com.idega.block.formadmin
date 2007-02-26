package com.idega.block.formadmin;

import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;
import com.idega.idegaweb.include.GlobalIncludeManager;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version 1.0
 */
public class IWBundleStarter implements IWBundleStartable {

	public static final String IW_BUNDLE_IDENTIFIER = "com.idega.block.formadmin";

	public void start(IWBundle starterBundle) {
		
		GlobalIncludeManager.getInstance().addBundleStyleSheet(IW_BUNDLE_IDENTIFIER, "/style/formadmin.css");
	}

	public void stop(IWBundle starterBundle) {
	}
}
