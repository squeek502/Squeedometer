package squeek.speedometer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.client.FMLClientHandler;

//
// GuiSpeedometer
//
public class GuiSpeedometer extends Gui
{
	private static Minecraft mc = FMLClientHandler.instance().getClient();

	private double firstJumpSpeed = 0.0F;
	private double lastJumpSpeed = 0.0F;
	private double percentJumpSpeedChanged = 0.0F;
	private double jumpSpeedChanged = 0.0F;
	private static boolean didJumpThisTick = false;
	private static boolean isJumping = false;
	private boolean didJumpLastTick = false;
	private long jumpInfoStartTime = 0;
	private boolean wasFirstJump = true;

	private double currentSpeed = 0.0D;

	public static void setDidJumpThisTick(boolean val)
	{
		didJumpThisTick = val;
	}

	public static void setIsJumping(boolean val)
	{
		isJumping = val;
	}

	@ForgeSubscribe
	public void onRenderExperienceBar(RenderGameOverlayEvent.Post event)
	{
		if (event.type != ElementType.HOTBAR)
		{
			return;
		}

		this.updateValues();
		this.draw();
	}

	private float lastJumpInfoTimeRemaining()
	{
		return (Minecraft.getSystemTime() - this.jumpInfoStartTime) / 1000.0F;
	}

	private boolean showingLastJumpInfo()
	{
		return this.jumpInfoStartTime != 0 && lastJumpInfoTimeRemaining() <= (float) (ModConfig.LAST_JUMP_INFO_DURATION.getDouble(ModConfig.LAST_JUMP_INFO_DURATION_DEFAULT));
	}

	private void draw()
	{
		final ScaledResolution scaledresolution = new ScaledResolution(GuiSpeedometer.mc.gameSettings, GuiSpeedometer.mc.displayWidth, GuiSpeedometer.mc.displayHeight);

		boolean drawCurrentSpeed = true;
		boolean drawJumpSpeedChanged = lastJumpSpeed != 0 && showingLastJumpInfo();

		String strCurrentSpeed = drawCurrentSpeed ? String.format("%.4f %s", ModConfig.SPEED_UNIT.convertTo(this.currentSpeed), ModConfig.SPEED_UNIT) : null;
		String strJumpSpeedChanged = !this.wasFirstJump ? String.format("%+.2f (%+.1f%%)", ModConfig.SPEED_UNIT.convertTo(this.jumpSpeedChanged), ((this.percentJumpSpeedChanged) * 100.0F)) : String.format("%.4f", ModConfig.SPEED_UNIT.convertTo(this.firstJumpSpeed));

		int width = GuiSpeedometer.mc.fontRenderer.getStringWidth(strCurrentSpeed);
		int height = GuiSpeedometer.mc.fontRenderer.FONT_HEIGHT;
		int padding = ModConfig.SPEEDOMETER_PADDING.getInt();
		int margin = ModConfig.SPEEDOMETER_MARGIN.getInt();
		int xPos = ModConfig.SPEEDOMETER_XPOS.getInt() + margin;
		int yPos = ModConfig.SPEEDOMETER_YPOS.getInt() + margin;
		int realheight = height + padding + padding - 1;

		if (ModConfig.SPEEDOMETER_ALIGNMENT_X.getString().equals("center") || ModConfig.SPEEDOMETER_ALIGNMENT_X.getString().equals("middle"))
			xPos += scaledresolution.getScaledWidth() / 2 - (margin * 2 + width + padding * 2) / 2;
		else if (ModConfig.SPEEDOMETER_ALIGNMENT_X.getString().equals("right"))
			xPos += scaledresolution.getScaledWidth() - margin * 2 - width - padding * 2;

		if (ModConfig.SPEEDOMETER_ALIGNMENT_Y.getString().equals("center") || ModConfig.SPEEDOMETER_ALIGNMENT_Y.getString().equals("middle"))
			yPos += scaledresolution.getScaledHeight() / 2 - (margin * 2 + realheight) / 2;
		else if (ModConfig.SPEEDOMETER_ALIGNMENT_Y.getString().equals("bottom"))
			yPos += scaledresolution.getScaledHeight() - margin * 2 - realheight;

		if (ModConfig.SPEEDOMETER_DRAW_BACKGROUND.getBoolean(true))
			Gui.drawRect(xPos, yPos, xPos + width + padding * 2, yPos + height + padding + padding - 1, 0xAA000000);

		xPos += padding;
		yPos += padding;

		if (drawJumpSpeedChanged && ModConfig.LAST_JUMP_INFO_ENABLED.getBoolean(true))
		{
			int stringWidth = GuiSpeedometer.mc.fontRenderer.getStringWidth(strJumpSpeedChanged);
			int floatingXPos = xPos + (width / 2) - stringWidth / 2;

			// dont let it go offscreen
			if (floatingXPos < margin)
				floatingXPos = margin;
			else if (floatingXPos > scaledresolution.getScaledWidth() - stringWidth - margin)
				floatingXPos = scaledresolution.getScaledWidth() - stringWidth - margin;

			int floatingYPos = yPos;
			int floatingYPosOffset = (int) (GuiSpeedometer.mc.fontRenderer.FONT_HEIGHT * 1.75F);
			int floatingYPosOffsetDirection = (floatingYPos - floatingYPosOffset < 0) ? 1 : -1;
			floatingYPos += floatingYPosOffsetDirection * floatingYPosOffset;

			if (ModConfig.LAST_JUMP_INFO_FLOAT_ENABLED.getBoolean(true))
			{
				float percentJumpInfoElapsed = lastJumpInfoTimeRemaining() / (float) (ModConfig.LAST_JUMP_INFO_DURATION.getDouble(ModConfig.LAST_JUMP_INFO_DURATION_DEFAULT));
				floatingYPos += floatingYPosOffsetDirection * (percentJumpInfoElapsed * GuiSpeedometer.mc.fontRenderer.FONT_HEIGHT * 4);
			}
			int color = this.wasFirstJump ? 0x2b7bff : (this.jumpSpeedChanged == 0 ? 14737632 : (this.jumpSpeedChanged > 0 ? 1481216 : 10092544));
			this.drawString(GuiSpeedometer.mc.fontRenderer, strJumpSpeedChanged, floatingXPos, floatingYPos, color);
		}

		if (drawCurrentSpeed)
		{
			this.drawString(GuiSpeedometer.mc.fontRenderer, strCurrentSpeed, xPos, yPos, 14737632);
		}
	}

	private void updateValues()
	{
		double distTraveledLastTickX = GuiSpeedometer.mc.thePlayer.posX - GuiSpeedometer.mc.thePlayer.prevPosX;
		double distTraveledLastTickZ = GuiSpeedometer.mc.thePlayer.posZ - GuiSpeedometer.mc.thePlayer.prevPosZ;
		this.currentSpeed = MathHelper.sqrt_double(distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ);

		if ((showingLastJumpInfo() || didJumpThisTick) && !(GuiSpeedometer.mc.thePlayer.onGround && !isJumping))
		{
			if (didJumpThisTick && !this.didJumpLastTick)
			{
				this.wasFirstJump = this.lastJumpSpeed == 0.0D;
				this.percentJumpSpeedChanged = (this.currentSpeed != 0.0D) ? (this.currentSpeed / this.lastJumpSpeed - 1.0D) : -1.0D;
				this.jumpSpeedChanged = this.currentSpeed - this.lastJumpSpeed;
				this.jumpInfoStartTime = Minecraft.getSystemTime();
				this.lastJumpSpeed = this.currentSpeed;
				this.firstJumpSpeed = this.wasFirstJump ? this.lastJumpSpeed : 0.0D;
			}

			this.didJumpLastTick = didJumpThisTick;
		}
		else
		{
			this.didJumpLastTick = false;
			this.lastJumpSpeed = 0.0F;
		}
	}
}