package squeek.speedometer;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ModConfig
{

	public static final String CATEGORY_SPEEDOMETER = "speedometer";

	public static Property SPEEDOMETER_ALIGNMENT_X;
	private static final String SPEEDOMETER_ALIGNMENT_X_NAME = "xAlign";
	public static final String SPEEDOMETER_ALIGNMENT_X_DEFAULT = "left";

	public static Property SPEEDOMETER_ALIGNMENT_Y;
	private static final String SPEEDOMETER_ALIGNMENT_Y_NAME = "yAlign";
	public static final String SPEEDOMETER_ALIGNMENT_Y_DEFAULT = "bottom";

	public static Property SPEEDOMETER_XPOS;
	private static final String SPEEDOMETER_XPOS_NAME = "xPos";
	public static final int SPEEDOMETER_XPOS_DEFAULT = 0;

	public static Property SPEEDOMETER_YPOS;
	private static final String SPEEDOMETER_YPOS_NAME = "yPos";
	public static final int SPEEDOMETER_YPOS_DEFAULT = 0;

	public static Property SPEEDOMETER_PADDING;
	private static final String SPEEDOMETER_PADDING_NAME = "padding";
	public static final int SPEEDOMETER_PADDING_DEFAULT = 2;

	public static Property SPEEDOMETER_MARGIN;
	private static final String SPEEDOMETER_MARGIN_NAME = "margin";
	public static final int SPEEDOMETER_MARGIN_DEFAULT = 4;

	public static Property SPEEDOMETER_PRECISION;
	private static final String SPEEDOMETER_PRECISION_NAME = "precision";
	public static final int SPEEDOMETER_PRECISION_DEFAULT = 2;

	public static Property SPEEDOMETER_DRAW_BACKGROUND;
	private static final String SPEEDOMETER_DRAW_BACKGROUND_NAME = "drawBackground";
	public static final boolean SPEEDOMETER_DRAW_BACKGROUND_DEFAULT = true;

	public static Property SHOW_UNITS;
	private static final String SHOW_UNITS_NAME = "showUnits";
	public static final boolean SHOW_UNITS_DEFAULT = true;

	public static Property MINIMAL_UNITS;
	private static final String MINIMAL_UNITS_NAME = "minimalUnits";
	public static final boolean MINIMAL_UNITS_DEFAULT = false;

	public static Property LAST_JUMP_INFO_ENABLED;
	private static final String LAST_JUMP_INFO_ENABLED_NAME = "lastJumpInfoEnabled";
	public static final boolean LAST_JUMP_INFO_ENABLED_DEFAULT = true;

	public static Property LAST_JUMP_INFO_FLOAT_ENABLED;
	private static final String LAST_JUMP_INFO_FLOAT_ENABLED_NAME = "lastJumpInfoTextFloatEnabled";
	public static final boolean LAST_JUMP_INFO_FLOAT_ENABLED_DEFAULT = false;

	public static Property LAST_JUMP_INFO_DURATION;
	private static final String LAST_JUMP_INFO_DURATION_NAME = "lastJumpInfoDuration";
	public static final double LAST_JUMP_INFO_DURATION_DEFAULT = 3.0F;

	private static Property SPEED_UNIT_PROPERTY;
	public static SpeedUnit SPEED_UNIT;
	private static final String SPEED_UNIT_NAME = "speedUnit";
	public static final String SPEED_UNIT_DEFAULT = "bps";

	public static Configuration config;

	public static void init(File file)
	{
		if (config == null)
		{
			config = new Configuration(file);
			load();
		}
	}

	public static void save()
	{
		if (config.hasChanged())
			config.save();
	}

	public static void load()
	{
		SPEEDOMETER_ALIGNMENT_X = config.get(CATEGORY_SPEEDOMETER, SPEEDOMETER_ALIGNMENT_X_NAME, SPEEDOMETER_ALIGNMENT_X_DEFAULT);
		SPEEDOMETER_ALIGNMENT_Y = config.get(CATEGORY_SPEEDOMETER, SPEEDOMETER_ALIGNMENT_Y_NAME, SPEEDOMETER_ALIGNMENT_Y_DEFAULT);

		SPEEDOMETER_XPOS = config.get(CATEGORY_SPEEDOMETER, SPEEDOMETER_XPOS_NAME, SPEEDOMETER_XPOS_DEFAULT);
		SPEEDOMETER_YPOS = config.get(CATEGORY_SPEEDOMETER, SPEEDOMETER_YPOS_NAME, SPEEDOMETER_YPOS_DEFAULT);

		SPEEDOMETER_PADDING = config.get(CATEGORY_SPEEDOMETER, SPEEDOMETER_PADDING_NAME, SPEEDOMETER_PADDING_DEFAULT);
		SPEEDOMETER_MARGIN = config.get(CATEGORY_SPEEDOMETER, SPEEDOMETER_MARGIN_NAME, SPEEDOMETER_MARGIN_DEFAULT);

		SPEEDOMETER_PRECISION = config.get(CATEGORY_SPEEDOMETER, SPEEDOMETER_PRECISION_NAME, SPEEDOMETER_PRECISION_DEFAULT);

		SPEEDOMETER_DRAW_BACKGROUND = config.get(CATEGORY_SPEEDOMETER, SPEEDOMETER_DRAW_BACKGROUND_NAME, SPEEDOMETER_DRAW_BACKGROUND_DEFAULT);
		SHOW_UNITS = config.get(CATEGORY_SPEEDOMETER, SHOW_UNITS_NAME, SHOW_UNITS_DEFAULT);
		MINIMAL_UNITS = config.get(CATEGORY_SPEEDOMETER, MINIMAL_UNITS_NAME, MINIMAL_UNITS_DEFAULT);

		LAST_JUMP_INFO_ENABLED = config.get(CATEGORY_SPEEDOMETER, LAST_JUMP_INFO_ENABLED_NAME, LAST_JUMP_INFO_ENABLED_DEFAULT);
		LAST_JUMP_INFO_FLOAT_ENABLED = config.get(CATEGORY_SPEEDOMETER, LAST_JUMP_INFO_FLOAT_ENABLED_NAME, LAST_JUMP_INFO_FLOAT_ENABLED_DEFAULT);
		LAST_JUMP_INFO_DURATION = config.get(CATEGORY_SPEEDOMETER, LAST_JUMP_INFO_DURATION_NAME, LAST_JUMP_INFO_DURATION_DEFAULT);

		SPEED_UNIT_PROPERTY = config.get(CATEGORY_SPEEDOMETER, SPEED_UNIT_NAME, SPEED_UNIT_DEFAULT, "valid units: bpt (blocks/tick), bps (blocks/sec), ms (meters/sec), kmh  (km/hour), mph (miles/hour)");
		String speedUnitId = SPEED_UNIT_PROPERTY.getString();
		setSpeedUnit(speedUnitId);

		save();
	}

	public static void setSpeedUnit(String speedUnitId)
	{
		setSpeedUnit(SpeedUnit.getById(speedUnitId));
	}
	
	public static void setSpeedUnit(SpeedUnit speedUnit)
	{
		if (speedUnit == null)
			SPEED_UNIT = SpeedUnit.BLOCKSPERSECOND;
		else
			SPEED_UNIT = speedUnit;

		SPEED_UNIT_PROPERTY.set(SPEED_UNIT.getId());
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equalsIgnoreCase(ModInfo.MODID)) {
			load();
		}
	}
}
