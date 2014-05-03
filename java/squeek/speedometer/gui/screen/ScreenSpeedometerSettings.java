package squeek.speedometer.gui.screen;

import squeek.speedometer.ModConfig;
import squeek.speedometer.gui.GuiEvent;
import squeek.speedometer.gui.widget.*;

public class ScreenSpeedometerSettings extends GuiScreen
{
	private final static int padding = 5;
	//private final static int colMargin = 8;
	private final static int colWidth = 150;
	//private final static int rowMargin = 8;
	private final static int rowHeight = 20;
	private final static int buttonHeight = 20;
	private final static int buttonWidth = 50;

	protected WidgetButton saveButton;
	protected WidgetButton cancelButton;

	@Override
	public void initGui()
	{
		super.initGui();

		int midX = width / 2;
		int topY = Math.max(padding, height / 6 - rowHeight);
		int bottomY = height - buttonHeight - Math.max(padding, height / 8 - rowHeight); //buttonHeight - padding * 2;

		new WidgetLabel(this, midX, topY + fontRenderer.FONT_HEIGHT / 2, "Speedometer settings", 0xFFFFFF, true);

		saveButton = new WidgetButton(this, midX - buttonWidth - padding, bottomY, buttonWidth, buttonHeight, "Save");
		cancelButton = new WidgetButton(this, midX + padding, bottomY, buttonWidth, buttonHeight, "Cancel");

		topY += fontRenderer.FONT_HEIGHT + padding;
		bottomY -= padding;
		int gridHeight = bottomY - topY;
		int halfColWidth = (colWidth-padding)/2;
		
		WidgetFluidGrid fluidGrid = new WidgetFluidGrid(this, padding, topY, width - padding * 2, gridHeight);
		
		WidgetWrapper positionSettings = new WidgetWrapper(fluidGrid);
		new WidgetTextField(positionSettings, 0, fontRenderer.FONT_HEIGHT+padding, halfColWidth, rowHeight);
		new WidgetLabel(positionSettings, 0, 0, "X:").drawCentered = false;
		new WidgetTextField(positionSettings, halfColWidth+padding, fontRenderer.FONT_HEIGHT+padding, halfColWidth, rowHeight);
		new WidgetLabel(positionSettings, halfColWidth+padding, 0, "Y:").drawCentered = false;

		WidgetWrapper layoutSettings = new WidgetWrapper(fluidGrid);
		new WidgetTextField(layoutSettings, 0, fontRenderer.FONT_HEIGHT+padding, halfColWidth, rowHeight);
		new WidgetLabel(layoutSettings, 0, 0, "Margin:").drawCentered = false;
		new WidgetTextField(layoutSettings, halfColWidth+padding, fontRenderer.FONT_HEIGHT+padding, halfColWidth, rowHeight);
		new WidgetLabel(layoutSettings, halfColWidth+padding, 0, "Padding:").drawCentered = false;

		WidgetWrapper alignmentSettings = new WidgetWrapper(fluidGrid);
		new WidgetLabel(alignmentSettings, 0, 0, "Screen Alignment:").drawCentered = false;
		new WidgetBox(alignmentSettings, 0, fontRenderer.FONT_HEIGHT+padding, colWidth, buttonHeight*3-4);

		WidgetWrapper buttons = new WidgetWrapper(fluidGrid);
		new WidgetButton(buttons, 0, 0, colWidth, buttonHeight, "Last Jump Info: ON");
		new WidgetButton(buttons, 0, buttonHeight + padding, colWidth, buttonHeight, "Units: blocks/sec");
		new WidgetButton(buttons, 0, buttonHeight*2 + padding*2, colWidth, buttonHeight, "Background: OFF");
		
		fluidGrid.determineLayout();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, f);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}

	@Override
	public void onGuiEvent(GuiEvent event, Object source, Object[] data)
	{
		if (event == GuiEvent.BUTTON_PRESSED)
		{
			int type = (Integer) data[0];

			if (type == 0 && (source == saveButton || source == cancelButton))
			{
				if (source == saveButton)
					ModConfig.save();
				else if (source == cancelButton)
					ModConfig.load();

				mc.displayGuiScreen(null);
				return;
			}
		}

		super.onGuiEvent(event, source, data);
	}

}
