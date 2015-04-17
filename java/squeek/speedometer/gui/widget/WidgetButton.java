package squeek.speedometer.gui.widget;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import squeek.speedometer.gui.GuiEvent;
import squeek.speedometer.gui.IGuiHierarchical;

public class WidgetButton extends WidgetBase
{
	protected static final ResourceLocation defaultButtonTexture = new ResourceLocation("textures/gui/widgets.png");
	protected ResourceLocation buttonTexture = WidgetButton.defaultButtonTexture;
	protected int textureX = 0;
	protected int textureY = 46;
	protected WidgetLabel label = null;

	public WidgetButton(IGuiHierarchical parent, int x, int y, int w, int h, String labelText)
	{
		super(parent, x, y, w, h);
		this.label = new WidgetLabel(this, getX() + getWidth() / 2, getY() + getHeight() / 2, labelText, 0xffffff, true);
	}

	public WidgetButton(IGuiHierarchical parent, int x, int y, int w, int h, ResourceLocation buttonTexture, int textureX, int textureY, String labelText)
	{
		super(parent, x, y, w, h);
		this.buttonTexture = buttonTexture;
		this.textureX = textureX;
		this.textureY = textureY;
		this.label = new WidgetLabel(this, x + w / 2, y + h / 2, labelText, 0xffffff, true);
	}

	@Override
	public void drawBackground(int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(buttonTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int offsetTextureY = (isEnabled() ? (isMouseInsideBounds(mouseX, mouseY) ? 2 : 1) : 0) * 20;
		this.drawTexturedModalRect(x, y, textureX, textureY + offsetTextureY, w / 2, h);
		this.drawTexturedModalRect(x + w / 2, y, textureX + 200 - w / 2, textureY + offsetTextureY, w / 2, h);

		for (IWidget child : this.children)
		{
			if (child instanceof WidgetLabel)
			{
				WidgetLabel childLabel = (WidgetLabel) child;
				childLabel.setColor(isEnabled() ? (isMouseInsideBounds(mouseX, mouseY) ? 0xffffa0 : 0xffffff) : 0xa0a0a0);
			}
		}

		super.drawBackground(mouseX, mouseY);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int type, boolean isShiftKeyDown)
	{
		if (isEnabled() && isVisible() && isMouseInsideBounds(mouseX, mouseY))
		{
	        this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
			onClicked(type, isShiftKeyDown);
		}
		else
			super.mouseClicked(mouseX, mouseY, type, isShiftKeyDown);
	}

	protected void onClicked(int type, boolean isShiftKeyDown)
	{
		Object eventData[] = {type, isShiftKeyDown};
		pushGuiEvent(GuiEvent.BUTTON_PRESSED, eventData);
	}

	public void setLabelText(String text)
	{
		label.setText(text);
	}

}
