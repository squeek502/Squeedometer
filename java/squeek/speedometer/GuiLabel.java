package squeek.speedometer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GuiLabel extends Gui {
	public int x;
	public int y;
	public String text;
	public int color = 0xa0a0a0;

    public GuiLabel( int _x, int _y, String _text) {
    	x = _x;
    	y = _y;
    	text = _text;
    }
    
    public GuiLabel( int _x, int _y, String _text, int _color ) {
    	x = _x;
    	y = _y;
    	text = _text;
    	color = _color;
    }
    
    public void drawLabel(Minecraft mc) {
        drawString(mc.fontRenderer, text, x, y, color);
    }
}
