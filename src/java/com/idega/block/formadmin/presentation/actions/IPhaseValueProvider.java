package com.idega.block.formadmin.presentation.actions;

import java.util.List;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas ‰ivilis</a>
 * @version 1.0
 */
public interface IPhaseValueProvider {

	public abstract List getGridTableValues();

	public abstract String getButtonValue();
}