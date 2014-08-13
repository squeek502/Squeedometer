package squeek.speedometer.gui;

public interface IGuiEventHandler
{
	public void pushGuiEvent(GuiEvent event);

	public void pushGuiEvent(GuiEvent event, Object[] data);

	public void onGuiEvent(GuiEvent event, Object source, Object[] data);
}
