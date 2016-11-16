package squeek.speedometer.gui;

import squeek.speedometer.gui.widget.IWidget;

import java.util.List;

public interface IGuiHierarchical
{
	void addChild(IWidget child);

	boolean removeChild(IWidget child);

	void addChildren(List<IWidget> children);

	void removeChildren(List<IWidget> children);

	void clearChildren();

	IGuiHierarchical getParent();

	void setParent(IGuiHierarchical parent);

	IGuiHierarchical getTopMostParent();
}