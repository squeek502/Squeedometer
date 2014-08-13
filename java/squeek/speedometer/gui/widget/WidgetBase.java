package squeek.speedometer.gui.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import squeek.speedometer.gui.GuiEvent;
import squeek.speedometer.gui.IGuiEventHandler;
import squeek.speedometer.gui.IGuiHierarchical;

public abstract class WidgetBase extends Gui implements IWidget, IGuiEventHandler
{
	public int x = 0;
	public int y = 0;
	public int w = 0;
	public int h = 0;
	public List<IWidget> children = new ArrayList<IWidget>();
	protected IGuiHierarchical parent = null;
	protected Minecraft mc = Minecraft.getMinecraft();
	protected boolean visible = true;
	protected boolean enabled = true;
	protected String tooltipString = null;

	public WidgetBase(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public WidgetBase(IGuiHierarchical parent)
	{
		this.setParent(parent);
	}

	public WidgetBase(IGuiHierarchical parent, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.setParent(parent);
	}

	public WidgetBase(IGuiHierarchical parent, int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.setParent(parent);
	}

	public boolean isMouseInsideBounds(int mouseX, int mouseY)
	{
		return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	@Override
	public boolean isVisible()
	{
		return this.visible;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void setParent(IGuiHierarchical parent)
	{
		if (this.parent != null)
			this.parent.removeChild(this);
		if (parent != null)
			parent.addChild(this);

		this.parent = parent;
	}

	@Override
	public IGuiHierarchical getParent()
	{
		return this.parent;
	}

	@Override
	public IGuiHierarchical getTopMostParent()
	{
		return getParent() == null ? this : getParent().getTopMostParent();
	}

	protected void onChildrenChanged()
	{
	}

	protected void onChildAdded(IWidget child)
	{
	}

	@Override
	public void addChild(IWidget child)
	{
		this.children.add(child);
		onChildAdded(child);
		onChildrenChanged();
	}

	@Override
	public void addChildren(List<IWidget> children)
	{
		this.children.addAll(children);
		for (IWidget child : children)
			onChildAdded(child);
		onChildrenChanged();
	}

	@Override
	public void clearChildren()
	{
		this.children.clear();
		onChildrenChanged();
	}

	@Override
	public boolean removeChild(IWidget child)
	{
		boolean didRemove = this.children.remove(child);
		onChildrenChanged();
		return didRemove;
	}

	@Override
	public void removeChildren(List<IWidget> children)
	{
		this.children.removeAll(children);
		onChildrenChanged();
	}

	@Override
	public void pushGuiEvent(GuiEvent event)
	{
		pushGuiEvent(event, null);
	}

	@Override
	public void pushGuiEvent(GuiEvent event, Object[] data)
	{
		onGuiEvent(event, this, data);
	}

	@Override
	public void onGuiEvent(GuiEvent event, Object source, Object[] data)
	{
		if (getParent() != null && getParent() instanceof IGuiEventHandler)
		{
			((IGuiEventHandler) getParent()).onGuiEvent(event, source, data);
		}
	}

	@Override
	public void setPos(int x, int y)
	{
		int offsetX = (x - this.x);
		int offsetY = (y - this.y);
		for (IWidget child : children)
		{
			child.setPos(child.getX() + offsetX, child.getY() + offsetY);
		}

		this.x = x;
		this.y = y;
		invalidateLayout();
	}

	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}

	@Override
	public void setSize(int w, int h)
	{
		this.w = w;
		this.h = h;
		invalidateLayout();
	}

	@Override
	public int getWidth()
	{
		return w;
	}

	@Override
	public int getHeight()
	{
		return h;
	}

	protected void invalidateLayout()
	{
		pushGuiEvent(GuiEvent.LAYOUT_CHANGED);
	}

	public void setTooltipString(String tooltipString)
	{
		this.tooltipString = tooltipString;
	}

	@Override
	public List<String> getTooltip(int mouseX, int mouseY)
	{
		List<String> tooltip = new ArrayList<String>();
		if (this.tooltipString != null && isMouseInsideBounds(mouseX, mouseY))
		{
			tooltip.addAll(Arrays.asList(tooltipString.split(System.getProperty("line.separator"))));
		}
		for (IWidget child : this.children)
			tooltip.addAll(child.getTooltip(mouseX, mouseY));
		return tooltip;
	}

	@Override
	public void drawBackground(int mouseX, int mouseY)
	{
		if (!isVisible())
			return;

		for (IWidget child : this.children)
			child.drawBackground(mouseX, mouseY);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY)
	{
		if (!isVisible())
			return;

		for (IWidget child : this.children)
			child.drawForeground(mouseX, mouseY);
	}

	@Override
	public void draw(int mouseX, int mouseY)
	{
		if (!isVisible())
			return;

		drawBackground(mouseX, mouseY);
		drawForeground(mouseX, mouseY);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int type, boolean isShiftKeyDown)
	{
		for (IWidget child : this.children)
			child.mouseClicked(mouseX, mouseY, type, isShiftKeyDown);
	}

	@Override
	public boolean keyTyped(char c, int i)
	{
		for (IWidget child : this.children)
		{
			if (child.keyTyped(c, i))
				return true;
		}
		return false;
	}

	@Override
	public void handleMouseInput()
	{
		for (IWidget child : this.children)
			child.handleMouseInput();
	}

	@Override
	public void update()
	{
		for (IWidget child : this.children)
			child.update();
	}
}
