package squeek.speedometer.gui.widget;

import squeek.speedometer.gui.GuiEvent;
import squeek.speedometer.gui.IGuiHierarchical;

public class WidgetWrapper extends WidgetBase
{
	protected WidgetBox bounds;

	public WidgetWrapper(IGuiHierarchical parent)
	{
		super(parent, 0, 0, 0, 0);
		bounds = new WidgetBox(null, 0, 0, 0, 0, 0x3300FF00);
	}

	@Override
	public void setPos(int x, int y)
	{
		if (bounds != null)
			bounds.setPos(x, y);

		super.setPos(x, y);
	}

	@Override
	public void setSize(int w, int h)
	{
		super.setSize(w, h);
		if (bounds != null)
			bounds.setSize(w, h);
	}

	@Override
	public void drawBackground(int mouseX, int mouseY)
	{
		//bounds.drawBackground(mouseX, mouseY);
		super.drawBackground(mouseX, mouseY);
	}

	@Override
	protected void onChildAdded(IWidget child)
	{
		super.onChildAdded(child);

		child.setPos(getX() + child.getX(), getY() + child.getY());
	}

	@Override
	public void onGuiEvent(GuiEvent event, Object source, Object[] data)
	{
		if (event == GuiEvent.LAYOUT_CHANGED && source != this)
		{
			determineBounds();
			return;
		}
		super.onGuiEvent(event, source, data);
	}

	@Override
	protected void onChildrenChanged()
	{
		super.onChildrenChanged();

		determineBounds();
	}

	public void determineBounds()
	{
		int minX = 0, minY = 0, maxX = 0, maxY = 0;
		boolean isInitialized = false;
		for (IWidget child : children)
		{
			if (!isInitialized)
			{
				minX = child.getX();
				maxX = minX + child.getWidth();
				minY = child.getY();
				maxY = minY + child.getHeight();
				isInitialized = true;
			}
			else
			{
				if (child.getX() < minX)
					minX = child.getX();
				if (child.getX() + child.getWidth() > maxX)
					maxX = child.getX() + child.getWidth();
				if (child.getY() < minY)
					minY = child.getY();
				if (child.getY() + child.getHeight() > maxY)
					maxY = child.getY() + child.getHeight();
			}
		}

		setSize(maxX - minX, maxY - minY);
	}

}
