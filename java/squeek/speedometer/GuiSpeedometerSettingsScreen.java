package squeek.speedometer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiSpeedometerSettingsScreen extends GuiScreen {

    protected String screenTitle = "Speedometer Settings";
    
    private GuiTextField xPosField;
    private GuiTextField yPosField;
    private GuiTextField paddingField;
    private GuiTextField marginField;
    //private GuiTextField alignXField;
    //private GuiTextField alignYField;
    //private GuiTextField unitsField;
    
    private final static int padding = 4;
    private final static int colMargin = 8;
    private final static int colWidth = 150;
    private final static int rowMargin = 8;
    private final static int rowHeight = 20;
    private final static int buttonHeight = 20;
    private final static int buttonWidth = 50;

    protected List<Gui> labelList = new ArrayList<Gui>();
    
    class Background extends Gui
    {
    	int color = 0xAA770000;
    	int x=0;
    	int y=0;
    	int width=0;
    	int height=0;
    	
    	public Background( int _x, int _y, int _width, int _height )
    	{
    		x = _x; y = _y; width = _width; height = _height;
    	}

    	public Background( int _x, int _y, int _width, int _height, int _color )
    	{
    		x = _x; y = _y; width = _width; height = _height; color = _color;
    	}
    	
    	public void draw()
    	{
            Gui.drawRect(x, y, x+width, y+height, color);
    	}
    }
    
    class AlignmentButton extends GuiButton
    {
        public AlignmentButton(int par1, int par2, int par3, int par4,
				int par5, String par6Str) {
			super(par1, par2, par3, par4, par5, par6Str);
		}
        public AlignmentButton(int par1, int par2, int par3, int par4,
				int par5, String par6Str, boolean _isHighlighted) {
			super(par1, par2, par3, par4, par5, par6Str);
			isHighlighted = _isHighlighted;
		}

		public boolean isHighlighted = false;
		public int highlightColor = 0xFF007700;
		public int buttonColor = 0xFF000000;
		public int hoverColor = 0xFF333333;
		public int disabledColor = 0xFFAAAAAA;
		
		/**
         * Draws this button to the screen.
         */
    	@Override
        public void drawButton(Minecraft par1Minecraft, int par2, int par3)
        {
    		
            if (this.drawButton)
            {
                FontRenderer fontrenderer = par1Minecraft.fontRenderer;
                par1Minecraft.getTextureManager().bindTexture(buttonTextures);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
                int k = this.getHoverState(this.field_82253_i);
                Gui.drawRect(this.xPosition, this.yPosition, this.xPosition+this.width, this.yPosition+this.height, isHighlighted ? highlightColor : 0xFFA0A0A0);
                Gui.drawRect(this.xPosition+1, this.yPosition+1, this.xPosition+this.width-1, this.yPosition+this.height-1, k == 0 ? disabledColor : (k == 1 ? buttonColor : hoverColor));
                this.mouseDragged(par1Minecraft, par2, par3);
                int l = 14737632;

                if (!this.enabled)
                {
                    l = -6250336;
                }
                else if (this.field_82253_i)
                {
                    l = 16777120;
                }

                this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
            }
        }
    }
    
    protected List<Background> backgroundList = new ArrayList<Background>();

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @SuppressWarnings("unchecked")
	@Override
    public void initGui() {
    	int topY = Math.max(padding, height / 6 - rowHeight);
    	int bottomY = height-buttonHeight-padding*2;
    	int midX = width/2;
    	
        buttonList.clear();
        labelList.clear();
        backgroundList.clear();
        
        buttonList.add(new GuiButton(2, midX - buttonWidth - padding, bottomY+padding, buttonWidth, buttonHeight, "Save"));
        buttonList.add(new GuiButton(3, midX + padding, bottomY+padding, buttonWidth, buttonHeight, "Cancel"));

        // skip past the title
        topY += fontRenderer.FONT_HEIGHT+padding+rowMargin;

    	int curY = topY;
    	int curX = midX;
        // get total available column height
    	int colHeight = bottomY - topY;
    	int textFieldWidth = colWidth/2-padding;
    	int textFieldHeight = rowHeight;
    	
    	int totalLabelHeight = fontRenderer.FONT_HEIGHT+padding;
    	int totalRowHeight = rowHeight+rowMargin;
        int alignScreenHeight = Math.max( rowHeight, Math.min( colHeight-totalLabelHeight, rowHeight+totalRowHeight+totalLabelHeight ) );
        int alignButtonDimensions = Math.min( alignScreenHeight/3-1, 10 );
    	int totalYHeightOfRows = 2*(totalRowHeight+totalLabelHeight)+totalLabelHeight+alignScreenHeight+totalRowHeight+totalRowHeight;
    	int numColumns = Math.min( 5, Math.max( 1, (int)Math.floor(totalYHeightOfRows/colHeight+1) ) );
    	int curColumn = 1;

    	/*
    	int colStartX = curX - colWidth/2 - (numColumns-1)*(colWidth/2+colMargin/2);
    	for (int i=0; i<numColumns; i++)
    	{
    		backgroundList.add( new Background( colStartX, topY, colWidth, colHeight ) );
    		colStartX += colWidth+colMargin;
    	}
    	*/
    	
    	curX -= (numColumns-1)*(colWidth/2+colMargin/2);
        
    	// row 1
    	labelList.add(new GuiLabel(curX-textFieldWidth-padding, curY, "X:"));
    	labelList.add(new GuiLabel(curX+padding, curY, "Y:"));
    	
    	curY += totalLabelHeight;
    	
        xPosField = new GuiTextField(fontRenderer, curX - textFieldWidth - padding, curY, textFieldWidth, textFieldHeight);
        xPosField.setFocused(true);
        xPosField.setMaxStringLength(6);
        xPosField.setText(String.valueOf(ModConfig.SPEEDOMETER_XPOS.getInt(0)));

        yPosField = new GuiTextField(fontRenderer, curX + padding, curY, textFieldWidth, textFieldHeight);
        yPosField.setMaxStringLength(6);
        yPosField.setText(String.valueOf(ModConfig.SPEEDOMETER_YPOS.getInt(0)));

        curY += totalRowHeight;
        
        if (curY+totalLabelHeight+totalRowHeight >= topY+colHeight && curColumn+1<=numColumns)
        {
        	curY = topY;
        	curX += colWidth+colMargin;
        	curColumn++;
        }

    	//curX += (colWidth+colMargin*2);
    	
        // row 2
    	labelList.add(new GuiLabel(curX - textFieldWidth - padding, curY, "Margin:"));
    	labelList.add(new GuiLabel(curX+padding, curY, "Padding:"));

    	curY += totalLabelHeight;
    	
        marginField = new GuiTextField(fontRenderer, curX - textFieldWidth - padding, curY, textFieldWidth, textFieldHeight);
        marginField.setMaxStringLength(6);
        marginField.setText(String.valueOf(ModConfig.SPEEDOMETER_MARGIN.getInt(0)));

        paddingField = new GuiTextField(fontRenderer, curX + padding, curY, textFieldWidth, textFieldHeight);
        paddingField.setMaxStringLength(6);
        paddingField.setText(String.valueOf(ModConfig.SPEEDOMETER_PADDING.getInt(0)));

        curY += totalRowHeight;

        if (curY+totalLabelHeight+alignScreenHeight >= topY+colHeight && curColumn+1<=numColumns)
        {
        	curY = topY;
        	curX += colWidth+colMargin;
        	curColumn++;
        }
        
        // row 3-5
    	labelList.add(new GuiLabel(curX-colWidth/2, curY, "Screen Alignment:"));

    	curY += totalLabelHeight;
        
        backgroundList.add( new Background(curX-colWidth/2, curY, colWidth, alignScreenHeight, 0xAA000000) );
        
        String xAlign = ModConfig.SPEEDOMETER_ALIGNMENT_X.getString();
        String yAlign = ModConfig.SPEEDOMETER_ALIGNMENT_Y.getString();
        
        buttonList.add(new AlignmentButton(4, curX - colWidth/2, curY, alignButtonDimensions, alignButtonDimensions, "", xAlign.equals("left") && yAlign.equals("top")));
        buttonList.add(new AlignmentButton(5, curX - alignButtonDimensions/2, curY, alignButtonDimensions, alignButtonDimensions, "", xAlign.equals("center") && yAlign.equals("top")));
        buttonList.add(new AlignmentButton(6, curX + colWidth/2 - alignButtonDimensions, curY, alignButtonDimensions, alignButtonDimensions, "", xAlign.equals("right") && yAlign.equals("top")));
        
        buttonList.add(new AlignmentButton(7, curX - colWidth/2, curY+alignScreenHeight/2 - alignButtonDimensions/2, alignButtonDimensions, alignButtonDimensions, "", xAlign.equals("left") && yAlign.equals("middle")));
        buttonList.add(new AlignmentButton(8, curX - alignButtonDimensions/2, curY+alignScreenHeight/2 - alignButtonDimensions/2, alignButtonDimensions, alignButtonDimensions, "", xAlign.equals("center") && yAlign.equals("middle")));
        buttonList.add(new AlignmentButton(9, curX + colWidth/2 - alignButtonDimensions, curY+alignScreenHeight/2 - alignButtonDimensions/2, alignButtonDimensions, alignButtonDimensions, "", xAlign.equals("right") && yAlign.equals("middle")));

        buttonList.add(new AlignmentButton(10, curX - colWidth/2, curY+alignScreenHeight-alignButtonDimensions, alignButtonDimensions, alignButtonDimensions, "", xAlign.equals("left") && yAlign.equals("bottom")));
        buttonList.add(new AlignmentButton(11, curX - alignButtonDimensions/2, curY+alignScreenHeight-alignButtonDimensions, alignButtonDimensions, alignButtonDimensions, "", xAlign.equals("center") && yAlign.equals("bottom")));
        buttonList.add(new AlignmentButton(12, curX + colWidth/2 - alignButtonDimensions, curY+alignScreenHeight-alignButtonDimensions, alignButtonDimensions, alignButtonDimensions, "", xAlign.equals("right") && yAlign.equals("bottom")));

    	curY += alignScreenHeight+rowMargin;
    	
        if (curY+rowHeight >= topY+colHeight && curColumn+1<=numColumns)
        {
        	curY = topY;
        	curX += colWidth+colMargin;
        	curColumn++;
        }
        
        buttonList.add(new GuiButton(13, curX-colWidth/2, curY, colWidth, rowHeight, "Last Jump Info: "+( ModConfig.LAST_JUMP_INFO_ENABLED.getBoolean(true) ? "ON" : "OFF" ) ));

    	curY += totalRowHeight;
    	
        if (curY+totalRowHeight+totalLabelHeight >= topY+colHeight && curColumn+1<=numColumns)
        {
        	curY = topY;
        	curX += colWidth+colMargin;
        	curColumn++;
        }
    	
        buttonList.add(new GuiButton(14, curX-colWidth/2, curY, colWidth, rowHeight, "Units: "+ModConfig.SPEED_UNIT.toString() ));

    	curY += totalRowHeight;
    	
        if (curY+totalRowHeight+totalLabelHeight >= topY+colHeight && curColumn+1<=numColumns)
        {
        	curY = topY;
        	curX += colWidth+colMargin;
        	curColumn++;
        }
        
        buttonList.add(new GuiButton(15, curX-colWidth/2, curY, colWidth, rowHeight, "Background: "+( ModConfig.SPEEDOMETER_DRAW_BACKGROUND.getBoolean(true) ? "ON" : "OFF" ) ));
        
        updateStrings();
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int x, int y, float z) {
        updateStrings();
        
        int ys = Math.max(padding, height / 6 - rowHeight);
        int xs = width / 2;
        
        drawDefaultBackground();
        for (int k = 0; k < this.backgroundList.size(); ++k)
        {
            Background guibackground = (Background)this.backgroundList.get(k);
            guibackground.draw();
        }
        
        drawCenteredString(fontRenderer, "Speedometer settings", xs, ys, 0xffffff);

        for (int k = 0; k < this.labelList.size(); ++k)
        {
            GuiLabel guilabel = (GuiLabel)this.labelList.get(k);
            guilabel.drawLabel(this.mc);
        }
        
        xPosField.drawTextBox();
        yPosField.drawTextBox();
        paddingField.drawTextBox();
        marginField.drawTextBox();

        super.drawScreen(x, y, z);
    }
    
    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen() {
    	xPosField.updateCursorCounter();
    	yPosField.updateCursorCounter();
    }
    
    /**
     * Fired when a control is clicked. This is the equivalent of
     * ActionListener.actionPerformed(ActionEvent e).
     */
    @Override
    protected void actionPerformed(GuiButton button) {
        if (!button.enabled) {
            return;
        }

        // save/cancel
        if (button.id == 2 || button.id == 3)
        {
	        if (button.id == 2) {
	            save();
	        }
	        else
	        {
	        	load();
	        }
	        mc.displayGuiScreen(null);
        }
        else if (button.id <= 12)
        {
            for (int k = 0; k < this.buttonList.size(); ++k)
            {
            	if ( ((GuiButton)buttonList.get(k)).id <= 3 || ((GuiButton)buttonList.get(k)).id > 12 )
            		continue;
            	
                AlignmentButton otherbutton = (AlignmentButton)this.buttonList.get(k);
                otherbutton.isHighlighted = false;
            }
        	((AlignmentButton)button).isHighlighted = true;
            
	        switch (button.id)
	        {
	        case 4:
	        case 7:
	        case 10:
	        	ModConfig.SPEEDOMETER_ALIGNMENT_X.set("left");
	        	break;
	        case 5:
	        case 8:
	        case 11:
	        	ModConfig.SPEEDOMETER_ALIGNMENT_X.set("center");
	        	break;
	        case 6:
	        case 9:
	        case 12:
	        	ModConfig.SPEEDOMETER_ALIGNMENT_X.set("right");
	        }
	        
	        switch (button.id)
	        {
	        case 4:
	        case 5:
	        case 6:
	        	ModConfig.SPEEDOMETER_ALIGNMENT_Y.set("top");
	        	break;
	        case 7:
	        case 8:
	        case 9:
	        	ModConfig.SPEEDOMETER_ALIGNMENT_Y.set("middle");
	        	break;
	        case 10:
	        case 11:
	        case 12:
	        	ModConfig.SPEEDOMETER_ALIGNMENT_Y.set("bottom");
	        }
        }
        else
        {
        	switch(button.id)
        	{
        	case 13:
	        	ModConfig.LAST_JUMP_INFO_ENABLED.set( !ModConfig.LAST_JUMP_INFO_ENABLED.getBoolean(true) );
	        	button.displayString = "Last Jump Info: "+( ModConfig.LAST_JUMP_INFO_ENABLED.getBoolean(true) ? "ON" : "OFF" );
        		break;
        	case 14:
        		SpeedUnit[] units = SpeedUnit.getUnits();
        		int curIndex = 0;
        		for (int i=0; i<units.length; i++)
        		{
        			if (units[i] == ModConfig.SPEED_UNIT)
        			{
        				curIndex = i;
        				break;
        			}
        		}
        		int nextIndex = (curIndex + 1) % units.length;
        		ModConfig.setSpeedUnit( units[nextIndex].getId() );
        		button.displayString = "Units: "+ModConfig.SPEED_UNIT.toString();
        		break;
        	case 15:
	        	ModConfig.SPEEDOMETER_DRAW_BACKGROUND.set( !ModConfig.SPEEDOMETER_DRAW_BACKGROUND.getBoolean(true) );
	        	button.displayString = "Background: "+( ModConfig.SPEEDOMETER_DRAW_BACKGROUND.getBoolean(true) ? "ON" : "OFF" );
        		break;
        	}
        }
    }
    
    private void save()
    {
        ModConfig.save();
    }
    
    private void load()
    {
    	ModConfig.load();
    }
    
    private void updateStrings() {
    }
    
    /**
     * Fired when a key is typed. This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e).
     */
    @Override
    protected void keyTyped(char par1, int par2) {
        // switch text fields with tab
        if (par1 == '\t') {
            if (xPosField.isFocused()) {
            	xPosField.setFocused(false);
            	yPosField.setFocused(true);
            } else if (yPosField.isFocused()) {
            	yPosField.setFocused(false);
            	marginField.setFocused(true);
            } else if (marginField.isFocused()) {
                marginField.setFocused(false);
                paddingField.setFocused(true);
            } else {
            	paddingField.setFocused(false);
            	xPosField.setFocused(true);
            }
        }
        else
        {
            xPosField.textboxKeyTyped(par1, par2);
            yPosField.textboxKeyTyped(par1, par2);
            marginField.textboxKeyTyped(par1, par2);
            paddingField.textboxKeyTyped(par1, par2);
        }
        updateValues();
    }
    
    private int getIntValueOfTextField( GuiTextField textField )
    {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
    
    private void updateValues() {
    	ModConfig.SPEEDOMETER_XPOS.set( getIntValueOfTextField( xPosField ) );
    	ModConfig.SPEEDOMETER_YPOS.set( getIntValueOfTextField( yPosField ) );
    	ModConfig.SPEEDOMETER_MARGIN.set( getIntValueOfTextField( marginField ) );
    	ModConfig.SPEEDOMETER_PADDING.set( getIntValueOfTextField( paddingField ) );
    }
    
    /**
     * Called when the mouse is clicked.
     */
    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        xPosField.mouseClicked(par1, par2, par3);
        yPosField.mouseClicked(par1, par2, par3);
        paddingField.mouseClicked(par1, par2, par3);
        marginField.mouseClicked(par1, par2, par3);
    }
}
