package squeek.speedometer.gui.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import squeek.speedometer.gui.GuiEvent;
import squeek.speedometer.gui.IGuiEventHandler;
import squeek.speedometer.gui.IGuiHierarchical;
import squeek.speedometer.gui.widget.IWidget;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScreen extends net.minecraft.client.gui.GuiScreen implements IGuiHierarchical, IGuiEventHandler
{

	protected List<IWidget> children = new ArrayList<IWidget>();

	@Override
	public void initGui()
	{
		super.initGui();
		this.clearChildren();
	}

	@Override
	public void addChild(IWidget child)
	{
		this.children.add(child);
	}

	@Override
	public boolean removeChild(IWidget child)
	{
		return this.children.remove(child);
	}

	@Override
	public void addChildren(List<IWidget> children)
	{
		this.children.addAll(children);
	}

	@Override
	public void removeChildren(List<IWidget> children)
	{
		this.children.removeAll(children);
	}

	@Override
	public void clearChildren()
	{
		this.children.clear();
	}

	@Override
	public IGuiHierarchical getParent()
	{
		return null;
	}

	@Override
	public void setParent(IGuiHierarchical parent)
	{
		return;
	}

	@Override
	public IGuiHierarchical getTopMostParent()
	{
		return this;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);

		for (IWidget child : this.children)
		{
			child.drawBackground(mouseX, mouseY);
		}

		for (IWidget child : this.children)
		{
			child.draw(mouseX, mouseY);
		}

		for (IWidget child : this.children)
		{
			child.drawForeground(mouseX, mouseY);
		}

		List<String> tooltip = new ArrayList<String>();
		for (IWidget child : this.children)
		{
			tooltip.addAll(child.getTooltip(mouseX, mouseY));
		}
		if (!tooltip.isEmpty())
			this.drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		for (IWidget child : this.children)
		{
			child.update();
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int type) throws IOException
	{
		for (IWidget child : this.children)
			child.mouseClicked(mouseX, mouseY, type, isShiftKeyDown());
		super.mouseClicked(mouseX, mouseY, type);
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		for (IWidget child : this.children)
			child.handleMouseInput();
		super.handleMouseInput();
	}

	@Override
	protected void keyTyped(char c, int i) throws IOException
	{
		for (IWidget child : this.children)
		{
			if (child.keyTyped(c, i))
			{
				return;
			}
		}
		super.keyTyped(c, i);
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
			((IGuiEventHandler) getParent()).onGuiEvent(event, null, data);
		}
	}

}
