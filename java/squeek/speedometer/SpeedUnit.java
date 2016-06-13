package squeek.speedometer;

import net.minecraft.client.resources.I18n;

public enum SpeedUnit
{
	BLOCKSPERTICK(1.0F, "bpt"),
	BLOCKSPERSECOND(0.05F, "bps"),
	METERSPERSECOND(0.05F, "ms"),
	KILOMETERSPERHOUR(0.277778F * 0.05F, "kmh"),
	MILESPERHOUR(0.44704F * 0.05F, "mph");

	private final float conversionFromBlocksPerTick;
	public final String name;
	public final String minimalName;
	private final String id;

	private SpeedUnit(float conversionFromBlocksPerTick, String id)
	{
		this.conversionFromBlocksPerTick = conversionFromBlocksPerTick;
		this.name = I18n.format("squeedometer.unit." + id);
		
		if (I18n.hasKey("squeedometer.unit.min." + id))
			this.minimalName = I18n.format("squeedometer.unit.min." + id);
		else
			this.minimalName = name;
		
		this.id = id;
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

	public static SpeedUnit getById(String id)
	{
		for (SpeedUnit unit : SpeedUnit.getUnits())
		{
			if (unit.id.equals(id))
				return unit;
		}
		return null;
	}

	public static SpeedUnit[] getUnits()
	{
		return SpeedUnit.values();
	}
}