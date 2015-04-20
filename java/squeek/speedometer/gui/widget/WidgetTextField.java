package squeek.speedometer.gui.widget;

import net.minecraft.client.gui.GuiTextField;
import squeek.speedometer.gui.GuiEvent;
import squeek.speedometer.gui.IGuiHierarchical;

public class WidgetTextField extends WidgetBase
{

	protected GuiTextField textField;

	public WidgetTextField(IGuiHierarchical parent, int x, int y, int w, int h)
	{
		super(parent, x, y, w, h);
		this.textField = new GuiTextField(0, mc.fontRendererObj, getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int type, boolean isShiftKeyDown)
	{
		super.mouseClicked(mouseX, mouseY, type, isShiftKeyDown);
		if (type == 1 && isMouseInsideBounds(mouseX, mouseY))
		{
			this.textField.setText("");
			this.onTextChangedByUser("");
		}
		this.textField.mouseClicked(mouseX, mouseY, type);
	}

	@Override
	public boolean keyTyped(char c, int i)
	{
		if (super.keyTyped(c, i))
			return true;

		String lastText = this.textField.getText();
		this.textField.textboxKeyTyped(c, i);
		if (!lastText.equals(this.textField.getText()))
		{
			onTextChangedByUser(this.textField.getText());
			return true;
		}
		else
			return false;
	}

	@Override
	public void drawBackground(int mouseX, int mouseY)
	{
		this.textField.drawTextBox();
		super.drawBackground(mouseX, mouseY);
	}

	@Override
	public void onGuiEvent(GuiEvent event, Object source, Object[] data)
	{
		if (event == GuiEvent.LAYOUT_CHANGED)
		{
			// seriously have to recreate the GuiTextField ...
			if (this.textField != null)
			{
				this.textField = new GuiTextField(1, mc.fontRendererObj, x, y, w, h);
			}
		}
		super.onGuiEvent(event, source, data);
	}

	@Override
	public void update()
	{
		this.textField.updateCursorCounter();
		super.update();
	}

	protected void onTextChangedByUser(String newText)
	{
		Object eventData[] = {newText};
		pushGuiEvent(GuiEvent.TEXT_CHANGED, eventData);
	}

	// Begin delegate methods

	public void updateCursorCounter()
	{
		textField.updateCursorCounter();
	}

	public void deleteWords(int par1)
	{
		textField.deleteWords(par1);
	}

	public void deleteFromCursor(int par1)
	{
		textField.deleteFromCursor(par1);
	}

	public void setText(String par1Str)
	{
		textField.setText(par1Str);
	}

	public String getText()
	{
		return textField.getText();
	}

	public String getSelectedText()
	{
		return textField.getSelectedText();
	}

	public void writeText(String par1Str)
	{
		textField.writeText(par1Str);
	}

	public int getNthWordFromCursor(int par1)
	{
		return textField.getNthWordFromCursor(par1);
	}

	public int getNthWordFromPos(int par1, int par2)
	{
		return textField.getNthWordFromPos(par1, par2);
	}

	public int func_73798_a(int par1, int par2, boolean par3)
	{
		return textField.func_146197_a(par1, par2, par3);
	}

	public void moveCursorBy(int par1)
	{
		textField.moveCursorBy(par1);
	}

	public void setCursorPosition(int par1)
	{
		textField.setCursorPosition(par1);
	}

	public void setCursorPositionZero()
	{
		textField.setCursorPositionZero();
	}

	public void setCursorPositionEnd()
	{
		textField.setCursorPositionEnd();
	}

	public void setMaxStringLength(int par1)
	{
		textField.setMaxStringLength(par1);
	}

	public int getMaxStringLength()
	{
		return textField.getMaxStringLength();
	}

	public int getCursorPosition()
	{
		return textField.getCursorPosition();
	}

	public boolean getEnableBackgroundDrawing()
	{
		return textField.getEnableBackgroundDrawing();
	}

	public void setEnableBackgroundDrawing(boolean par1)
	{
		textField.setEnableBackgroundDrawing(par1);
	}

	public void setTextColor(int par1)
	{
		textField.setTextColor(par1);
	}

	public void setDisabledTextColour(int par1)
	{
		textField.setDisabledTextColour(par1);
	}

	public void setFocused(boolean par1)
	{
		textField.setFocused(par1);
	}

	public boolean isFocused()
	{
		return textField.isFocused();
	}

	@Override
	public void setEnabled(boolean par1)
	{
		this.setEnabled(par1);
		textField.setEnabled(par1);
	}

	public int getSelectionEnd()
	{
		return textField.getSelectionEnd();
	}

	/*
	public int getWidth()
	{
	    return textField.getWidth();
	}
	*/

	public void setSelectionPos(int par1)
	{
		textField.setSelectionPos(par1);
	}

	public void setCanLoseFocus(boolean par1)
	{
		textField.setCanLoseFocus(par1);
	}

	public boolean getTextFieldVisible()
	{
		return textField.getVisible();
	}

	public void setTextFieldVisible(boolean par1)
	{
		textField.setVisible(par1);
	}

	// End delegate methods
}
