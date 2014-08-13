package squeek.speedometer.gui.widget;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import squeek.speedometer.gui.IGuiHierarchical;

public class WidgetBox extends WidgetBase
{
	protected ResourceLocation texture = null;
	protected int textureX = 0;
	protected int textureY = 0;
	protected int backgroundColor = 0xAA000000;

	public WidgetBox(IGuiHierarchical parent, int x, int y, int w, int h)
	{
		super(parent, x, y, w, h);
	}

	public WidgetBox(IGuiHierarchical parent, int x, int y, int w, int h, ResourceLocation texture, int textureX, int textureY)
	{
		super(parent, x, y, w, h);
		this.texture = texture;
		this.textureX = textureX;
		this.textureY = textureY;
	}

	public WidgetBox(IGuiHierarchical parent, int x, int y, int w, int h, int backgroundColor)
	{
		super(parent, x, y, w, h);
		this.backgroundColor = backgroundColor;
	}

	@Override
	public void drawBackground(int mouseX, int mouseY)
	{
		if (texture != null)
		{
			mc.getTextureManager().bindTexture(texture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(x, y, textureX, textureY, w, h);
		}
		else
		{
			Gui.drawRect(x, y, x + w, y + h, backgroundColor);
		}

		super.drawBackground(mouseX, mouseY);
	}
}
