package squeek.speedometer.gui.screen;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import squeek.speedometer.gui.GuiEvent;
import squeek.speedometer.gui.IGuiEventHandler;
import squeek.speedometer.gui.IGuiHierarchical;
import squeek.speedometer.gui.widget.IWidget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiScreen extends net.minecraft.client.gui.GuiScreen implements IGuiHierarchical, IGuiEventHandler
{
	private static final List<IWidget> CHILDREN = new ArrayList<>();

	@Override
	public void initGui()
	{
		super.initGui();
		this.clearChildren();
	}

	@Override
	public void addChild(IWidget child)
	{
		CHILDREN.add(child);
	}

	@Override
	public boolean removeChild(IWidget child)
	{
		return CHILDREN.remove(child);
	}

	@Override
	public void addChildren(List<IWidget> children)
	{
		CHILDREN.addAll(children);
	}

	@Override
	public void removeChildren(List<IWidget> children)
	{
		CHILDREN.removeAll(children);
	}

	@Override
	public void clearChildren()
	{
		CHILDREN.clear();
	}

	@Override
	public IGuiHierarchical getParent()
	{
		return null;
	}

	@Override
	public void setParent(IGuiHierarchical parent)
	{
	}

	@Override
	public IGuiHierarchical getTopMostParent()
	{
		return this;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		for (IWidget child : CHILDREN)
		{
			child.drawBackground(mouseX, mouseY);
		}

		for (IWidget child : CHILDREN)
		{
			child.draw(mouseX, mouseY);
		}

		for (IWidget child : CHILDREN)
		{
			child.drawForeground(mouseX, mouseY);
		}

		List<String> tooltip = new ArrayList<>();
		for (IWidget child : CHILDREN)
		{
			tooltip.addAll(child.getTooltip(mouseX, mouseY));
		}
		if (!tooltip.isEmpty())
			this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		for (IWidget child : CHILDREN)
		{
			child.update();
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int type) throws IOException
	{
		for (IWidget child : CHILDREN)
			child.mouseClicked(mouseX, mouseY, type, isShiftKeyDown());
		super.mouseClicked(mouseX, mouseY, type);
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		for (IWidget child : CHILDREN)
			child.handleMouseInput();
		super.handleMouseInput();
	}

	@Override
	protected void keyTyped(char c, int i) throws IOException
	{
		for (IWidget child : CHILDREN)
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