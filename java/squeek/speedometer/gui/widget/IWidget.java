package squeek.speedometer.gui.widget;

import squeek.speedometer.gui.IGuiEventHandler;
import squeek.speedometer.gui.IGuiHierarchical;

import java.util.List;

public interface IWidget extends IGuiHierarchical, IGuiEventHandler
{
	void setSize(int w, int h);

	void setPos(int x, int y);

	int getWidth();

	int getHeight();

	int getX();

	int getY();

	void setVisible(boolean visible);

	boolean isVisible();

	void setEnabled(boolean enabled);

	boolean isEnabled();

	List<String> getTooltip(int mouseX, int mouseY);

	void drawForeground(int mouseX, int mouseY);

	void drawBackground(int mouseX, int mouseY);

	void draw(int mouseX, int mouseY);

	void mouseClicked(int mouseX, int mouseY, int type, boolean isShiftKeyDown);

	boolean keyTyped(char c, int i);

	void handleMouseInput();

	void update();
}
