package squeek.speedometer.gui.widget;

import java.util.List;
import squeek.speedometer.gui.IGuiEventHandler;
import squeek.speedometer.gui.IGuiHierarchical;

public interface IWidget extends IGuiHierarchical, IGuiEventHandler
{
	public void setSize(int w, int h);

	public void setPos(int x, int y);

	public int getWidth();

	public int getHeight();

	public int getX();

	public int getY();

	public void setVisible(boolean visible);

	public boolean isVisible();

	public void setEnabled(boolean enabled);

	public boolean isEnabled();

	public List<String> getTooltip(int mouseX, int mouseY);

	public void drawForeground(int mouseX, int mouseY);

	public void drawBackground(int mouseX, int mouseY);

	public void draw(int mouseX, int mouseY);

	public void mouseClicked(int mouseX, int mouseY, int type, boolean isShiftKeyDown);

	public boolean keyTyped(char c, int i);

	public void handleMouseInput();

	public void update();
}
