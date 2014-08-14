package squeek.speedometer;

import java.util.ArrayList;
import net.minecraft.util.StatCollector;

public class SpeedUnit
{
	private static ArrayList<SpeedUnit> units = new ArrayList<SpeedUnit>();

	public static final SpeedUnit BLOCKSPERTICK = new SpeedUnit(1.0F, "bpt");
	public static final SpeedUnit BLOCKSPERSECOND = new SpeedUnit(0.05F, "bps");
	public static final SpeedUnit METERSPERSECOND = new SpeedUnit(1.0F, "ms");
	public static final SpeedUnit KILOMETERSPERHOUR = new SpeedUnit(0.277778F * 0.05F, "kmh");
	public static final SpeedUnit MILESPERHOUR = new SpeedUnit(0.44704F * 0.05F, "mph");

	private final float conversionFromBlocksPerTick;
	public final String name;
	public final String minimalName;
	private final String id;

	public SpeedUnit(float conversionFromBlocksPerTick, String id)
	{
		this.conversionFromBlocksPerTick = conversionFromBlocksPerTick;
		this.name = StatCollector.translateToLocal("squeedometer.unit." + id);
		
		if (StatCollector.func_94522_b("squeedometer.unit.min." + id)) // hasTranslateKey
			this.minimalName = StatCollector.translateToLocal("squeedometer.unit.min." + id);
		else
			this.minimalName = name;
		
		this.id = id;
		units.add(this);
	}

	public double convertTo(double speedInBlocksPerTick)
	{
		return speedInBlocksPerTick / this.conversionFromBlocksPerTick;
	}

	public double convertFrom(double speedInUnit)
	{
		return speedInUnit * this.conversionFromBlocksPerTick;
	}

	public String getId()
	{
		return id;
	}

	@Override
	public final String toString()
	{
		return name;
	}

	public static SpeedUnit[] getUnits()
	{
		return units.toArray(new SpeedUnit[units.size()]);
	}
}