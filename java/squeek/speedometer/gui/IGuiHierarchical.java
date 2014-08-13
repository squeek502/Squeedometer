package squeek.speedometer.gui;

import java.util.List;
import squeek.speedometer.gui.widget.IWidget;

public interface IGuiHierarchical
{
	public void addChild(IWidget child);

	public boolean removeChild(IWidget child);

	public void addChildren(List<IWidget> children);

	public void removeChildren(List<IWidget> children);

	public void clearChildren();

	public IGuiHierarchical getParent();

	public void setParent(IGuiHierarchical parent);

	public IGuiHierarchical getTopMostParent();
}