package squeek.speedometer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Constructor;

public class HudSpeedometer extends Gui
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

	private static Constructor<ScaledResolution> scaledResolution188Constructor = null;
	static
	{
		try
		{
			scaledResolution188Constructor = ScaledResolution.class.getConstructor(Minecraft.class);
		}
		catch(Exception e)
		{
		}
	}

	public static void setDidJumpThisTick(boolean val)
	{
		didJumpThisTick = val;
	}

	public static void setIsJumping(boolean val)
	{
		isJumping = val;
	}

	@SubscribeEvent
	public void onRenderExperienceBar(RenderGameOverlayEvent.Pre event)
	{
		if (event.getType() != ElementType.HOTBAR)
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
		// in 1.8.8, the ScaledResolution constructor changed; allow for either one
		ScaledResolution scaledresolution;
		if (scaledResolution188Constructor != null)
		{
			try
			{
				scaledresolution = scaledResolution188Constructor.newInstance(mc);
			}
			catch(Exception e)
			{
				return;
			}
		}
		else
			scaledresolution = new ScaledResolution(mc);

		boolean drawCurrentSpeed = true;
		boolean drawJumpSpeedChanged = lastJumpSpeed != 0 && showingLastJumpInfo();

		String numFormatString = "%." + ModConfig.SPEEDOMETER_PRECISION.getInt() + "f";
		String unitString = ModConfig.SHOW_UNITS.getBoolean(true) ? " " + (ModConfig.MINIMAL_UNITS.getBoolean(true) ? ModConfig.SPEED_UNIT.minimalName : ModConfig.SPEED_UNIT.name) : "";
		String strCurrentSpeed = drawCurrentSpeed ? String.format(numFormatString + "%s", ModConfig.SPEED_UNIT.convertTo(this.currentSpeed), unitString) : null;
		String strJumpSpeedChanged = !this.wasFirstJump ? String.format("%+.2f (%+.1f%%)", ModConfig.SPEED_UNIT.convertTo(this.jumpSpeedChanged), ((this.percentJumpSpeedChanged) * 100.0F)) : String.format(numFormatString, ModConfig.SPEED_UNIT.convertTo(this.firstJumpSpeed));

		int width = HudSpeedometer.mc.fontRendererObj.getStringWidth(strCurrentSpeed);
		int height = HudSpeedometer.mc.fontRendererObj.FONT_HEIGHT;
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
			int stringWidth = HudSpeedometer.mc.fontRendererObj.getStringWidth(strJumpSpeedChanged);
			int floatingXPos = xPos + (width / 2) - stringWidth / 2;

			// dont let it go offscreen
			if (floatingXPos < margin)
				floatingXPos = margin;
			else if (floatingXPos > scaledresolution.getScaledWidth() - stringWidth - margin)
				floatingXPos = scaledresolution.getScaledWidth() - stringWidth - margin;

			int floatingYPos = yPos;
			int floatingYPosOffset = (int) (HudSpeedometer.mc.fontRendererObj.FONT_HEIGHT * 1.75F);
			int floatingYPosOffsetDirection = (floatingYPos - floatingYPosOffset < 0) ? 1 : -1;
			floatingYPos += floatingYPosOffsetDirection * floatingYPosOffset;

			if (ModConfig.LAST_JUMP_INFO_FLOAT_ENABLED.getBoolean(true))
			{
				float percentJumpInfoElapsed = lastJumpInfoTimeRemaining() / (float) (ModConfig.LAST_JUMP_INFO_DURATION.getDouble(ModConfig.LAST_JUMP_INFO_DURATION_DEFAULT));
				floatingYPos += floatingYPosOffsetDirection * (percentJumpInfoElapsed * HudSpeedometer.mc.fontRendererObj.FONT_HEIGHT * 4);
			}
			int color = this.wasFirstJump ? 0x2b7bff : (this.jumpSpeedChanged == 0 ? 14737632 : (this.jumpSpeedChanged > 0 ? 1481216 : 10092544));
			this.drawString(HudSpeedometer.mc.fontRendererObj, strJumpSpeedChanged, floatingXPos, floatingYPos, color);
		}

		if (drawCurrentSpeed)
		{
			this.drawString(HudSpeedometer.mc.fontRendererObj, strCurrentSpeed, xPos, yPos, 14737632);
		}
	}

	private void updateValues()
	{
		double distTraveledLastTickX = HudSpeedometer.mc.thePlayer.posX - HudSpeedometer.mc.thePlayer.prevPosX;
		double distTraveledLastTickZ = HudSpeedometer.mc.thePlayer.posZ - HudSpeedometer.mc.thePlayer.prevPosZ;
		this.currentSpeed = MathHelper.sqrt_double(distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ);

		if ((showingLastJumpInfo() || didJumpThisTick) && !(HudSpeedometer.mc.thePlayer.onGround && !isJumping))
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