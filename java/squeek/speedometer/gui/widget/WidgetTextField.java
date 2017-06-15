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
		this.textField = new GuiTextField(0, MC.fontRenderer, getX(), getY(), getWidth(), getHeight());
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
				this.textField = new GuiTextField(1, MC.fontRenderer, x, y, w, h);
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

	public void deleteWords(int num)
	{
		textField.deleteWords(num);
	}

	public void deleteFromCursor(int num)
	{
		textField.deleteFromCursor(num);
	}

	public void setText(String text)
	{
		textField.setText(text);
	}

	public String getText()
	{
		return textField.getText();
	}

	public String getSelectedText()
	{
		return textField.getSelectedText();
	}

	public void writeText(String textToWrite)
	{
		textField.writeText(textToWrite);
	}

	public int getNthWordFromCursor(int numWords)
	{
		return textField.getNthWordFromCursor(numWords);
	}

	public int getNthWordFromPos(int n, int pos)
	{
		return textField.getNthWordFromPos(n, pos);
	}

	public int getNthWordFromPos(int n, int pos, boolean skipWs)
	{
		return textField.getNthWordFromPosWS(n, pos, skipWs);
	}

	public void moveCursorBy(int num)
	{
		textField.moveCursorBy(num);
	}

	public void setCursorPosition(int pos)
	{
		textField.setCursorPosition(pos);
	}

	public void setCursorPositionZero()
	{
		textField.setCursorPositionZero();
	}

	public void setCursorPositionEnd()
	{
		textField.setCursorPositionEnd();
	}

	public void setMaxStringLength(int length)
	{
		textField.setMaxStringLength(length);
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

	public void setEnableBackgroundDrawing(boolean enableBackgroundDrawing)
	{
		textField.setEnableBackgroundDrawing(enableBackgroundDrawing);
	}

	public void setTextColor(int color)
	{
		textField.setTextColor(color);
	}

	public void setDisabledTextColour(int color)
	{
		textField.setDisabledTextColour(color);
	}

	public void setFocused(boolean isFocused)
	{
		textField.setFocused(isFocused);
	}

	public boolean isFocused()
	{
		return textField.isFocused();
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.setEnabled(enabled);
		textField.setEnabled(enabled);
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

	public void setSelectionPos(int position)
	{
		textField.setSelectionPos(position);
	}

	public void setCanLoseFocus(boolean canLoseFocus)
	{
		textField.setCanLoseFocus(canLoseFocus);
	}

	public boolean getTextFieldVisible()
	{
		return textField.getVisible();
	}

	public void setTextFieldVisible(boolean isVisible)
	{
		textField.setVisible(isVisible);
	}

	// End delegate methods
}