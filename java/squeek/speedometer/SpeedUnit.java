package squeek.speedometer;

import java.util.ArrayList;

public class SpeedUnit {
	private static ArrayList<SpeedUnit> units = new ArrayList<SpeedUnit>();
	
	public static final SpeedUnit BLOCKSPERTICK = new SpeedUnit(1.0F, "blocks/tick", "bpt");
	public static final SpeedUnit BLOCKSPERSECOND = new SpeedUnit(0.05F, "blocks/sec", "bps");
	public static final SpeedUnit METERSPERSECOND = new SpeedUnit(1.0F, "meters/sec", "ms");
	public static final SpeedUnit KILOMETERSPERHOUR = new SpeedUnit(0.277778F*0.05F, "km/h", "kmh");
	public static final SpeedUnit MILESPERHOUR = new SpeedUnit(0.44704F*0.05F, "mph", "mph");
	
	private final float conversionFromBlocksPerTick;
	private final String name;
	private final String id;
	
	public SpeedUnit( float conversionFromBlocksPerTick, String name, String id ) {
		this.conversionFromBlocksPerTick = conversionFromBlocksPerTick;
		this.name = name;
		this.id = id;
		units.add(this);
	}
	
	public double convertTo( double speedInBlocksPerTick )
	{
		return speedInBlocksPerTick/this.conversionFromBlocksPerTick;
	}
	
	public double convertFrom( double speedInUnit )
	{
		return speedInUnit*this.conversionFromBlocksPerTick;
	}
	
	public String getId()
	{
		return id;
	}
	
	public final String toString()
	{
		return name;
	}
	
	public static SpeedUnit[] getUnits()
	{
		return units.toArray( new SpeedUnit[units.size()] );
	}
}