package squeek.speedometer.gui.screen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import squeek.speedometer.gui.GuiEvent;
import squeek.speedometer.gui.IGuiEventHandler;
import squeek.speedometer.gui.IGuiHierarchical;
import squeek.speedometer.gui.widget.IWidget;

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
			this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
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
	protected void mouseClicked(int mouseX, int mouseY, int type)
	{
		for (IWidget child : this.children)
			child.mouseClicked(mouseX, mouseY, type, isShiftKeyDown());
		super.mouseClicked(mouseX, mouseY, type);
	}

	@Override
	public void handleMouseInput()
	{
		for (IWidget child : this.children)
			child.handleMouseInput();
		super.handleMouseInput();
	}

	@Override
	protected void keyTyped(char c, int i)
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

	// Taken from GuiContainer
	protected void drawHoveringText(List<String> par1List, int par2, int par3, FontRenderer font)
	{
		if (!par1List.isEmpty())
		{
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			int k = 0;
			Iterator<String> iterator = par1List.iterator();

			while (iterator.hasNext())
			{
				String s = iterator.next();
				int l = font.getStringWidth(s);

				if (l > k)
				{
					k = l;
				}
			}

			int i1 = par2 + 12;
			int j1 = par3 - 12;
			int k1 = 8;

			if (par1List.size() > 1)
			{
				k1 += 2 + (par1List.size() - 1) * 10;
			}

			if (i1 + k > this.width)
			{
				i1 -= 28 + k;
			}

			if (j1 + k1 + 6 > this.height)
			{
				j1 = this.height - k1 - 6;
			}

			this.zLevel = 300.0F;
			int l1 = -267386864;
			this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
			this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
			this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
			this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
			this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
			int i2 = 1347420415;
			int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
			this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
			this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
			this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
			this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

			for (int k2 = 0; k2 < par1List.size(); ++k2)
			{
				String s1 = par1List.get(k2);
				font.drawStringWithShadow(s1, i1, j1, -1);

				if (k2 == 0)
				{
					j1 += 2;
				}

				j1 += 10;
			}

			this.zLevel = 0.0F;
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			RenderHelper.enableStandardItemLighting();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
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
