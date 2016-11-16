package squeek.speedometer.gui;

public interface IGuiEventHandler
{
	void pushGuiEvent(GuiEvent event);

	void pushGuiEvent(GuiEvent event, Object[] data);

	void onGuiEvent(GuiEvent event, Object source, Object[] data);
}
