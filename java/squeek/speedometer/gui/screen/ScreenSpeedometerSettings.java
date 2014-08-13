package squeek.speedometer.gui.screen;

import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import squeek.speedometer.ModConfig;
import squeek.speedometer.SpeedUnit;
import squeek.speedometer.gui.GuiEvent;
import squeek.speedometer.gui.IGuiHierarchical;
import squeek.speedometer.gui.widget.*;
import cpw.mods.fml.common.Loader;

public class ScreenSpeedometerSettings extends GuiScreen
{
	private final static int padding = 5;
	//private final static int colMargin = 8;
	private final static int colWidth = 150;
	//private final static int rowMargin = 8;
	private final static int rowHeight = 20;
	private final static int buttonHeight = 20;
	private final static int buttonWidth = 50;
	private final static int alignButtonDimensions = 10;

	protected WidgetButton saveButton;
	protected WidgetButton cancelButton;

	protected WidgetButton lastJumpButton;
	protected WidgetButton unitsButton;
	protected WidgetButton backgroundButton;
	protected WidgetTextField xField;
	protected WidgetTextField yField;
	protected WidgetTextField paddingField;
	protected WidgetTextField marginField;
	protected WidgetWrapper alignmentSettings;

	protected class WidgetScreenAlignment extends WidgetButton
	{
		private String xAlign;
		private String yAlign;
		public boolean isHighlighted = false;
		public int highlightColor = 0xFF007700;
		public int buttonColor = 0xFF000000;
		public int hoverColor = 0xFF333333;
		public int disabledColor = 0xFFAAAAAA;
		public int innerColor = 0xFFA0A0A0;

		public WidgetScreenAlignment(IGuiHierarchical parent, int x, int y, int w, int h, String xAlign, String yAlign)
		{
			super(parent, x, y, w, h, "");
			this.xAlign = xAlign;
			this.yAlign = yAlign;
		}

		@Override
		public void drawBackground(int mouseX, int mouseY)
		{
			if (isVisible())
			{
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				boolean hovered = isMouseInsideBounds(mouseX, mouseY);
				Gui.drawRect(x, y, x + w, y + h, isHighlighted ? highlightColor : innerColor);
				Gui.drawRect(x + 1, y + 1, x + w - 1, y + h - 1, !isEnabled() ? disabledColor : (hovered ? hoverColor : buttonColor));
			}
		}

		@Override
		protected void onClicked(int type, boolean isShiftKeyDown)
		{
			Object eventData[] = {type, isShiftKeyDown, xAlign, yAlign};
			pushGuiEvent(GuiEvent.BUTTON_PRESSED, eventData);
		}
	}

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
		int halfColWidth = (colWidth - padding) / 2;

		WidgetFluidGrid fluidGrid = new WidgetFluidGrid(this, padding, topY, width - padding * 2, gridHeight);

		WidgetWrapper positionSettings = new WidgetWrapper(fluidGrid);
		xField = new WidgetTextField(positionSettings, 0, fontRenderer.FONT_HEIGHT + padding, halfColWidth, rowHeight);
		new WidgetLabel(positionSettings, 0, 0, "X:").drawCentered = false;
		yField = new WidgetTextField(positionSettings, halfColWidth + padding, fontRenderer.FONT_HEIGHT + padding, halfColWidth, rowHeight);
		new WidgetLabel(positionSettings, halfColWidth + padding, 0, "Y:").drawCentered = false;

		WidgetWrapper layoutSettings = new WidgetWrapper(fluidGrid);
		marginField = new WidgetTextField(layoutSettings, 0, fontRenderer.FONT_HEIGHT + padding, halfColWidth, rowHeight);
		new WidgetLabel(layoutSettings, 0, 0, "Margin:").drawCentered = false;
		paddingField = new WidgetTextField(layoutSettings, halfColWidth + padding, fontRenderer.FONT_HEIGHT + padding, halfColWidth, rowHeight);
		new WidgetLabel(layoutSettings, halfColWidth + padding, 0, "Padding:").drawCentered = false;

		int alignBoxTop = fontRenderer.FONT_HEIGHT + padding;
		int alignBoxHeight = buttonHeight * 3 - 4;
		alignmentSettings = new WidgetWrapper(fluidGrid);
		new WidgetLabel(alignmentSettings, 0, 0, "Screen Alignment:").drawCentered = false;
		new WidgetBox(alignmentSettings, 0, alignBoxTop, colWidth, alignBoxHeight);
		new WidgetScreenAlignment(alignmentSettings, 0, alignBoxTop, alignButtonDimensions, alignButtonDimensions, "left", "top");
		new WidgetScreenAlignment(alignmentSettings, colWidth / 2 - alignButtonDimensions / 2, alignBoxTop, alignButtonDimensions, alignButtonDimensions, "center", "top");
		new WidgetScreenAlignment(alignmentSettings, colWidth - alignButtonDimensions, alignBoxTop, alignButtonDimensions, alignButtonDimensions, "right", "top");
		new WidgetScreenAlignment(alignmentSettings, 0, alignBoxTop + alignBoxHeight / 2 - alignButtonDimensions / 2, alignButtonDimensions, alignButtonDimensions, "left", "middle");
		new WidgetScreenAlignment(alignmentSettings, colWidth / 2 - alignButtonDimensions / 2, alignBoxTop + alignBoxHeight / 2 - alignButtonDimensions / 2, alignButtonDimensions, alignButtonDimensions, "center", "middle");
		new WidgetScreenAlignment(alignmentSettings, colWidth - alignButtonDimensions, alignBoxTop + alignBoxHeight / 2 - alignButtonDimensions / 2, alignButtonDimensions, alignButtonDimensions, "right", "middle");
		new WidgetScreenAlignment(alignmentSettings, 0, alignBoxTop + alignBoxHeight - alignButtonDimensions, alignButtonDimensions, alignButtonDimensions, "left", "bottom");
		new WidgetScreenAlignment(alignmentSettings, colWidth / 2 - alignButtonDimensions / 2, alignBoxTop + alignBoxHeight - alignButtonDimensions, alignButtonDimensions, alignButtonDimensions, "center", "bottom");
		new WidgetScreenAlignment(alignmentSettings, colWidth - alignButtonDimensions, alignBoxTop + alignBoxHeight - alignButtonDimensions, alignButtonDimensions, alignButtonDimensions, "right", "bottom");

		WidgetWrapper buttons = new WidgetWrapper(fluidGrid);
		lastJumpButton = new WidgetButton(buttons, 0, 0, colWidth, buttonHeight, "");
		unitsButton = new WidgetButton(buttons, 0, buttonHeight + padding, colWidth, buttonHeight, "");
		backgroundButton = new WidgetButton(buttons, 0, buttonHeight * 2 + padding * 2, colWidth, buttonHeight, "");

		if (!Loader.isModLoaded("Squake"))
			lastJumpButton.setEnabled(false);

		fluidGrid.determineLayout();

		setWidgetValuesFromConfig();
	}

	private void setWidgetValuesFromConfig()
	{
		lastJumpButton.setLabelText(String.format("Last Jump Info: %s", ModConfig.LAST_JUMP_INFO_ENABLED.getBoolean(true) ? "ON" : "OFF"));
		unitsButton.setLabelText(String.format("Units: %s", ModConfig.SPEED_UNIT.toString()));
		backgroundButton.setLabelText(String.format("Background: %s", ModConfig.SPEEDOMETER_DRAW_BACKGROUND.getBoolean(true) ? "ON" : "OFF"));
		xField.setText(String.valueOf(ModConfig.SPEEDOMETER_XPOS.getInt(0)));
		yField.setText(String.valueOf(ModConfig.SPEEDOMETER_YPOS.getInt(0)));
		marginField.setText(String.valueOf(ModConfig.SPEEDOMETER_MARGIN.getInt(0)));
		paddingField.setText(String.valueOf(ModConfig.SPEEDOMETER_PADDING.getInt(0)));

		String alignX = ModConfig.SPEEDOMETER_ALIGNMENT_X.getString();
		String alignY = ModConfig.SPEEDOMETER_ALIGNMENT_Y.getString();
		for (IWidget alignmentSettingsChild : alignmentSettings.children)
		{
			if (alignmentSettingsChild instanceof WidgetScreenAlignment)
			{
				WidgetScreenAlignment widgetAlign = (WidgetScreenAlignment) alignmentSettingsChild;
				widgetAlign.isHighlighted = widgetAlign.xAlign.equals(alignX) && widgetAlign.yAlign.equals(alignY);
			}
		}
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

			if (source == saveButton || source == cancelButton)
			{
				if (type == 0)
				{
					if (source == saveButton)
						ModConfig.save();
					else if (source == cancelButton)
						ModConfig.load();

					mc.displayGuiScreen(null);
				}
				return;
			}
			else if (source == lastJumpButton)
			{
				ModConfig.LAST_JUMP_INFO_ENABLED.set(!ModConfig.LAST_JUMP_INFO_ENABLED.getBoolean(true));
			}
			else if (source == unitsButton)
			{
				SpeedUnit[] units = SpeedUnit.getUnits();
				int curIndex = 0;
				for (int i = 0; i < units.length; i++)
				{
					if (units[i] == ModConfig.SPEED_UNIT)
					{
						curIndex = i;
						break;
					}
				}
				int step = type == 0 ? 1 : -1;
				int nextIndex = (curIndex + step % units.length + units.length) % units.length;
				ModConfig.setSpeedUnit(units[nextIndex].getId());
			}
			else if (source == backgroundButton)
			{
				ModConfig.SPEEDOMETER_DRAW_BACKGROUND.set(!ModConfig.SPEEDOMETER_DRAW_BACKGROUND.getBoolean(true));
			}
			else if (source instanceof WidgetScreenAlignment)
			{
				ModConfig.SPEEDOMETER_ALIGNMENT_X.set((String) data[2]);
				ModConfig.SPEEDOMETER_ALIGNMENT_Y.set((String) data[3]);
			}
			else
				return;

			setWidgetValuesFromConfig();
		}
		else if (event == GuiEvent.TEXT_CHANGED)
		{
			String text = (String) data[0];
			if (source == xField)
				ModConfig.SPEEDOMETER_XPOS.set(getIntValueOfText(text));
			else if (source == yField)
				ModConfig.SPEEDOMETER_YPOS.set(getIntValueOfText(text));
			else if (source == marginField)
				ModConfig.SPEEDOMETER_MARGIN.set(getIntValueOfText(text));
			else if (source == paddingField)
				ModConfig.SPEEDOMETER_PADDING.set(getIntValueOfText(text));
			else
				return;

			setWidgetValuesFromConfig();
		}

		super.onGuiEvent(event, source, data);
	}

	private int getIntValueOfText(String text)
	{
		try
		{
			return Integer.parseInt(text);
		}
		catch (NumberFormatException ex)
		{
			return 0;
		}
	}
}
